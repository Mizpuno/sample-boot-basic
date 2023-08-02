package th.mfu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloControllerTest {

    @Autowired
    private HelloController controller;

    @Test
    public void testHello() {

        // Act
        String response = controller.hello();
        // Assert
        assertEquals("Hello World!", response);
    }

    @Test
    public void testHi() {
        String response = controller.hi("CMU");
        assertEquals("Hello CMU", response);
    }

    @Test
    public void testSum() {
        int response1 = controller.sum(5, 5);
        assertEquals(10, response1);

        int response2 = controller.sum(1, 3);
        assertEquals(4, response2);
    }
}
