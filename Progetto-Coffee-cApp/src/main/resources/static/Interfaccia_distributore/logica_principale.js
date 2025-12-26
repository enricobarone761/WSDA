let credito_residuo_el = document.querySelector('.credit');
const DISTRIBUTORE_ID = "CA001";

let prezzo = null;
let idBevandaSelezionata = null;

document.querySelectorAll('.btn-bevanda').forEach(btn => {
    btn.addEventListener('click', e => {
        const card = e.target.closest('.bevanda-card');

        document.querySelectorAll('.bevanda-card.attiva').forEach(c => c.classList.remove('attiva'));
        card.classList.add('attiva');

        prezzo = parseFloat(card.querySelector('.bevanda-prezzo').innerText.replace('€ ', '').replace(',', '.'));
        idBevandaSelezionata = parseInt(card.getAttribute('data-id'));

        console.log('Bevanda selezionata: ID ' + idBevandaSelezionata + ', Prezzo ' + prezzo);
    });
});

document.querySelector('.btn-conferma').addEventListener('click', () => {
    if (idBevandaSelezionata === null) {
        alert("Nessuna bevanda selezionata");
        return;
    }

    const creditoAttuale = parseFloat(credito_residuo_el.textContent);
    if (prezzo > creditoAttuale) {
        alert("Credito insufficiente! Si prega di ricaricare il credito.");
        return;
    }

    // Chiamata al backend per l'erogazione
    fetch(`/erogazione/${DISTRIBUTORE_ID}/eroga`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id_bevanda: idBevandaSelezionata
        })
    })
    .then(response => {
        if (response.ok) {
            alert("Ordine Confermato! Erogazione in corso...");
            // Non serve aggiornare il credito manualmente qui perché il polling
            // lo aggiornerà automaticamente non appena il backend scala il credito.
            resetSelezione();
        } else if (response.status === 402) {
            alert("Credito insufficiente sul server.");
        } else if (response.status === 401) {
            alert("Nessun utente connesso.");
        } else {
            alert("Errore durante l'erogazione.");
        }
    })
    .catch(error => {
        console.error('Errore:', error);
        alert("Errore di comunicazione con il server.");
    });
});

function resetSelezione() {
    prezzo = null;
    idBevandaSelezionata = null;
    document.querySelectorAll('.bevanda-card.attiva').forEach(c => c.classList.remove('attiva'));
}
