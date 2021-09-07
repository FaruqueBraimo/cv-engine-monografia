package Engine.Application.Form.cv.engine.reporitory;

import Engine.Application.Form.cv.engine.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
   Job findByTitle(String title);
}
