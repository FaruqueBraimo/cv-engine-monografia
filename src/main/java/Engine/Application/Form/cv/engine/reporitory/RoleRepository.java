package Engine.Application.Form.cv.engine.reporitory;

import Engine.Application.Form.cv.engine.model.Role;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface RoleRepository  extends ElasticsearchRepository<Role, UUID> {
}
