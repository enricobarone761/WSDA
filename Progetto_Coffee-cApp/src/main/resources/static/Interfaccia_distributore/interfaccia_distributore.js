// Controllo periodico dell'utente
let utente = null
let username_el = document.getElementsByClassName("username")[0].innerText;
let credito_el = document.getElementsByClassName("credit")[0].innerText;
let bevandaCard = document.querySelectorAll(".bevanda-card")

function check_user() {
    fetch("utente.json")
        .then(res => res.json())
        .then(res => {
            username_el = res.username;
            credito_el = res.credit;
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
setInterval(check_user, 1000);

document.querySelector('.btn-conferma').addEventListener('click', () => {
    alert("1111");
});