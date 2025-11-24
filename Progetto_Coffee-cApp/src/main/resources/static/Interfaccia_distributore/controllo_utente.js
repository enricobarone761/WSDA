// Controllo periodico dell'utente
let utente = null
let username_el = document.querySelector('.username')
let credito_el = document.querySelector('.credit')

function check_user() {
    fetch("utente.json")
        .then(res => res.json())
        .then(res => {
            if (username_el && credito_el) {
                username_el.textContent = res.username;
                credito_el.textContent = "€ " + res.credit;
            }
        console.log('check')
        })

        .catch(error => {
            document.getElementsByClassName("user-label")[0].innerText = ""
            username_el = "Nessun Utente Connesso";
            credito_el = "";
            console.log('Nessun file presente')

            document.querySelectorAll(".btn-bevanda").forEach(element => {
                element.outerHTML = '<img src="../CSS-e-Icone/blocked-svgrepo-com.svg" class="Caffè-Icona" alt="" style="height:50px">'
            })

        });
}
check_user()
//setInterval(check_user, 2000);

