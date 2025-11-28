// aspetta che i distributori siano caricati dal file gestione_distributori.js
window.addEventListener('distributoriCaricati', ()=>{

    document.querySelector('#search-dist-btn').addEventListener('click', e=>{
        e.preventDefault();

        //ad ogni pressione ripristiniamo tutte le card per poi eseguire la logica di ricerca
        document.querySelectorAll('.distributore-card').forEach(e=>{
                $(`#${e.id}`).show(10);
        })

        const dati_grezzi_form = new FormData(document.querySelector('#search-dist-form'))
        const criteri_di_ricerca = Object.fromEntries(dati_grezzi_form.entries())

        // stato dei pulsanti filtro ai dati
        criteri_di_ricerca.fornitura_bassa = document.querySelector('#search-dist-low-stock-btn').classList.contains('active')
        criteri_di_ricerca.guasto_presente = document.querySelector('#search-dist-fault-btn').classList.contains('active')
        console.log(criteri_di_ricerca)


        const lista_distributori =  xmlDocDist.querySelectorAll('distributore');
        let lista_distributori_validi = new Set(lista_id_distributori)

        lista_distributori.forEach(e=>{
            //controllo id
            if(criteri_di_ricerca['distributore-id'] && criteri_di_ricerca['distributore-id'] !== e.id ){
                lista_distributori_validi.delete(e.id)
            }

            //controllo edificio
            if(criteri_di_ricerca['distributore-edificio'] && criteri_di_ricerca['distributore-edificio'].toLowerCase() !== e.querySelector('edificio').textContent.toLowerCase() ){
                lista_distributori_validi.delete(e.id)
            }

            //controllo stato
            if(criteri_di_ricerca['distributore-stato'] && criteri_di_ricerca['distributore-stato'] !== e.querySelector('stato').textContent.toLowerCase()){
                lista_distributori_validi.delete(e.id)
            }

            //controllo forniture
            let esiste = false
            e.querySelectorAll('fornitura').forEach(forn=>{
                if(criteri_di_ricerca['fornitura_bassa'] && forn.querySelector('livello').textContent < 50){
                    esiste = true
                }
            })
            if(!esiste && criteri_di_ricerca['fornitura_bassa']){lista_distributori_validi.delete(e.id)}

            //controllo guasti
            esiste = false
            e.querySelectorAll('componente').forEach(comp=>{
                if(criteri_di_ricerca['guasto_presente'] && comp.querySelector('stato').textContent === 'Guasto'){
                    esiste = true
                }
            })
            if(!esiste && criteri_di_ricerca['guasto_presente']){lista_distributori_validi.delete(e.id)}

        })

        console.log(lista_distributori_validi)

        //.hide() con jquery di tutti i distributori che NON SONO nella lista / mostriamo il risultato della query di ricerca
        document.querySelectorAll('.distributore-card').forEach(e=>{
            if(!lista_distributori_validi.has(e.id)){
                $(`#${e.id}`).hide(10);
            }
        })


    })

})

