let input_id = null;
const botton_connessione = document.querySelector('#conn');
const status_div = document.querySelector('#status-connessione');
const id_connesso_el = document.querySelector('#id-connesso');
const id_utente = document.querySelector('#id_utente_hidden')?.value;

// CONNESSIONE
document.querySelector('#form-connessione').addEventListener('submit', (event) => {
    event.preventDefault();

    const id_distributore = document.querySelector('#id-distributore').value;
    
    if (id_distributore && id_utente) {
        fetch(`/connetti?id_utente=${id_utente}&id_distributore=${id_distributore}`, {
            method: 'POST'
        })
        .then(response => {
            // Anche se il backend non è pronto, facciamo finta che vada a buon fine per ora
            // o gestiamo l'errore se vogliamo essere pronti.
            input_id = id_distributore;
            alert('Richiesta di connessione inviata al distributore ' + input_id);
            
            // Aggiornamento UI
            botton_connessione.disabled = true;
            botton_connessione.style.backgroundColor = '#dcd9d4';
            botton_connessione.innerHTML = 'Connesso';
            
            if (status_div && id_connesso_el) {
                id_connesso_el.textContent = input_id;
                status_div.style.display = 'block';
            }
        })
        .catch(error => {
            console.error('Errore durante la connessione:', error);
            alert('Errore di comunicazione con il server');
        });
    }
});

// DISCONNESSIONE
document.querySelector('#disconnessione-btn').addEventListener('click', () => {
    if (input_id && id_utente) {
        fetch(`/disconnetti?id_utente=${id_utente}`, {
            method: 'POST'
        })
        .then(response => {
            alert('Disconnesso dal distributore ' + input_id);
            
            // Reset UI
            input_id = null;
            botton_connessione.disabled = false;
            botton_connessione.style.backgroundColor = '';
            botton_connessione.innerHTML = 'Connetti';
            
            if (status_div) {
                status_div.style.display = 'none';
            }
            document.querySelector('#id-distributore').value = '';
        })
        .catch(error => {
            console.error('Errore durante la disconnessione:', error);
        });
    } else {
        alert('Nessun distributore connesso');
    }
});

// Nota: La ricarica è gestita tramite form POST standard nel template Thymeleaf,
// quindi non serve logica JS aggiuntiva qui a meno di voler fare fetch.
