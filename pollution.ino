#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <DHT.h>

const char* ssid = "kusom";
const char* password = "Kusom@#123";

const char* host = "172.16.16.158";  // IP address of your Java Spring Boot server
const int port = 8080;               // Port number of your Java Spring Boot server

// Pin definitions
const int muxControlPin0 = 12;    // Control pin for multiplexer
const int muxControlPin1 = 13;    // Control pin for multiplexer
const int muxControlPin2 = 14;    // Control pin for multiplexer
const int muxOutputPin = A0;      // Analog pin for multiplexer
const int DHTPIN = 2;             // Digital pin for DHT sensor
const int DHTTYPE = DHT11;        // DHT sensor type

// Static location coordinates
const String STATIC_LATITUDE ="27.669809859230572";   // Example latitude
const String STATIC_LONGITUDE ="85.33413018272607"; 
DHT dht(DHTPIN, DHTTYPE);

ESP8266WebServer server(80);

void setup() {
  Serial.begin(115200);
  delay(10);

  // Connect to WiFi
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  // Initialize DHT sensor
  dht.begin();

  // Initialize multiplexer control pins as outputs
  pinMode(muxControlPin0, OUTPUT);
  pinMode(muxControlPin1, OUTPUT);
  pinMode(muxControlPin2, OUTPUT);

  // Route for sensor data
  server.on("/sendData", HTTP_POST, []() {
    if (server.hasArg("plain")) {
      String jsonPayload = server.arg("plain");

      // Send data to Java Spring Boot server
      WiFiClient client;
      if (!client.connect(host, port)) {
        Serial.println("Connection to server failed");
        return;
      }

      // Prepare HTTP POST request
      client.print("POST /sendData HTTP/1.1\r\n");
      client.print("Host: ");
      client.print(host);
      client.print(":");
      client.print(port);
      client.print("\r\n");
      client.print("Content-Type: application/json\r\n");
      client.print("Content-Length: ");
      client.print(jsonPayload.length());
      client.print("\r\n\r\n");
      client.print(jsonPayload);

      Serial.println("Request sent to server");

      delay(1000);  // Small delay to ensure data is sent

      // Check for response from the server
      while (client.available()) {
        String line = client.readStringUntil('\r');
        Serial.print(line);
      }

      // Close the connection
      client.stop();

      server.send(200); // Respond to the Arduino client
    } else {
      server.send(400, "text/plain", "Bad Request");
    }
  });

  server.begin();
}

void loop() {
  server.handleClient();

  // Read sensor data
  float temperature = dht.readTemperature();    // Read temperature in Celsius
  float humidity = dht.readHumidity();          // Read humidity

  // Check for NaN values
  if (isnan(temperature) || isnan(humidity)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  
  // Read gas sensor data
  int gasValue = readFromMultiplexer(0);        // Read from channel 0
  // Convert gas value to ppm
  float gasPPM = convertToPPM(gasValue);
  // Convert gasPPM to AQI
  int aqi = convertCO2ToAQI(gasPPM);

  // Read sound sensor data
  int soundValue = readFromMultiplexer(1);      // Read from channel 1

  // Prepare JSON payload
  String jsonPayload = "{\"latitude\": " + String(STATIC_LATITUDE) + ", ";
  jsonPayload += "\"longitude\": " + String(STATIC_LONGITUDE) + ", ";
  jsonPayload += "\"gas\":\"" + String(gasValue) + "\",";
  jsonPayload += "\"sound\":\"" + String(soundValue) + "\",";
  jsonPayload += "\"gasPPM\":\"" + String(gasPPM) + "\",";
  jsonPayload += "\"aqi\":\"" + String(aqi) + "\",";
  jsonPayload += "\"temperature\":\"" + String(temperature) + "\",";
  jsonPayload += "\"humidity\":\"" + String(humidity) + "\"}";

  // Send sensor data to Java Spring Boot server
  sendSensorData(jsonPayload);

  delay(5000);  // Delay for 5 seconds
}

int readFromMultiplexer(int channel) {
  // Set control pins to select the desired input channel
  digitalWrite(muxControlPin0, channel & 0x01);
  digitalWrite(muxControlPin1, (channel >> 1) & 0x01);
  digitalWrite(muxControlPin2, (channel >> 2) & 0x01);

  // Read data from the selected channel
  return analogRead(muxOutputPin);
}

float convertToPPM(int sensorValue) {
  // Conversion logic for the sensor value to ppm
  // This is a placeholder and should be replaced with actual conversion formula
  float voltage = sensorValue * (3.3 / 1024.0); // Example conversion from sensor value to voltage
  float ppm = (voltage - 0.1) * 2000; // Example conversion from voltage to ppm
  return ppm;
}

int convertCO2ToAQI(float gasPPM) {
  int aqi = 0;
  if (gasPPM <= 600) {
    aqi = map(gasPPM, 0, 600, 0, 50);
  } else if (gasPPM <= 1000) {
    aqi = map(gasPPM, 601, 1000, 51, 100);
  } else if (gasPPM <= 1500) {
    aqi = map(gasPPM, 1001, 1500, 101, 150);
  } else if (gasPPM <= 2000) {
    aqi = map(gasPPM, 1501, 2000, 151, 200);
  } else if (gasPPM <= 5000) {
    aqi = map(gasPPM, 2001, 5000, 201, 300);
  } else {
    aqi = map(gasPPM, 5001, 10000, 301, 500); // Custom handling for very high levels
  }
  return aqi;
}

void sendSensorData(String jsonPayload) {
  // Establish connection to the Java Spring Boot server
  WiFiClient client;
  if (!client.connect(host, port)) {
    Serial.println("Connection to server failed");
    return;
  }

  // Prepare HTTP POST request
  client.print("POST /sendData HTTP/1.1\r\n");
  client.print("Host: ");
  client.print(host);
  client.print(":");
  client.print(port);
  client.print("\r\n");
  client.print("Content-Type: application/json\r\n");
  client.print("Content-Length: ");
  client.print(jsonPayload.length());
  client.print("\r\n\r\n");
  client.print(jsonPayload);

  Serial.println("Request sent to server");

  delay(30000);  // Small delay to ensure data is sent

  // Check for response from the server
  while (client.available()) {
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }

  // Close the connection
  client.stop();
}
