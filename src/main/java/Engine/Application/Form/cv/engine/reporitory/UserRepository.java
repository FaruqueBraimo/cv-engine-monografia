package Engine.Application.Form.cv.engine.reporitory;

import Engine.Application.Form.cv.engine.model.Role;
import Engine.Application.Form.cv.engine.model.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends ElasticsearchRepository<UserEntity, UUID> {

    UserEntity findByUsername(String username);
}
