let username_el = document.querySelector('.username');
let credito_el = document.querySelector('.credit');
let userLabel_el = document.querySelector('.user-label');

const DISTRIBUTORE_ID_THIS = document.querySelector('.dispenser-id').innerText;

function check_user() {
    fetch(`http://localhost:8080/distributore/polling-utente?idDistributore=${DISTRIBUTORE_ID_THIS}`)
        .then(res => {
            if (res.status === 204) {
                resetUI();
                return null;
            }
            if (!res.ok) throw new Error("Errore nel recupero dati");
            return res.json();
        })
        .then(data => {
            if (data && data.nome) {
                if (username_el) username_el.textContent = `${data.nome} ${data.cognome || ''}`;
                if (credito_el) credito_el.textContent = data.credito_residuo !== undefined ? parseFloat(data.credito_residuo).toFixed(2) : "0.00";
                if (userLabel_el) userLabel_el.textContent = "Utente Connesso";

                // mostra i pulsanti "seleziona" e nasconde le icone di blocco
                document.querySelectorAll('.btn-bevanda').forEach(btn => btn.style.display = 'block');
                document.querySelectorAll('.icon-blocked').forEach(img => img.style.display = 'none');
            }
        })
        .catch(error => {
            console.error("Polling error:", error);
            resetUI();
        });
}

function resetUI() {
    if (username_el) username_el.textContent = "Nessun Utente";
    if (credito_el) credito_el.textContent = "0.00";
    if (userLabel_el) userLabel_el.textContent = "In attesa...";

    // mostra le icone di blocco e nasconde i pulsanti "seleziona"
    document.querySelectorAll('.btn-bevanda').forEach(btn => btn.style.display = 'none');
    document.querySelectorAll('.icon-blocked').forEach(img => {
        img.style.display = 'block';
        img.style.margin = '0 auto';
        img.style.height = '45px';
    });
}
resetUI()


// Polling continuo ogni 5 secondi
setInterval(check_user, 5000);
check_user();
