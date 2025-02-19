package smart.home.deviceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.home.deviceservice.model.Device;
import smart.home.deviceservice.model.Sensor;
import smart.home.deviceservice.model.Status;
import smart.home.deviceservice.repository.SensorRepository;

import java.util.List;
import java.util.Optional;

    @Service
    public class SensorService {

        @Autowired
        private SensorRepository sensorRepository;

        public Sensor addSensor(Sensor sensor) {
            return sensorRepository.save(sensor);
        }

        public Optional<Sensor> getSensorById(Long id) {
            return sensorRepository.findById(id);
        }

        public List<Sensor> getAllByUserId(Long userId) {
            return sensorRepository.getAllByUserId(userId);
        }

        public Sensor updateSensor(Long id, Sensor updatedSensor) {
            Optional<Sensor> optionalSensor = sensorRepository.findById(id);
            if (optionalSensor.isPresent()) {
                Sensor sensor = optionalSensor.get();
                sensor.setName(updatedSensor.getName());
                sensor.setType(updatedSensor.getType());
                sensor.setValue(updatedSensor.getValue());
                return sensorRepository.save(sensor);
            }
            return null;
        }

        public Sensor updateSensorValue(Long id, String value) {
            Optional<Sensor> optionalSensor = sensorRepository.findById(id);
            if (optionalSensor.isPresent()) {
                Sensor sensor = optionalSensor.get();
                sensor.setValue(value);
                return sensorRepository.save(sensor);
            }
            return null;
        }

        public void deleteSensor(Long id) {
            sensorRepository.deleteById(id);
        }

    }
