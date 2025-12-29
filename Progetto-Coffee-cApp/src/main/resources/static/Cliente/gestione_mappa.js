document.addEventListener('DOMContentLoaded', function() {
    const btnMappa = document.getElementById('btn-mostra-mappa');
    const mapContainer = document.getElementById('map-container');
    let map = null;

    if (btnMappa && mapContainer) {
        btnMappa.addEventListener('click', function() {
            if (mapContainer.style.display === 'none') {
                mapContainer.style.display = 'block';
                btnMappa.textContent = "Nascondi Mappa";

                if (!map) {
                    // Inizializza la mappa centrata su Palermo (o altra posizione di default)
                    map = L.map('map').setView([38.1157, 13.3615], 13);

                    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        maxZoom: 19,
                        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                    }).addTo(map);

                    // TODO: Sostituire con l'URL reale dell'endpoint
                    // L'endpoint deve restituire un JSON array di oggetti con proprietà 'lat' e 'lon'
                    const endpointUrl = 'https://example.com/api/distributori'; 

                    fetch(endpointUrl)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.json();
                        })
                        .then(data => {
                            data.forEach(punto => {
                                if (punto.lat && punto.lon) {
                                    L.marker([punto.lat, punto.lon]).addTo(map);
                                }
                            });
                        })
                        .catch(error => {
                            console.error('Errore nel recupero dei dati della mappa:', error);
                            // Fallback per demo se l'endpoint non è raggiungibile
                            // L.marker([38.1157, 13.3615]).addTo(map).bindPopup('Distributore Centrale (Demo)');
                        });
                }
                
                // Forza il ricalcolo delle dimensioni della mappa
                setTimeout(() => {
                    map.invalidateSize();
                }, 200);

            } else {
                mapContainer.style.display = 'none';
                btnMappa.textContent = "Mostra Mappa Distributori";
            }
        });
    }
});