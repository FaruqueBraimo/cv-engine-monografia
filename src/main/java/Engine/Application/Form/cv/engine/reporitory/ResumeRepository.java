package Engine.Application.Form.cv.engine.reporitory;

import Engine.Application.Form.cv.engine.model.Resume;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ResumeRepository  extends ElasticsearchRepository<Resume, String> {
      Long countByJob(String job)  ;

}
