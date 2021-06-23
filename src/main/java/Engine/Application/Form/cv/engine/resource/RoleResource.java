package Engine.Application.Form.cv.engine.resource;

import Engine.Application.Form.cv.engine.model.Role;
import Engine.Application.Form.cv.engine.reporitory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "api")
@CrossOrigin("*")
public class RoleResource {

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/saveRole")
    public Role saveRole(@RequestBody Role role){
        return roleRepository.save(role);
    }

    @GetMapping("/roles")
    public List<Role> listCategories(){
        return roleRepository.findAll();
    }
}
