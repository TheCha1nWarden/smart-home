package smart.home.simpleservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
public class OkController {

    @GetMapping("/ok")
    public String ok() {
        return "OK";
    }

}
