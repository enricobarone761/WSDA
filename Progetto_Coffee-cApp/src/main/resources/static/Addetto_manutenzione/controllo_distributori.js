//Livelli forniture da XML
fetch('../XML/xml-distributori.xml')
    .then(response => response.text())
    .then(str => new DOMParser().parseFromString(str, "application/xml"))
    .catch(errore => console.log(errore))

function recuperaFornitureDaID(id){
    //TODO
}


function aggiornaPaginaConForniture(id){
    //TODO
}

function aggiornaID(id){
    document.querySelector('#ID').innerHTML = 'Distributore: ' + id;
}

document.querySelector('#load_state').addEventListener('click', (event) => {
    event.preventDefault(); // Temporaneo per l'assignment (da rimuovere in futuro). Previene il refresh della pagina

    const id_distributore = document.querySelector('#id-distributore').value;
    console.log(id_distributore)

    aggiornaID(id_distributore)
})