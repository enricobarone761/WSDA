let xmlDocDist = null;
let lista_id_distributori = null

function scaricaDatiDistributori() {
    fetch('/info_distributori')
        .then(response => response.text())
        .then(str => {

            xmlDocDist = new DOMParser().parseFromString(str, "application/xml");

            //qui estraggo soltanto la lista di ID da xml su cui iterare. Quando serve, la logica di estrazione dei dati avviene dentro aggiungi_html_distributore()
            lista_id_distributori = Array.from(xmlDocDist.getElementsByTagName('distributore')).map(e => e.id)

            // la creazione di questo evento personalizzato mi permette di fare ricerca sui distributori aspettando che vengano prima caricati in memoria
            window.dispatchEvent(new CustomEvent('distributoriCaricati'));
        })
        .then(carica_distributori)
        .catch(errore => console.log(errore));
}
scaricaDatiDistributori() //primo avvio


function aggiungi_html_distributore(id) {

    const distributore_el_xml = xmlDocDist.getElementById(id)

    //funzione forse superflua che mi aiuta per compilare in modo più leggibile l'HTML qui sotto
    //recupera lo stato di forniture/guasti da XML in modo pulito e semplice
    const distributore = function(categoria, tipo) {
        const elementi = distributore_el_xml.querySelectorAll(categoria);
        let risultato = null;
        elementi.forEach(e => {
            if (e.querySelector('nome').textContent === tipo) {
                if (categoria === 'fornitura') {
                    risultato = e.querySelector('livello').textContent;
                } else { // se componente
                    risultato = e.querySelector('stato').textContent;
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

    const stato_attuale = distributore_el_xml.querySelector('stato').textContent;
    const testo_pulsante = stato_attuale === 'ATTIVO' ? 'Disattiva' : 'Attiva';


    const div = document.createElement('div')
    div.classList.add('distributore-card')
    div.id = id
    div.innerHTML =
        `<div class="distributore-header">
            <div style="display: flex; align-items: center; justify-content: space-between;">
                <span class="distributore-id">${id}</span>
                <span class="distributore-stato ${stato_attuale.toLowerCase()}">${stato_attuale}</span>
            </div>
            <div class="distributore-posizione">
                ${distributore_el_xml.querySelector('via').textContent} - 
                Piano ${distributore_el_xml.querySelector('piano').textContent}
            </div>
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
                <div class="guasto-item ${ distributore('componente','Pompa Acqua').toLowerCase() }">Pompa Acqua</div>
                <div class="guasto-item ${ distributore('componente','Riscaldatore').toLowerCase() }">Riscaldatore</div>
                <div class="guasto-item ${ distributore('componente','Erogatore').toLowerCase() }">Erogatore</div>
                <div class="guasto-item ${ distributore('componente','Display').toLowerCase() }">Display</div>
                <div class="guasto-item ${ distributore('componente','Gettoniera').toLowerCase() }">Gettoniera</div>
                <div class="guasto-item ${ distributore('componente','Macina Caffè').toLowerCase() }">Macina Caffè</div>
            </div>
        </div>

        <div class="distributore-actions">
            <button class="btn-piccolo btn-disattiva">${testo_pulsante}</button>
            <button class="btn-piccolo btn-danger">Rimuovi</button>
        </div>`;


    //LOGICA PULSANTE RIMUOVI
    div.querySelector('.btn-rimuovi').addEventListener('click', _ => {
        fetch(`/gestione-distributori/rimuovi?id=${id}`, {
            method: 'DELETE'
        })
        .then(async response => {
            if (response.ok) {
                alert('Hai rimosso ' + id )
                div.closest('.distributore-card').remove();
            } else {
                const errorMessage = await response.text();
                alert(errorMessage);
            }
        })
        .catch(error => console.error('Errore:', error));
    })


    //LOGICA PULSANTE ATTIVA/DISATTIVA
    div.querySelector('.btn-disattiva').addEventListener('click', _ => {
        const statoSpan = div.querySelector('.distributore-stato');
        const pulsante = div.querySelector('.btn-disattiva');

        const nuovoStato = statoSpan.textContent === 'ATTIVO' ? 'INATTIVO' : 'ATTIVO';

        fetch(`/manutenzione/cambia-stato?idDistributore=${id}&stato=${nuovoStato}`, {
            method: 'POST'
        })
            .then(async response => {
                if (!response.ok) {
                    const errorMessage = await response.text();
                    throw new Error(errorMessage);
                }

                statoSpan.className = `distributore-stato ${nuovoStato.toLowerCase()}`;

                const isDisattivato = nuovoStato === 'INATTIVO';
                statoSpan.textContent = nuovoStato;
                pulsante.textContent = isDisattivato ? 'Attiva' : 'Disattiva';

                alert(`Hai ${isDisattivato ? 'disattivato' : 'attivato'} il distributore ${id}`);
            })
            .catch((e) => alert(e.message));
    });

    //
    document.querySelector('.distributori-list').prepend(div)
    console.log(div)
}

function carica_distributori() {
    document.querySelector('.distributori-list').innerHTML = '';
    for( let distributore of lista_id_distributori ) {
        aggiungi_html_distributore( distributore )
    }
}

//LOGICA PULSANTE AGGIUNGI
document.querySelector('#add-dist-btn').addEventListener('click', listener => {
    listener.preventDefault();
    console.log('adsdasda');

    const posizione = {
        'via': document.querySelector('#add-distributore-via').value,
        'piano': document.querySelector('#add-distributore-piano').value
    };
    const nuovo_id = document.querySelector('#add-distributore-id').value;

    if (document.querySelector('#add-dist-form').reportValidity()) {
        if (!lista_id_distributori.includes(nuovo_id)) {
            
            const nuovoDistributore = {
                id_distributore: nuovo_id,
                via: posizione.via,
                piano: posizione.piano
            };

            fetch('/gestione-distributori/aggiungi', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(nuovoDistributore)
            })
            .then(async response => {
                if (response.ok) {
                    alert("Aggiunto "+ nuovo_id)
                    document.querySelector('#add-dist-form').reset();
                    scaricaDatiDistributori();
                } else {
                    const errorMessage = await response.text();
                    alert(errorMessage);
                }
            })
            .catch(error => console.error('Errore:', error));

        } else {
            alert('Errore: ID distributore già esistente');
        }
    }
});