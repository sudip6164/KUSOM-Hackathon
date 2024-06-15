var ctx = document.getElementById('pollutionGauge').getContext('2d');
        var value = parseFloat(document.getElementById('currentAir').textContent); // Set the pollution value here (0 to 100)

        var pollutionGauge = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Pollution Level'],
                datasets: [{
                    data: [value, 200 - value],
                    backgroundColor: ['blue', '#d3d3d3'],
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
                        var solutionText = document.getElementById('solutionText');
                        if (value >= 0) {
                            solutionText.classList.add('active');
                        } else {
                            solutionText.classList.remove('active');
                        }
                    }
                }
            }
        });