var map = L.map('map').setView([27.7172, 85.3240], 13);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        }).addTo(map);

        // Function to update markers on the map
        function updateMarkers(data) {
            // Clear existing markers
            map.eachLayer(function(layer) {
                if (layer instanceof L.Marker) {
                    map.removeLayer(layer);
                }
            });

            // Add new markers based on data
            data.forEach(function(sensor) {
                L.marker(sensor.latlng, { title: sensor.title }).addTo(map)
                    .bindPopup(sensor.title);
            });
        }

        // Function to fetch sensor data from localStorage
        function fetchSensorData() {
            var sensorData = localStorage.getItem('sensorData');
            if (sensorData) {
                return JSON.parse(sensorData);
            } else {
                return [];
            }
        }

        // Initial update of markers
        updateMarkers(fetchSensorData());

        // Listen for changes in localStorage and update markers accordingly
        window.addEventListener('storage', function(event) {
            if (event.key === 'sensorData') {
                updateMarkers(JSON.parse(event.newValue));
            }
        });