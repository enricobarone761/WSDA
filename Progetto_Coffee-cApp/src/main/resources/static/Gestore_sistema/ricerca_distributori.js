//RECUPERIAMO I DATI DAL FORM TODO
document.querySelector('#search-dist-btn').addEventListener('click', e=>{
    e.preventDefault();

    const dati_grezzi_form = new FormData(document.querySelector('#search-dist-form'))
    const dati = Object.fromEntries(dati_grezzi_form.entries())

    // Aggiungi lo stato dei pulsanti filtro ai dati
    const lowStockBtn = document.querySelector('#search-dist-low-stock-btn')
    const faultBtn = document.querySelector('#search-dist-fault-btn')

    dati.lowStock = lowStockBtn.classList.contains('active')
    dati.hasFault = faultBtn.classList.contains('active')

    console.log(dati)
})

// Toggle per il pulsante forniture basse
document.querySelector('#search-dist-low-stock-btn').addEventListener('click', e => {
    e.currentTarget.classList.toggle('active')
})

// Toggle per il pulsante guasti
document.querySelector('#search-dist-fault-btn').addEventListener('click', e => {
    e.currentTarget.classList.toggle('active')
})