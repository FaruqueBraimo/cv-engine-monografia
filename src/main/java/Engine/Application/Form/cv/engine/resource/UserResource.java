package Engine.Application.Form.cv.engine.resource;


import Engine.Application.Form.cv.engine.model.Role;
import Engine.Application.Form.cv.engine.model.RoleToUser;
import Engine.Application.Form.cv.engine.model.UserEntity;
import Engine.Application.Form.cv.engine.reporitory.RoleRepository;
import Engine.Application.Form.cv.engine.reporitory.UserRepository;
import Engine.Application.Form.cv.engine.service.ElasticResumeService;
import Engine.Application.Form.cv.engine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserResource {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return  ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public  ResponseEntity<UserEntity> saveUser( @RequestBody UserEntity userEntity ){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return  ResponseEntity.created(uri).body(userService.saveUser(userEntity));
    }

    @PostMapping("/role/save")
    public  ResponseEntity<Role> saveRole( @RequestBody Role role ){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return  ResponseEntity.created(uri).body(userService.saveRole(role));
    }


    @PostMapping("/role/adduser")
    public  ResponseEntity<?> addRoleToUser( @RequestBody RoleToUser roleToUser ){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        userService.addRoleToUser(roleToUser.getRole(), roleToUser.getUsername());
        return  ResponseEntity.ok().build();
    }


    
}
