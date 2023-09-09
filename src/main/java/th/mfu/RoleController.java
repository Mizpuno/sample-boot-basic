package th.mfu;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/roles")
    public ResponseEntity createRole(@RequestBody Role role) {
        roleRepository.save(role);
        return ResponseEntity.ok(role);
    }

    @PutMapping("/accounts/{account_id}/roles/{role_id}")
    public ResponseEntity setRoleToAccount (@PathVariable Long account_id, @PathVariable Long role_id) {
        // search account by id.
        Optional<Account> accountopt = accountRepository.findById(account_id);

        // set/add roles to account.
        if (!accountopt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Account account = accountopt.get();

        Optional<Role> roleopt = roleRepository.findById(role_id);
        if (!roleopt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // save account.
        Role role = roleopt.get();
        account.getRoles().add(role);
        accountRepository.save(account);

        return ResponseEntity.ok("Success update.");
    }
}
