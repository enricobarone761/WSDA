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

        
        const cards = document.querySelectorAll('.distributore-card');
        let lista_distributori_validi = new Set();

        cards.forEach(card => {
            const id = card.id;
            const posizioneText = card.querySelector('.distributore-posizione').textContent.toLowerCase();
            const stato = card.querySelector('.distributore-stato').textContent.toLowerCase();
            
            let isValido = true;

            //controllo id
            if(criteri_di_ricerca['distributore-id'] && criteri_di_ricerca['distributore-id'].toUpperCase() !== id){
                isValido = false;
            }

            //controllo via (cerchiamo nel testo della posizione che include via e piano)
            if(isValido && criteri_di_ricerca['distributore-via'] && !posizioneText.includes(criteri_di_ricerca['distributore-via'].toLowerCase())){
                isValido = false;
            }

            //controllo stato
            if(isValido && criteri_di_ricerca['distributore-stato'] && criteri_di_ricerca['distributore-stato'] !== stato){
                isValido = false;
            }

            //controllo forniture
            if(isValido && criteri_di_ricerca['fornitura_bassa']){
                const percentuali = Array.from(card.querySelectorAll('.percentuale')).map(p => parseInt(p.textContent));
                if(!percentuali.some(p => p < 50)){
                    isValido = false;
                }
            }

            //controllo guasti
            if(isValido && criteri_di_ricerca['guasto_presente']){
                const guasti = card.querySelectorAll('.guasto-item.guasto');
                if(guasti.length === 0){
                    isValido = false;
                }
            }

            if(isValido) {
                lista_distributori_validi.add(id);
            }
        });

        console.log(lista_distributori_validi)

        //.hide() con jquery di tutti i distributori che NON SONO nella lista / mostriamo il risultato della query di ricerca
        if(lista_distributori_validi.size === 0){
            alert('Nessun distributore trovato con questi criteri di ricerca')
            document.querySelector('#search-dist-form').reset()
            document.querySelector('#search-dist-low-stock-btn').classList.remove('active');
            document.querySelector('#search-dist-fault-btn').classList.remove('active');
        }else{
            document.querySelectorAll('.distributore-card').forEach(e=>{
                if(!lista_distributori_validi.has(e.id)){
                    $(`#${e.id}`).hide(10);
                }
            })
        }
    })
})
