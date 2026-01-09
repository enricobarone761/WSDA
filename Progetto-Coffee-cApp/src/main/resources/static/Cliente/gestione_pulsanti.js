//questa pagina implementa sessioStorage, semplice funzione js che mi permette di "resistere" al refresh della pagina
//conservando l'id distributore al quale si è connessi. Quando la tab viene chiusa la sessione è cancellata

let input_id = sessionStorage.getItem('id_distributore'); //all'avvio è null

const botton_connessione = document.querySelector('#conn');
const status_div = document.querySelector('#status-connessione');
const id_connesso_el = document.querySelector('#id-connesso');
const id_utente = document.querySelector('#id_utente_hidden')?.value;

// funzione per aggiornare la UI in base allo stato di connessione
function aggiornaUI(id) {
    if (id) {
        botton_connessione.disabled = true;
        botton_connessione.style.backgroundColor = '#dcd9d4';
        botton_connessione.innerHTML = 'Connesso';
        if (status_div && id_connesso_el) {
            id_connesso_el.textContent = id;
            status_div.style.display = 'block';
        }
    } else {
        botton_connessione.disabled = false;
        botton_connessione.style.backgroundColor = '';
        botton_connessione.innerHTML = 'Connetti';
        if (status_div) {
            status_div.style.display = 'none';
        }
        document.querySelector('#id-distributore').value = '';
    }
}

// al primo avvio è null, quindi UI vuota
if (input_id) {
    aggiornaUI(input_id);
}

// CONNESSIONE
document.querySelector('#form-connessione').addEventListener('submit', (event) => {
    event.preventDefault();
    const id_distributore = document.querySelector('#id-distributore').value;
    
    if (id_distributore && id_utente) {
        fetch(`http://localhost:8080/connetti?id_utente=${id_utente}&id_distributore=${id_distributore}`, {
            method: 'POST'
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(message => {
            input_id = id_distributore;
            sessionStorage.setItem('id_distributore', input_id);
            alert(message);
            aggiornaUI(input_id);
        })
        .catch(error => {
            alert(error.message);
        });
    }
});

// DISCONNESSIONE
document.querySelector('#disconnessione-btn').addEventListener('click', () => {
    if (input_id && id_utente) {
        fetch(`http://localhost:8080/disconnetti?id_utente=${id_utente}`, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                return response.text().then(text => { throw new Error(text) });
            }
        })
        .then(message => {
            alert(message);
            input_id = null;
            sessionStorage.removeItem('id_distributore');
            aggiornaUI(null);
        })
        .catch(error => {
            alert(error.message);
        });
    } else {
        alert('Nessun distributore connesso');
    }
});

//queste due piccole righe di codice chiudono la connessione se l'utente chiude la tab del browser
window.addEventListener('beforeunload', function (_) {
    navigator.sendBeacon(`http://localhost:8080/disconnetti?id_utente=${id_utente}`);
    sessionStorage.removeItem('id_distributore');
});

