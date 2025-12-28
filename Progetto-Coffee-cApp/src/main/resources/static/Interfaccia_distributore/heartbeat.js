function sendHeartbeat() {
    const distributoreId = "CA001"; 

    // La chiamata punta all'endpoint /heartbeat gestito dalla Servlet esterna
    fetch(`http://localhost:8081/heartbeat_service_war_exploded/heartbeat?id=${distributoreId}`, {
        method: 'POST'
    })
    .then(response => {
        if (!response.ok) {
            console.error("Errore durante l'invio dell'heartbeat");
        } else {
            console.log('Heartbeat inviato con successo');
        }
    })
    .catch(error => {
        console.error("'Errore di rete durante l'invio dell'heartbeat:'", error);
    });
}

// Invia l'heartbeat ogni 60 secondi
setInterval(sendHeartbeat, 1000);

// Invia un heartbeat iniziale al caricamento della pagina
sendHeartbeat();
