let input_id = null;
let botton_connessione = document.querySelector('#conn');

//CONNESSIONE
document.querySelector('form').addEventListener('submit', (event) => {
    event.preventDefault(); // Temproaneo per l'assignment (da rimuovere in futuro). Previene il refresh della pagina

    input_id = document.querySelector('#id-distributore').value;
    if(input_id){
        alert('Connesso al distributore ' + input_id);
        botton_connessione.disabled = true; // Disabilita il bottone di connessione dopo la connessione
        botton_connessione.style.backgroundColor = '#dcd9d4';
        botton_connessione.innerHTML = 'Connesso a ' + input_id;
    }
});

//DISCONNESSIONE
document.querySelector('#disconnessione-btn').addEventListener('click', () => {
    if(input_id){
        alert('Disconnesso dal distributore ' + input_id);
        input_id = null; // Resetta l'ID dopo la disconnessione
        botton_connessione.disabled = false;
        botton_connessione.style.backgroundColor = '';
        botton_connessione.innerHTML = 'Connetti';
    }
});


//RICARICA (Versione temporanea per l'assignment)
const credito_residuo_el = document.querySelector('.credito');

function AggiornaCredito(importo){
    let nuovo_importo = parseFloat(credito_residuo_el.innerHTML) + importo
    credito_residuo_el.innerHTML = nuovo_importo.toFixed(2);
}

document.querySelector('#ricarica').addEventListener('click', (event) => {
    event.preventDefault(); // Temporaneo per l'assignment (da rimuovere in futuro). Previene il refresh della pagina

    const importo_ricarica_el = parseFloat( document.querySelector('#importo').value );
    if(importo_ricarica_el > 0){
        AggiornaCredito(importo_ricarica_el);
        alert('Hai ricaricato €' + importo_ricarica_el);
    }

})