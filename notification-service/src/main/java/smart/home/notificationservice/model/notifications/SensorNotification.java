package smart.home.notificationservice.model.notifications;

import lombok.Data;

@Data
public class SensorNotification {
    private String sensorId;
    private String sensorName;
    private String sensorType;
    private String sensorValue;
    private String location;
    private String description;
    private UserInfo userInfo;
}
