let credito_residuo_el = document.querySelector('.credit');
const DISTRIBUTORE_ID = "CA001";

let prezzo = null;
let idBevandaSelezionata = null;


//Logica di selezione della bevanda
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


// Logica erogazione e riduzione del credito
document.querySelector('.btn-conferma').addEventListener('click', () => {
    if (idBevandaSelezionata === null) {
        alert("Nessuna bevanda selezionata");
        return;
    }

    // Chiamata al backend per l'erogazione
    fetch(`http://localhost:8080/erogazione/${DISTRIBUTORE_ID}/eroga/${idBevandaSelezionata}`, {
        method: 'POST'
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
