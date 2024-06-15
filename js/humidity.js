var ctx = document.getElementById('soundGauge').getContext('2d');
        var soundValue = parseFloat(document.getElementById('currentHumidity').textContent); // Set the sound pollution value here (0 to 100)

        var soundGauge = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Sound Level'],
                datasets: [{
                    data: [soundValue, 100 - soundValue],
                    backgroundColor: ['green', '#d3d3d3'],
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
                        var soundSolutionText = document.getElementById('soundSolutionText');
                        if (soundValue >= 0) {
                            soundSolutionText.classList.add('active');
                        } else {
                            soundSolutionText.classList.remove('active');
                        }
                    }
                }
            }
        });