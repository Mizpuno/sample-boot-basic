package th.mfu;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpLogging;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.config.web.server.ServerHttpSecurity.HttpsRedirectSpec;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    // // select all employees.
    // @GetMapping("/employees")
    // public Collection<Employee> getAllEmployees () {
    //     return employeesDB.values();
    // }

    // // select employee by id.
    // @GetMapping("/employees/{id}")
    // public ResponseEntity getIdEmployee(@PathVariable long id) {

    //     // check if id doesn't exsit.
    //     if (!employeesDB.containsKey(id)) {
    //         // return 404 not found.
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
    //     }
    //     return ResponseEntity.ok(employeesDB.get(id));
    // }

    //create new employee
    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee){

        // add employee to repository
        employeeRepository.save(employee);
       
        //return created success message
        return ResponseEntity.ok("Employee created");
    }

    @GetMapping("/employees")
    public Collection<Employee> selectAllEmployee() {
        return employeeRepository.findByOrderByFirstName();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity getEmployeeById(@PathVariable long id) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if (!optEmployee.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }

        Employee emp = optEmployee.get();
        return ResponseEntity.ok(emp);
    }

    @GetMapping("/employees/firstname/{firstname}")
    public ResponseEntity getEmployeeByFirstName(@PathVariable String firstname) {
        List<Employee> employees = employeeRepository.findByFirstName(firstname);
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }

        employeeRepository.save(employee);
        return ResponseEntity.ok("Employee updated");
    }

    @PatchMapping("/employees/{id}")
    public ResponseEntity patchEmployee(@PathVariable Long id, @RequestBody EmployeeDTO empDto) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);

        if (!optEmployee.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }

        Employee emp = optEmployee.get();
        employeeMapper.updateEmployeeFromDto(empDto, emp);

        employeeRepository.save(emp);
        return ResponseEntity.ok("Updated employee.");
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }

        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Emlpoyee deleted");
    }

}
