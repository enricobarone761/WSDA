//Livelli forniture da XML
const id_distributore = document.querySelector('#id_distributore').innerHTML;

fetch('./XML/xml-distributori.xml')
    .then(response => response.text())
    .then(str => new DOMParser().parseFromString(str, "application/xml"))
    .catch(errore => console.log(errore))




function aggiornaForniture(){
    //TODO
}

function aggiornaID(id){
    //TODO
}

document.querySelector('#load_state').addEventListener('click', (event) => {
    event.preventDefault(); // Temporaneo per l'assignment (da rimuovere in futuro). Previene il refresh della pagina

})