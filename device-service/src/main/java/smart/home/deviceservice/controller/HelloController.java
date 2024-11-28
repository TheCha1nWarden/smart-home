package smart.home.deviceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart.home.deviceservice.kafka.DeviceKafkaProducer;
import smart.home.deviceservice.model.Device;
import smart.home.deviceservice.model.Status;

@RestController
@RequestMapping("/test")
public class HelloController {

    @Autowired
    private DeviceKafkaProducer deviceKafkaProducer;

    @GetMapping("/ok")
    public String ok() {
        return "OK";
    }

    @GetMapping("/test-kafka")
    public String testKafka() {
        deviceKafkaProducer.sendDeviceStatus(new Device("name", "type", Status.ON, 123l));
        return "SUCCESS";
    }

}
