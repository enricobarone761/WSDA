//Livelli forniture da XML
let xmlDoc = null;
fetch('http://localhost:8080/info_distributori')
    .then(response => response.text())
    .then(str => {
        xmlDoc = new DOMParser().parseFromString(str, "application/xml");
    })
    .catch(errore => console.log(errore));

function carica_forniture(id){
    const forniture = xmlDoc.getElementById(id).querySelectorAll('fornitura');
    for( let fornitura of forniture ) {

        const nome = fornitura.querySelector('nome').innerHTML
        const livello = fornitura.querySelector('livello').innerHTML
        const colore_barra = livello >= 50 ? '#4caf50' : livello >= 30 ? '#ff9800' : '#f44336';

        //anziché modificare i dati sulla pagina creo direttamente l'html qui (singola fornitura)
        const div = document.createElement('div')
        div.innerHTML =
            `<div class="forniture-card">
                <div class="forniture-nome">${nome}</div>
                <div class="forniture-livello">${livello}%</div>
                <div class="barra-progresso">
                    <div class="barra-riempimento" style="width: ${livello}%; background-color: ${colore_barra};"></div>
                </div>
             </div>`;

        document.querySelector('.forniture-grid').appendChild(div)
        console.log(fornitura)
    }
}

function carica_guasti(id){
    const guasti = xmlDoc.getElementById(id).querySelectorAll('componente');
    for( let componente of guasti) {

        const nome = componente.querySelector('nome').innerHTML
        const stato = componente.querySelector('stato').innerHTML
        const icona = stato === 'OK' ? '✓' : stato === 'Manutenzione' ? '!' : 'x';
        const classe = stato === 'OK' ? 'guasto-ok' : stato === 'Manutenzione' ? 'guasto-warning' : 'guasto-errore';

        const div = document.createElement('div')
        div.innerHTML =
            `<div class="guasto-card ${classe}">
                <div class="guasto-icona">${icona}</div>
                <div class="guasto-nome">${nome}</div>
                <div class="guasto-stato">${stato}</div>
            </div>`;

        document.querySelector('.guasti-grid').appendChild(div)
        console.log(componente)
    }
}

function aggiornaID(id) {
    document.querySelector('#ID').innerHTML = 'Distributore: ' + id;
}

//PULSATE CARICA STATO
document.querySelector('#load_state').addEventListener('click', (event) => {
    event.preventDefault(); // Temporaneo per l'assignment (da rimuovere in futuro). Previene il refresh della pagina e la rimozione di tutti i dati

    // svuota le griglie prima ad ogni nuova pressione.
    document.querySelector('.forniture-grid').innerHTML = '';
    document.querySelector('.guasti-grid').innerHTML = '';

    const id_distributore = document.querySelector('#id-distributore').value;
    if(xmlDoc.getElementById(id_distributore)){ //contrlla se esiste
        console.log(id_distributore)

        aggiornaID(id_distributore)
        carica_forniture(id_distributore)
        carica_guasti(id_distributore)
    }else{
        alert('ID inesistente')
    }
})