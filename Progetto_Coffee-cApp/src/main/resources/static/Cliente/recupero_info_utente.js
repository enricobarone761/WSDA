let username_el = document.querySelector('.nome-utente');
let credito_el = document.querySelector('.credito');

function check_user() {
    fetch("../xml/utente.json")
        .then(res => res.json())
        .then(res => {
            if (username_el && credito_el) {
                username_el.textContent = res.username;
                credito_el.textContent = res.credit;
            }
            console.log('Utente Connesso')
        })

        .catch(error => {
            console.log('Nessun file presente')
        });
}
check_user()

