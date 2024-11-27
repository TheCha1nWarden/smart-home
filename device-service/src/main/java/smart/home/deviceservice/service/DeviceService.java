package smart.home.deviceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.home.deviceservice.model.Device;
import smart.home.deviceservice.model.Status;
import smart.home.deviceservice.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    // Добавить новое устройство
    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    // Получить устройство по ID
    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    // Получить все устройства пользователя
    public List<Device> getAllUserDevices(Long userId) {
        return deviceRepository.getAllByUserId(userId);
    }

    // Обновить устройство
    public Device updateDevice(Long id, Device updatedDevice) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            device.setName(updatedDevice.getName());
            device.setType(updatedDevice.getType());
            device.setStatus(updatedDevice.getStatus());
            return deviceRepository.save(device);
        }
        return null;
    }

    // Обновить статус устройства
    public Device updateDeviceStatus(Long id, Status status) {
        Optional<Device> optionalDevice = deviceRepository.findById(id);
        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            device.setStatus(status);
            return deviceRepository.save(device);
        }
        return null;
    }

    // Удалить устройство
    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}
