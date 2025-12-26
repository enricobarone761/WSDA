function sendHeartbeat() {
    // Recuperiamo l'ID del distributore. TODO da rivedere
    // In un'applicazione reale potrebbe essere salvato in un cookie, localStorage o passato come parametro.
    // Per ora usiamo un ID di esempio coerente con il formato CA001 visto nel progetto.
    const distributoreId = "CA001"; 

    // La chiamata punta all'endpoint /heartbeat gestito dalla Servlet esterna
    fetch(`http://localhost:8080/heartbeat_service_war_exploded/heartbeat?id=${distributoreId}`, {
        method: 'POST'
    })
    .then(response => {
        if (!response.ok) {
            console.error('Errore durante l\'invio dell\'heartbeat alla Servlet');
        } else {
            console.log('Heartbeat inviato con successo alla Servlet');
        }
    })
    .catch(error => {
        console.error('Errore di rete durante l\'invio dell\'heartbeat:', error);
    });
}

// Invia l'heartbeat ogni 60 secondi
setInterval(sendHeartbeat, 5000000);

// Invia un heartbeat iniziale al caricamento della pagina
//sendHeartbeat();
