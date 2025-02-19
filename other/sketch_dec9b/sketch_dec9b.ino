#include <WiFi.h>
#include <HTTPClient.h>
#include <map>
#include <iostream>
#include <ArduinoJson.h>
#include <string>
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>

#define DHTPIN 21       // Пин, к которому подключен DHT11 (например, D4)
#define DHTTYPE DHT11  // Используемый датчик: DHT11

// Создаём объект DHT
DHT dht(DHTPIN, DHTTYPE);

// Wi-Fi параметры
const char* ssid = "iPhone (Артем)";
const char* password = "123456789";

// URL для взаимодействия с микросервисами
std::string deviceServiceUrl = "http://87.228.27.155:80/api/devices/getAllByUser/";
std::string sensorServiceUrl = "http://87.228.27.155:80/api/sensors/updateValue/";
std::string authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZTIiLCJyb2xlIjoicm9sZSIsImlhdCI6MTczMzcwNjk3NCwiZXhwIjoxNzM2Mjk4OTc0fQ.LZ62SZmLlxGUWlKXmM5OzkqSyV4jNdTQSUMkVVYrBWI"; // Токен авторизации

long userId = 1;
std::map<long, int> devicesMap;
int relayPin = 26;

int count = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);

  WiFi.begin(ssid, password);
    Serial.print("Connecting to Wi-Fi");
    while (WiFi.status() != WL_CONNECTED) {
        delay(1000);
        Serial.print(".");
    }
    Serial.println("\nConnected to Wi-Fi");

    dht.begin(); // Инициализация DHT11

    //init map
    DynamicJsonDocument doc(2048);
    JsonArray devices = getDevices(doc);
    for (JsonObject device : devices) {
      //todo
      long id = device["id"];
      std::string status = device["status"];
      Serial.println(id); //todo
      Serial.println(status.c_str()); //todo

      pinMode(relayPin, OUTPUT);
      digitalWrite(relayPin, HIGH);

      devicesMap[id] = relayPin++;
      Serial.println(relayPin); // todo
    }
}

void loop() {
  // put your main code here, to run repeatedly:
  // Проверяем подключение к Wi-Fi
    if (WiFi.status() == WL_CONNECTED) {
        // Получаем список устройств и обновляем состояния реле
        DynamicJsonDocument doc(2048);
        JsonArray devices = getDevices(doc);
        for (JsonObject device : devices) {
          long id = device["id"];
          std::string status = device["status"];

          int relayPin = devicesMap[id];
          if (relayPin != 0) {
            updateRelayStatus(relayPin, status.c_str());
          }        
        }

        //push sensor data
        float humidity = dht.readHumidity();      
        float temperature = dht.readTemperature();

        updateSensorValue(4, humidity);
        updateSensorValue(3, temperature);
        Serial.println(humidity);
        Serial.println(temperature);
        // Ожидание перед следующим запросом
        delay(3000); // 30 секунд
    } else {
        Serial.println("Wi-Fi not connected!");
    }
}

JsonArray getDevices(DynamicJsonDocument &doc) {
  HTTPClient http;
  http.begin((deviceServiceUrl + std::to_string(userId)).c_str());
  http.addHeader("Authorization", authToken.c_str());

  int httpCode = http.GET();
  if (httpCode > 0) {
        if (httpCode == HTTP_CODE_OK) {
            String payload = http.getString();
            Serial.println("Device List:");
            Serial.println(payload);

            // Парсим JSON-ответ
            // DynamicJsonDocument doc(2048); 
            DeserializationError error = deserializeJson(doc, payload);
            if (error) {
              Serial.print("JSON Parsing failed: ");
              Serial.println(error.c_str());
              StaticJsonDocument<200> docErr;
              return docErr.to<JsonArray>();
            }
            return doc.as<JsonArray>();
        } else {
          StaticJsonDocument<200> docErr;
          return docErr.to<JsonArray>();;
        }
  }
}

void updateSensorValue(long sensorId, float newValue) {
  HTTPClient http;
  http.begin((sensorServiceUrl + std::to_string(sensorId)).c_str());
  http.addHeader("Content-Type", "application/json");
  http.addHeader("Authorization", authToken.c_str());

  int httpCode = http.PUT(String(newValue));
  Serial.println(httpCode);
}

// Функция для обновления состояния реле
void updateRelayStatus(int relayPin, const char* status) {
    if (strcmp(status, "ON") == 0) {
        digitalWrite(relayPin, LOW);
        Serial.printf("Relay on pin %d turned ON\n", relayPin);
    } else if (strcmp(status, "OFF") == 0) {
        digitalWrite(relayPin, HIGH);
        Serial.printf("Relay on pin %d turned OFF\n", relayPin);
    } else {
        Serial.printf("Unknown status: %s\n", status);
    }
}
