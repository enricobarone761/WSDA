let xmlDocDist = null;
fetch('../XML/xml-distributori.xml')
    .then(response => response.text())
    .then(str => {
        xmlDocDist = new DOMParser().parseFromString(str, "application/xml");
    })
    .then(aggiungi_html_distributore)
    .catch(errore => console.log(errore));


function aggiungi_html_distributore(id){

    const distributore = function(categoria, tipo) {
             const elementi = xmlDocDist.getElementById('CA001').querySelectorAll(categoria);
             let risultato = null;
             elementi.forEach(e => {
                 if (e.querySelector('nome').textContent === tipo) {
                     if (categoria === 'fornitura') {
                         risultato = e.querySelector('livello').innerHTML;
                     } else { // se componente
                         risultato = e.querySelector('stato').innerHTML;
                     }
                 }
             });
             return risultato;
         };

    const div = document.createElement('div')
    div.classList.add('distributore-card')
    div.innerHTML =
       `<div class="distributore-header">
            <div>
                <span class="distributore-id">${id}</span>
                <span class="distributore-stato attivo">Attivo</span>
            </div>
            <div class="distributore-posizione">Edificio 9 - Piano 2</div>
        </div>

        <div class="forniture-section">
            <h4>Livelli Forniture</h4>
            <div class="fornitura-item">
                <span>Caffè</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="width: 85%; background: #4caf50;"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Caffè')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Latte</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="width: 70%; background: #4caf50;"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Latte')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Zucchero</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="width: 90%; background: #4caf50;"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Zucchero')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Thè</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="width: 60%; background: #4caf50;"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Thè')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Cioccolato</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="width: 75%; background: #4caf50;"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Cioccolato')}%</span>
            </div>
            <div class="fornitura-item">
                <span>Bicchieri</span>
                <div class="barra-container">
                    <div class="barra-progresso" style="width: 80%; background: #4caf50;"></div>
                </div>
                <span class="percentuale">${distributore('fornitura','Bicchieri')}%</span>
            </div>
        </div>

        <div class="guasti-section">
            <h4>Stato Componenti</h4>
            <div class="guasti-grid">
                <div class="guasto-item ok">✓ Pompa Acqua</div>
                <div class="guasto-item errore">x Riscaldatore</div>
                <div class="guasto-item ok">✓ Erogatore</div>
                <div class="guasto-item ok">✓ Display</div>
                <div class="guasto-item warning">! Gettoniera</div>
                <div class="guasto-item ok">✓ Macina Caffè</div>
            </div>
        </div>

        <div class="distributore-actions">
            <button class="btn-piccolo btn-disattiva">Disattiva</button>
            <button class="btn-piccolo btn-rimuovi">Rimuovi</button>
        </div>`;

    //LOGICA PULSANTE RIMUOVI TODO
    div.querySelector('.rmv-addetto').addEventListener('click', listener => {
        alert('Hai rimosso ' + addetto.nome + ' ' + addetto.cognome)
        div.closest('.manutentore-card').remove();
    })

    //LOGICA PULSANTE ATTIVA/DISATTIVA TODO
    div.querySelector('.rmv-addetto').addEventListener('click', listener => {
        alert('Hai rimosso ' + addetto.nome + ' ' + addetto.cognome)
        div.closest('.manutentore-card').remove();
    })

    document.querySelector('.distributori-list').appendChild(div)
    console.log(addetto)

}