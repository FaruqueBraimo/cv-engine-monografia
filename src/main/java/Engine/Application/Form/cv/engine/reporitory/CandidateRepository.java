package Engine.Application.Form.cv.engine.reporitory;

import Engine.Application.Form.cv.engine.model.Candidate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CandidateRepository extends ElasticsearchRepository<Candidate, String> {
}
