let credito_residuo_el = document.querySelector('.credit');
function AggiornaCredito(importo){
    let nuovo_importo = credito_residuo_el.innerHTML - importo
    credito_residuo_el.innerHTML = nuovo_importo.toFixed(2);
}

let prezzo = null;
document.querySelectorAll('.btn-bevanda').forEach(btn => {
    btn.addEventListener('click', e => {

        const card = e.target.closest('.bevanda-card');
        prezzo = parseFloat(card.querySelector('.bevanda-prezzo').innerText.replace('€ ', '' ).replace(',' , '.'));
        console.log('prezzo selezionato ' + prezzo)
    });
});

document.querySelector('.btn-conferma').addEventListener('click',()=>{
    if (prezzo > credito_residuo_el.innerHTML){
        alert("Credito insufficiente! Si prega di ricaricare il credito.")
        return;
    }
    if (prezzo === null){
        alert("Nessuna bevanda selezionata")
        return;
    }
    alert("Ordine Confermato! Hai speso € " + prezzo);
    AggiornaCredito(prezzo);
    prezzo = null;
})
