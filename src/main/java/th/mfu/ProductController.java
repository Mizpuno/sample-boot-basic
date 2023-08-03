package th.mfu;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    HashMap<String, Product> products = new HashMap<String, Product>();

    // create a new product.
    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        if (products.containsKey(product.getName())) { 
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exsit.");
        }
        products.put(product.getName(), product);
        return ResponseEntity.ok("Created Product.");
    }

    // select all product.
    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return products.values();
    }
}
