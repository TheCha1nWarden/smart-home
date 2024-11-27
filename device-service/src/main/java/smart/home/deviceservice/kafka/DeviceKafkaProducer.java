package smart.home.deviceservice.kafka;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import smart.home.deviceservice.model.Device;

@Service
public class DeviceKafkaProducer {

    @Autowired
    private KafkaTemplate<String, Device> kafkaTemplate;

    @Value("${topic.notification}")
    private String TOPIC;

    public void sendDeviceStatus(Device device) {
        kafkaTemplate.send(new ProducerRecord<>(TOPIC, device));
    }
}
