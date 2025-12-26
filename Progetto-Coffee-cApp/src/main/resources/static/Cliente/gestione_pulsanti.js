let input_id = null;
const botton_connessione = document.querySelector('#conn');
const status_div = document.querySelector('#status-connessione');
const id_connesso_el = document.querySelector('#id-connesso');
const id_utente = document.querySelector('#id_utente_hidden')?.value; //recupera da thymeleaf l'id utente che però non mostro nell'html

// CONNESSIONE
document.querySelector('#form-connessione').addEventListener('submit', (event) => {
    event.preventDefault();

    const id_distributore = document.querySelector('#id-distributore').value;
    
    if (id_distributore && id_utente) {
        fetch(`/connetti?id_utente=${id_utente}&id_distributore=${id_distributore}`, {
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
            alert(message); // "Connessione riuscita"

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
            alert(error.message);
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
            if (response.ok) {
                return response.text();
            } else {
                return response.text().then(text => { throw new Error(text) });
            }
        })
        .then(message => {
            alert(message); // "Disconnessione riuscita"
            
            // reset ui
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
            alert(error.message);
        });
    } else {
        alert('Nessun distributore connesso');
    }
});
