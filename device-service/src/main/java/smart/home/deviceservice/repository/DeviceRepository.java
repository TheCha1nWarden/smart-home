package smart.home.deviceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.home.deviceservice.model.Device;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> getAllByUserId(Long userId);

}
