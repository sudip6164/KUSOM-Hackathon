var spanElement = document.getElementById('counter');

// Retrieve the current value and parse it as an integer (assuming it represents a percentage)
var targetPercentage = parseFloat(spanElement.textContent.trim()); // Get the target percentage

var speed = 1; // Adjust this value to control the speed (in milliseconds)

var counts = setInterval(updated, speed); // Adjust the speed here

var currentPercentage = 0;

function updated() {
    let count = document.getElementById("counter");
    count.innerHTML = currentPercentage.toFixed(1) + " AQI";
    currentPercentage+=0.1;

    if (currentPercentage > targetPercentage) {
        clearInterval(counts);
        count.innerHTML = currentPercentage.toFixed(1) + " AQI";
    }
}

// Select the span element
var spanElement1 = document.getElementById('counter1');

// Retrieve the current value and parse it as an integer
var currentValue1 = parseInt(spanElement1.textContent.trim());

var speed1 = 300;
let counts1 = setInterval(updated1, speed1);
        let upto1 = 0;
        function updated1() {
            let count1 = document.getElementById("counter1");
            count1.innerHTML = ++upto1;
            if (upto1 === currentValue1) {
                clearInterval(counts1);
            }
        }

/*// Select the span element
var spanElement2 = document.getElementById('counter2');

// Retrieve the current value and parse it as an integer
var currentValue2 = parseInt(spanElement2.textContent.trim());

var speed2 = 1;
let counts2 = setInterval(updated2, speed2);
        let upto2 = 0;
        function updated2() {
            let count2 = document.getElementById("counter2");
            count2.innerHTML = ++upto2;
            if (upto2 === currentValue2) {
                clearInterval(counts2);
            }
        }*/

        var spanElement3 = document.getElementById('counter3');

        // Retrieve the current value and parse it as an integer (assuming it represents a percentage)
        var targetPercentage3 = parseInt(spanElement3.textContent.trim()); // Get the target percentage
        
        var speed3 = 10; // Adjust this value to control the speed (in milliseconds)
        
        var counts3 = setInterval(updated3, speed3); // Adjust the speed here
        
        var currentPercentage3 = 0;
        
        function updated3() {
            let count3 = document.getElementById("counter3");
            count3.innerHTML = currentPercentage3 + "%";
            currentPercentage3++;
        
            if (currentPercentage3 > targetPercentage3) {
                clearInterval(counts3);
            }
        }