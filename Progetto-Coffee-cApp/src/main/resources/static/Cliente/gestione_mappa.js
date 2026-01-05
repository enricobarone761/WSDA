document.addEventListener('DOMContentLoaded', function() {
    const mapContainer = document.getElementById('map-container');
    let map = null;

    if (mapContainer) {
        mapContainer.style.display = 'block';

        // mappa centrata su Palermo
        map = L.map('map').setView([38.1157, 13.3615], 13);

        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(map);

        // la fetch viene fatta all'altro progetto Jakarta come richiesto dall'assignment
        const URLMappa = 'http://localhost:8081/heartbeat_service_war_exploded/distributori';

        fetch(URLMappa)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                data.forEach(punto => {
                    if (punto.lat !== undefined && punto.lon !== undefined) {
                        console.log(punto);
                        const marker = L.marker([punto.lat, punto.lon]).addTo(map);

                        // aggiunta popup distributore
                        marker.bindPopup(`
                            <b>ID:</b> ${punto.id}<br>
                            <b>Stato:</b> ${punto.stato}<br>
                        `);
                    }
                });
            })
            .catch(error => {
                console.error('Errore:', error);
            });

        setTimeout(() => {
            map.invalidateSize();
        }, 200);
    }
});
