let xmlDocDist = null;
let lista_id_distributori = null
fetch('../XML(assignment precedenti)/xml-distributori.xml')
    .then(response => response.text())
    .then(str => {

        xmlDocDist = new DOMParser().parseFromString(str, "application/xml");

        //qui estraggo soltanto la lista di ID da xml su cui iterare. Quando serve, la logica di estrazione dei dati avviene dentro aggiungi_html_distributore()
        lista_id_distributori = Array.from( xmlDocDist.getElementsByTagName('distributore') ).map(e => e.id)

        // la creazione di questo evento personalizzato mi permette di fare ricerca sui distributori aspettando che vengano prima caricati in memoria
        window.dispatchEvent(new CustomEvent('distributoriCaricati'));
    })
    .then(carica_distributori)
    .catch(errore => console.log(errore));


function aggiungi_html_distributore(id, posizione) {

    const distributore_el_xml = xmlDocDist.getElementById(id)

    //funzione forse superflua che mi aiuta per compilare in modo più leggibile l'HTML qui sotto
    //recupera lo stato di forniture/guasti da XML in modo pulito e semplice
    const distributore = function(categoria, tipo) {
        const elementi = distributore_el_xml?.querySelectorAll(categoria);
        let risultato = null;
        if(elementi != null){
            elementi.forEach(e => {
                if (e.querySelector('nome').textContent === tipo) {
                    if (categoria === 'fornitura') {
                        risultato = e.querySelector('livello').innerHTML;
                    } else { // se componente
                        risultato = e.querySelector('stato').innerHTML;
                    }
                }
            });
        }
        return risultato;
    };

    //funzione analoga a quella sopra che restituisce il giusto style per la barra di stato forniture/guasti
    function getBarraStyle(livello) {
        let colore;
        if (livello >= 60) {
            colore = '#4caf50'; // verde
        } else if (livello >= 30) {
            colore = '#ff9800'; // arancione
        } else {
            colore = '#f44336'; // rosso
        }
        return `width: ${livello}%; background: ${colore};`;
    }

    const stato_pulsante_card = function(){
        if(distributore_el_xml?.querySelector('stato').textContent === 'Attivo'){
            return 'Disattiva'
        }else{
            return 'Attiva'
        }
    }


    const div = document.createElement('div')
    div.classList.add('distributore-card')
    div.id = id
    div.innerHTML =
        `<div class="distributore-header">
            <div>
                <span class="distributore-id">${id}</span>
                <span class="distributore-stato ${(distributore_el_xml?.querySelector('stato')?.innerHTML ?? 'Inattivo').toLowerCase()}">${distributore_el_xml?.querySelector('stato')?.innerHTML ?? 'Inattivo'}</span>
            </div>
            <div class="distributore-posizione">
                Edificio ${distributore_el_xml?.querySelector('edificio')?.innerHTML ?? posizione?.edificio} - 
                Piano ${distributore_el_xml?.querySelector('piano')?.innerHTML ?? posizione?.piano}
            </div>
        </div>

        <div class="forniture-section">
            <h4>Livelli Forniture</h4>
            <div class="fornitura-item">
                <span>Caffè</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Caffè') ?? 100)}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Caffè') ?? 100}%</span>
            </div>
            <div class="fornitura-item">
                <span>Latte</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Latte') ?? 100)}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Latte') ?? 100}%</span>
            </div>
            <div class="fornitura-item">
                <span>Zucchero</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Zucchero') ?? 100)}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Zucchero') ?? 100}%</span>
            </div>
            <div class="fornitura-item">
                <span>Thè</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Thè') ?? 100)}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Thè') ?? 100}%</span>
            </div>
            <div class="fornitura-item">
                <span>Cioccolato</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Cioccolato') ?? 100)}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Cioccolato') ?? 100}%</span>
            </div>
            <div class="fornitura-item">
                <span>Bicchieri</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Bicchieri') ?? 100)}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Bicchieri') ?? 100}%</span>
            </div>
        </div>

        <div class="guasti-section">
            <h4>Stato Componenti</h4>
            <div class="guasti-grid">
                <div class="guasto-item ${ (distributore('componente','Pompa Acqua') ?? 'ok').toLowerCase() }">Pompa Acqua</div>
                <div class="guasto-item ${ (distributore('componente','Riscaldatore') ?? 'ok').toLowerCase() }">Riscaldatore</div>
                <div class="guasto-item ${ (distributore('componente','Erogatore') ?? 'ok').toLowerCase() }">Erogatore</div>
                <div class="guasto-item ${ (distributore('componente','Display') ?? 'ok').toLowerCase() }">Display</div>
                <div class="guasto-item ${ (distributore('componente','Gettoniera') ?? 'ok').toLowerCase() }">Gettoniera</div>
                <div class="guasto-item ${ (distributore('componente','Macina Caffè') ?? 'ok').toLowerCase() }">Macina Caffè</div>
            </div>
        </div>

        <div class="distributore-actions">
            <button class="btn-piccolo btn-disattiva">${stato_pulsante_card()}</button>
            <button class="btn-piccolo btn-rimuovi">Rimuovi</button>
        </div>`;


    //LOGICA PULSANTE RIMUOVI
    div.querySelector('.btn-rimuovi').addEventListener('click', listener => {
        alert('Hai rimosso ' + id )
        div.closest('.distributore-card').remove();
    })


    //LOGICA PULSANTE ATTIVA/DISATTIVA
    div.querySelector('.btn-disattiva').addEventListener('click', listener => {
        const statoSpan = div.querySelector('.distributore-stato');
        const pulsante = div.querySelector('.btn-disattiva');

        if (statoSpan.classList.contains('attivo')) {
            // Disattiva il distributore
            statoSpan.classList.remove('attivo');
            statoSpan.classList.add('inattivo');
            statoSpan.textContent = 'Inattivo';
            pulsante.textContent = 'Attiva';
            alert('Hai disattivato ' + id);
        } else {
            // Attiva il distributore
            statoSpan.classList.remove('inattivo');
            statoSpan.classList.add('attivo');
            statoSpan.textContent = 'Attivo';
            pulsante.textContent = 'Disattiva';
            alert('Hai attivato ' + id);
        }
    })

    //
    document.querySelector('.distributori-list').prepend(div)
    console.log(div)
}

function carica_distributori(){
    for( let distributore of lista_id_distributori ) {
        aggiungi_html_distributore( distributore )
    }
}

//LOGICA PULSANTE AGGIUNGI
document.querySelector('#add-dist-btn').addEventListener('click', listener => {
    listener.preventDefault() //Temporaneo per l'assignment
    console.log('adsdasda');

    const posizione = {
        'edificio': document.querySelector('#add-distributore-piano').value,
        'piano': document.querySelector('#add-distributore-edificio').value
    };

    const nuovo_id = document.querySelector('#add-distributore-id').value;
    if (document.querySelector('#add-dist-form').reportValidity()) {
        if (!lista_id_distributori.includes(nuovo_id)) {
            aggiungi_html_distributore(nuovo_id, posizione);
            document.querySelector('#add-dist-form').reset();
        } else {
            alert('Errore: ID distributore già esistente');
        }
    }
});