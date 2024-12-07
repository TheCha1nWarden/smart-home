package smart.home.simpleservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
public class OkController {

    @GetMapping("/ok")
    public ResponseEntity<Test> ok() {
        return ResponseEntity.ok(new Test("ok"));
    }

}
