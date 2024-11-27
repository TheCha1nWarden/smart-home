package smart.home.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart.home.authservice.model.User;
import smart.home.authservice.repository.TestRepo;

import java.util.Random;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestRepo testRepo;

    @GetMapping("/add")
    public String add() {
        testRepo.save(new User("name" + new Random().nextDouble(), "pass", "role"));
        return "success";
    }

    @GetMapping("/get")
    public String get() {
        return testRepo.getReferenceById(1l).toString();
    }

}
