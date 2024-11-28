package smart.home.deviceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smart.home.deviceservice.model.Device;
import smart.home.deviceservice.model.Sensor;
import smart.home.deviceservice.model.Status;
import smart.home.deviceservice.service.DeviceService;
import smart.home.deviceservice.service.SensorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping("/add")
    public ResponseEntity<Sensor> addSensor(@RequestBody Sensor sensor) {
        Sensor savedDevice = sensorService.addSensor(sensor);
        return ResponseEntity.ok(savedDevice);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Long id) {
        Optional<Sensor> sensor = sensorService.getSensorById(id);
        return sensor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllByUser/{id}")
    public ResponseEntity<List<Sensor>> getAllUserSensors(@PathVariable Long id) {
        List<Sensor> sensors = sensorService.getAllByUserId(id);
        return ResponseEntity.ok(sensors);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @RequestBody Sensor updatedSensor) {
        Sensor sensor = sensorService.updateSensor(id, updatedSensor);
        if (sensor != null) {
            return ResponseEntity.ok(sensor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateValue/{id}")
    public ResponseEntity<Sensor> updateSensorValue(@PathVariable Long id, @RequestBody String updatedValue) {
        Sensor sensor = sensorService.updateSensorValue(id, updatedValue);
        if (sensor != null) {
            return ResponseEntity.ok(sensor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }

}
