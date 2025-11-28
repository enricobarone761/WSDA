//LOGICA DI RICERCA TODO


//RECUPERIAMO I DATI DAL FORM
document.querySelector('#search-dist-btn').addEventListener('click', e=>{
    e.preventDefault();

    const dati_grezzi_form = new FormData(document.querySelector('#search-dist-form'))
    const dati = Object.fromEntries(dati_grezzi_form.entries())

    // stato dei pulsanti filtro ai dati
    dati.fornitura_50 = document.querySelector('#search-dist-low-stock-btn').classList.contains('active')
    dati.guasto = document.querySelector('#search-dist-fault-btn').classList.contains('active')
    console.log(dati)
})
