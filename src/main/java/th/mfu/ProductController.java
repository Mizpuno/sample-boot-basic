package th.mfu;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    // create a new product.
    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.ok("Created Product.");
    }

    // select all product.
    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // select one product.
    @GetMapping("/products/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (!optProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        Product product = optProduct.get();
        return ResponseEntity.ok(product);
    }

    // select one product by name.
    @GetMapping("/products/productName/{name}")
    public ResponseEntity getProductByName(@PathVariable String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        return ResponseEntity.ok(products);
    }

    // update the product.
    @PutMapping("/products/{id}")
    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        productRepository.save(product);
        return ResponseEntity.ok("Product updated.");
    }

    // delete the product.
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted.");
    } 
}
