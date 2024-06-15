var ctx = document.getElementById('temperatureGauge').getContext('2d');
        var temperatureValue = 30;
        document.getElementById('currentTemperature').textContent = `${temperatureValue}`;

        var temperatureGauge = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Temperature Level'],
                datasets: [{
                    data: [temperatureValue, 100 - temperatureValue],
                    backgroundColor: getTemperatureColor(temperatureValue),
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true,
                cutout: '80%',
                rotation: -90,
                circumference: 180,
                plugins: {
                    tooltip: {
                        enabled: false
                    }
                },
                animation: {
                    onComplete: function(animation) {
                        var temperatureSolutionText = document.getElementById('temperatureSolutionText');
                        if (temperatureValue >= 40) {
                            temperatureSolutionText.classList.add('active');
                        } else {
                            temperatureSolutionText.classList.remove('active');
                        }
                    }
                }
            }
        });

        function getTemperatureColor(value) {
            var hue = (1 - value / 60) * 120;
            return [`hsl(${hue}, 100%, 50%)`, '#d3d3d3'];
        }