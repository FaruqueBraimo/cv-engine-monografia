package Engine.Application.Form.cv.engine.reporitory;

import Engine.Application.Form.cv.engine.model.Job;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface JobRepository extends ElasticsearchRepository<Job, String> {

}
