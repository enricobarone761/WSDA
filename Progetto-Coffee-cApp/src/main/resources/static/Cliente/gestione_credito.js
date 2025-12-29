document.addEventListener('DOMContentLoaded', () => {
    const idUtente = document.querySelector('#id_utente_hidden').value;
    const creditoSpan = document.querySelector('.credito');
    const formRicarica = document.querySelector('#form-ricarica');

    // funzione per aggiornare il credito a schermo
    //ho deciso di usare un polling continuo del credito come lo schermo distributore per mantenerlo aggiornato
    //ad ogni erogazione
    const aggiornaCredito = () => {
        if (!idUtente) return;

        fetch(`/credito?id_utente=${idUtente}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Errore nel recupero del credito');
            })
            .then(credito => {
                creditoSpan.textContent = parseFloat(credito).toFixed(2);
            })
            .catch(error => console.error(error));
    };

    // polling ogni 5 secondi
    setInterval(aggiornaCredito, 5000);

    // PULSANTE RICARICA
    if (formRicarica) {
        formRicarica.addEventListener('submit', (event) => {
            event.preventDefault();
            const importo = document.querySelector('#importo').value;

            if (idUtente && importo) {
                fetch(`/ricarica?id_utente=${idUtente}&importo=${importo}`, {
                    method: 'POST'
                })
                .then(response => {
                    if (response.ok) {
                        alert('Ricarica effettuata');
                        // ho separato l'invio dall'aggiornamento a schermo utilizzando
                        // lo stesso approccio dell'interfaccia distributore
                        document.querySelector('#importo').value = '';
                    }
                })
                .catch(error => {
                    console.error(error);
                });
            }
        });
    }
});
