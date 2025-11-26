let xmlDoc = null;
fetch('../XML/addetti_manutenzione.xml')
    .then(response => response.text())
    .then(str => {
        xmlDoc = new DOMParser().parseFromString(str, "application/xml");
    })
    .then(carica_manutentori)
    .catch(errore => console.log(errore));


function costruttore_addetto(foo){
    const addetto = {
        'nome': foo.querySelector('nome').innerHTML,
        'cognome': foo.querySelector('cognome').innerHTML,
        'email': foo.querySelector('email').innerHTML
    }

    return addetto
}

function carica_manutentori(addetto){

    const nome = addetto.nome

    const div = document.createElement('div')
    div.innerHTML =
        `<div class="manutentore-card">
            <div class="manutentore-info">
                <div class="manutentore-nome">${nome} ${addetto[cognome]}</div>
                <div class="manutentore-email">${addetto[email]}</div>
            </div>
            <button class="btn-piccolo btn-danger">Rimuovi</button>
         </div>`;

    document.querySelector('.manutentori-list').appendChild(div)
    console.log(addetto)

}

function pippo(){
    const addetti = xmlDoc.getElementsByTagName('addetto');

    for( let addetto of addetti ) {
        carica_manutentori( costruttore_addetto(addetto) )
    }
}
