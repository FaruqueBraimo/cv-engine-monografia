package Engine.Application.Form.cv.engine.reporitory;

import Engine.Application.Form.cv.engine.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository  extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
