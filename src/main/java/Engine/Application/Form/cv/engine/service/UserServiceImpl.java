package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Role;
import Engine.Application.Form.cv.engine.model.UserEntity;
import Engine.Application.Form.cv.engine.reporitory.RoleRepository;
import Engine.Application.Form.cv.engine.reporitory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

//    private final  UserRepository userRepository;
//    private final RoleRepository roleRepository;
//
//
//
//
//    @Override
//    public UserEntity saveUser(UserEntity userEntity) {
//     log.info("saving user{} to the database", userEntity.getUsername());
//    return userRepository.save(userEntity);
//
//    }
//
//    @Override
//    public Role saveRole(Role role) {
//        log.info("saving role{} to the database", role.getName());
//        return roleRepository.save(role);
//    }
//
//    @Override
//    public void addRoleToUser(String username, String roleName) {
//
//        log.info("Adding role{} to user{} the database", roleName, username);
//        UserEntity user = userRepository.findByUsername(username);
//        Role role = roleRepository.findByName(roleName);
//        user.getRoles().add(role);
//
//    }
//
//    @Override
//    public UserEntity getUser(String username) {
//        log.info("fetching user{}", username);
//        return userRepository.findByUsername(username);
//
//    }
//
//    @Override
//    public List<UserEntity> getUsers() {
//        log.info("fetching users");
//        return userRepository.findAll();
//    }

}
