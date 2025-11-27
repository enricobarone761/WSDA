let xmlDocDist = null;
fetch('../XML/xml-distributori.xml')
    .then(response => response.text())
    .then(str => {
        xmlDocDist = new DOMParser().parseFromString(str, "application/xml");
    })
    .then(carica_distributori)
    .catch(errore => console.log(errore));


function aggiungi_html_distributore(id) {

    //funzione forse superflua che mi aiuta per compilare in modo più leggibile l'HTML qui sotto
    //recupera lo stato di forniture/guasti da XML in modo pulito e semplice
    const distributore = function(categoria, tipo) {
        const elementi = xmlDocDist.getElementById(id).querySelectorAll(categoria);
        let risultato = null;
        elementi.forEach(e => {
            if(e.querySelector('nome').textContent === tipo) {
                if(categoria === 'fornitura') {
                    risultato = e.querySelector('livello').innerHTML;
                } else { // se componente
                    risultato = e.querySelector('stato').innerHTML;
                }
            }
        });
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


    const div = document.createElement('div')
    div.classList.add('distributore-card')
    div.innerHTML =
        `<div class="distributore-header">
            <div>
                <span class="distributore-id">${id}</span>
                <span class="distributore-stato attivo">${xmlDocDist.getElementById(id).querySelector('stato').innerHTML}</span>
            </div>
            <div class="distributore-posizione">Edificio ${xmlDocDist.getElementById(id).querySelector('edificio').innerHTML} - Piano ${xmlDocDist.getElementById(id).querySelector('piano').innerHTML} </div>
        </div>

        <div class="forniture-section">
            <h4>Livelli Forniture</h4>
            <div class="fornitura-item">
                <span>Caffè</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Caffè'))}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Caffè')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Latte</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Latte'))}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Latte')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Zucchero</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Zucchero'))}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Zucchero')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Thè</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Thè'))}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Thè')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Cioccolato</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Cioccolato'))}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Cioccolato')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Bicchieri</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="${getBarraStyle(distributore('fornitura','Bicchieri'))}"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Bicchieri')}%</span>
            </div>
        </div>

        <div class="guasti-section">
            <h4>Stato Componenti</h4>
            <div class="guasti-grid">
                <div class="guasto-item ${distributore('componente','Pompa Acqua').toLowerCase()}"> Pompa Acqua</div>
                <div class="guasto-item ${distributore('componente','Riscaldatore').toLowerCase()}"> Riscaldatore</div>
                <div class="guasto-item ${distributore('componente','Erogatore').toLowerCase()}"> Erogatore</div>
                <div class="guasto-item ${distributore('componente','Display').toLowerCase()}"> Display</div>
                <div class="guasto-item ${distributore('componente','Gettoniera').toLowerCase()}"> Gettoniera</div>
                <div class="guasto-item ${distributore('componente','Macina Caffè').toLowerCase()}"> Macina Caffè</div>
            </div>
        </div>

        <div class="distributore-actions">
            <button class="btn-piccolo btn-disattiva">Disattiva</button>
            <button class="btn-piccolo btn-rimuovi">Rimuovi</button>
        </div>`;


    //LOGICA PULSANTE RIMUOVI
    div.querySelector('.btn-rimuovi').addEventListener('click', listener => {
        alert('Hai rimosso ' + id )
        div.closest('.distributore-card').remove();
    })


    //LOGICA PULSANTE ATTIVA/DISATTIVA TODO
    div.querySelector('.btn-disattiva').addEventListener('click', listener => {
        alert('Hai disattivato ' + id)

    })

    document.querySelector('.distributori-list').appendChild(div)
    console.log(div)
}

function carica_distributori(){
    const distributori =Array.from( xmlDocDist.getElementsByTagName('distributore') ).map(e => e.id)

    for( let distributore of distributori ) {
        aggiungi_html_distributore( distributore )
    }
}