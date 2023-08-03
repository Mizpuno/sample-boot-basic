package th.mfu;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloController {

    @GetMapping("/hello")
    String hello() {
        return "Hello World!";
    }

    @GetMapping("/hi/{name}")
    String hi(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("/sum/{n1}/{n2}")
    int sum(@PathVariable int n1, @PathVariable int n2) {
        return n1 + n2;
    }


}
