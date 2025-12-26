let username_el = document.querySelector('.username');
let credito_el = document.querySelector('.credit');
let userLabel_el = document.querySelector('.user-label');

const DISTRIBUTORE_ID = "CA001";

function check_user() {
    fetch(`/api/distributore/polling-utente?id=${DISTRIBUTORE_ID}`)
        .then(res => {
            if (!res.ok) throw new Error("Utente non trovato");
            return res.json();
        })
        .then(data => {
            // L'endpoint restituisce nome, cognome e credito
            if (data && data.nome) {
                if (username_el) username_el.textContent = `${data.nome} ${data.cognome || ''}`;
                if (credito_el) credito_el.textContent = data.credito !== undefined ? parseFloat(data.credito).toFixed(2) : "0.00";
                if (userLabel_el) userLabel_el.textContent = "Utente Connesso";
            } else {
                resetUI();
            }
        })
        .catch(error => {
            resetUI();
        });
}

function resetUI() {
    if (username_el) username_el.textContent = "Nessun Utente";
    if (credito_el) credito_el.textContent = "0.00";
    if (userLabel_el) userLabel_el.textContent = "In attesa...";
}

// Polling continuo ogni 3 secondi (non si interrompe mai)
setInterval(check_user, 3000);

// Esecuzione iniziale
check_user();
