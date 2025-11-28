// aspetta che i distributori siano caricati dal file gestione_distributori.js
window.addEventListener('distributoriCaricati', ()=>{

    document.querySelector('#search-dist-btn').addEventListener('click', e=>{
        e.preventDefault();

        const dati_grezzi_form = new FormData(document.querySelector('#search-dist-form'))
        const criteri_di_ricerca = Object.fromEntries(dati_grezzi_form.entries())

        // stato dei pulsanti filtro ai dati
        criteri_di_ricerca.fornitura_bassa = document.querySelector('#search-dist-low-stock-btn').classList.contains('active')
        criteri_di_ricerca.guasto_presente = document.querySelector('#search-dist-fault-btn').classList.contains('active')
        console.log(criteri_di_ricerca)


        const lista_distributori =  xmlDocDist.querySelectorAll('distributore');
        let lista_distributori_validi = new Set()

        lista_distributori.forEach(e=>{
            //controllo id
            if(criteri_di_ricerca['distributore-id'] && criteri_di_ricerca['distributore-id'] === e.id ){
                lista_distributori_validi.add(e.id)
            }

            //controllo edificio
            if(criteri_di_ricerca['distributore-edificio'] && criteri_di_ricerca['distributore-edificio'] === e.edificio ){
                lista_distributori_validi.add(e.id)
            }

            //controllo stato
            if(criteri_di_ricerca['distributore-stato'] && criteri_di_ricerca['distributore-stato'] === e.querySelector('stato').innerHTML.toLowerCase()){
                lista_distributori_validi.add(e.id)
            }

            //controllo forniture
            e.querySelectorAll('fornitura').forEach(forn=>{
                if(criteri_di_ricerca['fornitura_bassa'] && forn.querySelector('livello').innerHTML <= 50){
                    lista_distributori_validi.add(e.id)
                }
            })

            console.log(lista_distributori_validi)
            //controllo guasti
            e.querySelectorAll('componente').forEach(comp=>{
                if(criteri_di_ricerca['guasto_presente'] && comp.querySelector('stato').innerHTML === 'Guasto'){
                    lista_distributori_validi.add(e.id)
                }
            })


        })

    })

})



