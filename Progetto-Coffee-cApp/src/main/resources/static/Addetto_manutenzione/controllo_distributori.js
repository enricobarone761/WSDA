//Livelli forniture da XML
let xmlDoc = null;

async function scaricaDati() {
    try {
        const response = await fetch('/info_distributori');
        const str = await response.text();
        xmlDoc = new DOMParser().parseFromString(str, "application/xml");
    } catch (errore) {
        console.log(errore);
    }
}

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
        const stato = componente.querySelector('stato').innerHTML === 'OK' ? 'OK' : 'GUASTO';
        const icona = stato === 'OK' ? '✓' : 'x';
        const classe = stato === 'OK' ? 'guasto-ok' : 'guasto-errore';

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

function aggiornaID(id) { //aggiungi anche lo stato
    const stato = xmlDoc.getElementById(id).querySelector('stato').innerHTML;
    document.querySelector('#ID').innerHTML = `Distributore: ${id}<br>Stato: ${stato}`;
}

function carica_stato(id) {
    document.querySelector('#controlli-stato').style.display = 'flex';
    document.querySelector('#ripristina_forniture').style.display = 'block';
    document.querySelector('#ripristina_guasti').style.display = 'block';
}

//PULSATE CARICA STATO
document.querySelector('#load_state').addEventListener('click', async (event) => {
    event.preventDefault(); //previene refresh pagina
    await scaricaDati();

    // svuota le griglie prima ad ogni nuova pressione.
    document.querySelector('.forniture-grid').innerHTML = '';
    document.querySelector('.guasti-grid').innerHTML = '';
    document.querySelector('#controlli-stato').style.display = 'none';
    document.querySelector('#ripristina_forniture').style.display = 'none';
    document.querySelector('#ripristina_guasti').style.display = 'none';

    const id_distributore = document.querySelector('#id-distributore').value;
    if (xmlDoc.getElementById(id_distributore)) { //contrlla se esiste
        console.log(id_distributore)

        aggiornaID(id_distributore)
        carica_forniture(id_distributore)
        carica_guasti(id_distributore)
        carica_stato(id_distributore)
    } else {
        alert('ID inesistente')
    }
})

// PULSANTE RIPRISTINA FORNITURE
document.querySelector('#ripristina_forniture').addEventListener('click', async () => {
    const id_distributore = document.querySelector('#id-distributore').value;
    try {
        const response = await fetch(`/manutenzione/ripristina-forniture?idDistributore=${id_distributore}`, {
            method: 'POST',
        });
        if (response.ok) {
            alert('Forniture ripristinate!');
            // ricarica i dati per mostrare i livelli aggiornati
            document.querySelector('#load_state').click();
        }
    } catch (error) {
        console.error('Errore:', error);
        alert('Errore di connessione');
    }
});

// PULSANTE RIPRISTINA GUASTI
document.querySelector('#ripristina_guasti').addEventListener('click', async () => {
    const id_distributore = document.querySelector('#id-distributore').value;
    try {
        const response = await fetch(`/manutenzione/ripristina-guasti?idDistributore=${id_distributore}`, {
            method: 'POST',
        });
        if (response.ok) {
            alert('Guasti ripristinati!');
            // ricarica i dati per mostrare i livelli aggiornati
            document.querySelector('#load_state').click();
        }
    } catch (error) {
        console.error('Errore:', error);
    }
});

// PULSANTI PER CAMBIARE STATO
async function cambiaStato(nuovo_stato) {
    const id_distributore = document.querySelector('#id-distributore').value;
    try {
        const response = await fetch(`/manutenzione/cambia-stato?idDistributore=${id_distributore}&stato=${nuovo_stato}`, {
            method: 'POST',
        });
        if (response.ok) {
            alert('Stato aggiornato in ' + nuovo_stato + '!');
            // ricarica i dati per mostrare lo stato aggiornato
            document.querySelector('#load_state').click();
        }
    } catch (error) {
        console.error('Errore:', error);
        alert('Errore di connessione');
    }
}

document.querySelector('#change2attivo').addEventListener('click', () => cambiaStato('ATTIVO'));
document.querySelector('#change2manutenzione').addEventListener('click', () => cambiaStato('MANUTENZIONE'));
