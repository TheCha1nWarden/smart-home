package smart.home.deviceservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "devices")
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // Название устройства

    @Column(nullable = false)
    private String type;  // Тип устройства (например, "light", "thermostat")

    @Column(nullable = false)
    private Status status;  // Текущее состояние устройства (например, "ON", "OFF")

    @Column(nullable = false)
    private Long userId;    // Идентификатор пользователя устройства

    public Device(String name, String type, Status status, Long userId) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.userId = userId;
    }
}
