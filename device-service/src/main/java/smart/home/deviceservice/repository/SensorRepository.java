package smart.home.deviceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.home.deviceservice.model.Sensor;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> getAllByUserId(Long userId);

}
