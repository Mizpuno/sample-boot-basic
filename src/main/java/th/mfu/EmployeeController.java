package th.mfu;

import java.util.HashMap;
import java.util.Collection;

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
public class EmployeeController {

    private HashMap<Long, Employee> employeesDB = new HashMap<Long, Employee>();

    // select all employees.
    @GetMapping("/employees")
    public Collection<Employee> getAllEmployees () {
        return employeesDB.values();
    }

    // select employee by id.
    @GetMapping("/employees/{id}")
    public ResponseEntity getIdEmployee(@PathVariable long id) {

        // check if id doesn't exsit.
        if (!employeesDB.containsKey(id)) {
            // return 404 not found.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        return ResponseEntity.ok(employeesDB.get(id));
    }

    // post the employees.
    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        // check if id exsit.
        if (employeesDB.containsKey(employee.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee id already exsits.");    
        }

        employeesDB.put(employee.getId(), employee);
        return ResponseEntity.ok("Employee Created");
    }

    // update the employees.
    @PutMapping("employees/{id}")
    public ResponseEntity updateEmployee(@PathVariable long id, @RequestBody Employee employee) {

        if (!employeesDB.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }
        employeesDB.put(id, employee);
        return ResponseEntity.ok(employeesDB.get(id));
    }

    // delete the employee.
    @DeleteMapping("employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        
        if (!employeesDB.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }
        employeesDB.remove(id);
        return ResponseEntity.ok("Employee deleted");
    }
}
