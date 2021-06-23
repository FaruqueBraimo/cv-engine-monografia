package Engine.Application.Form.cv.engine.resource;


import Engine.Application.Form.cv.engine.model.Role;
import Engine.Application.Form.cv.engine.model.UserEntity;
import Engine.Application.Form.cv.engine.reporitory.RoleRepository;
import Engine.Application.Form.cv.engine.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api")
@CrossOrigin("*")
public class UserResource {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/saveUser")
    public UserEntity saveRole(@RequestBody UserEntity user){
        Role role = roleRepository.findByName("ADMIN");
        user.setRole(role);
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
       public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public UserEntity findByUserNameAndPassword(String username, String password) {
        UserEntity userEntity = findByUsername(username);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
    
    
}
