//TODO DA RIMUOVERE PRIMA DELLA CONSEGNA, SERVE SOLO PER SIMULARE GLI HB DI TUTTI I DISTRIBUTORI

document.addEventListener('DOMContentLoaded',()=>{

    let xmlDocDist = null;
    let lista_id_distributori = null

    function scaricaDatiDistributori() {
        fetch('http://localhost:8080/info_distributori')
            .then(response => response.text())
            .then(str => {

                xmlDocDist = new DOMParser().parseFromString(str, "application/xml");

                //qui estraggo soltanto la lista di ID da xml su cui iterare. Quando serve, la logica di estrazione dei dati avviene dentro aggiungi_html_distributore()
                lista_id_distributori = Array.from(xmlDocDist.getElementsByTagName('distributore')).map(e => e.id.toString())

                // la creazione di questo evento personalizzato mi permette di fare ricerca sui distributori aspettando che vengano prima caricati in memoria
                window.dispatchEvent(new CustomEvent('distributoriCaricati'));
            })
            .then(carica_distributori)
            .catch(errore => console.log(errore));
    }
    scaricaDatiDistributori() //primo avvio

    function sendHeartbeat(id) {

        // La chiamata punta all'endpoint /heartbeat gestito dalla Servlet esterna
        fetch(`http://localhost:8081/heartbeat_service_war_exploded/heartbeat?id=${id}`, {
            method: 'POST'
        });
    }

    function mandaHB() {
        lista_id_distributori.forEach(e => {
            sendHeartbeat(e)
        })
    }

    setInterval(mandaHB, 60000);
})
