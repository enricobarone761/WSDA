let xmlDoc = null;
fetch('/elenco-addetti')
    .then(response => response.text())
    .then(str => {
        xmlDoc = new DOMParser().parseFromString(str, "application/xml");
    })
    .then(carica_addetti)
    .catch(errore => console.log(errore));


function costruttore_addetto(foo){
    return {
        'nome': foo.querySelector('nome').innerHTML,
        'cognome': foo.querySelector('cognome').innerHTML,
        'email': foo.querySelector('email').innerHTML
    }
}

function aggiungi_html_addetto(addetto){

    const div = document.createElement('div')
    div.classList.add('manutentore-card') //ho messo questo per evitare che la card manutentore sia ulteruormente wrappata da un altro div
    div.innerHTML =
        `<div class="manutentore-info">
                <div class="manutentore-nome">${addetto.nome} ${addetto.cognome}</div>
                <div class="manutentore-email">${addetto.email}</div>
        </div>
        <button class="btn-piccolo btn-danger rmv-addetto">Rimuovi</button>`;

    //LOGICA PULSANTE RIMUOVI
    div.querySelector('.rmv-addetto').addEventListener('click', () => {
        fetch(`/gestione-addetti/rimuovi?email=${addetto.email}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                div.closest('.manutentore-card').remove();
                alert('Rimosso')
            } else {
                alert("errore");
            }
        })
        .catch(error => console.error('Error:', error));
    })

    document.querySelector('.manutentori-list').appendChild(div)
    console.log(addetto)

}

function carica_addetti(){
    const addetti = xmlDoc.getElementsByTagName('addetto');

    for( let addetto of addetti ) {
        aggiungi_html_addetto( costruttore_addetto(addetto) )
    }
}


//LOGICA PULSANTE AGGIUNGI ADDETTO
document.querySelector('#agg-add').addEventListener('click' , listener=>{
    listener.preventDefault()
    const email = document.querySelector('#addetto-email').value;

    if(document.querySelector('form').reportValidity()){
        fetch(`/gestione-addetti/aggiungi?email=${email}`, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                // ricarichiamo la lista degli addetti per aggiornarla
                fetch('/elenco-addetti')
                    .then(response => response.text())
                    .then(str => {
                        xmlDoc = new DOMParser().parseFromString(str, "application/xml");

                        document.querySelector('.manutentori-list').innerHTML = '';
                        carica_addetti();

                        // reset form
                        document.querySelector('#addetto-email').value = '';
                        alert('Ruolo addetto assegnato con successo');
                    });
            } else {
                return response.text().then(text => {
                    alert("Errore: " + text);
                });
            }
        })
        .catch(error => console.error('Error:', error));
    }

})
