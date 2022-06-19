package Engine.Application.Form.cv.engine.reporitory;

import Engine.Application.Form.cv.engine.model.Company;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository  extends ElasticsearchRepository<Company, UUID> {

  Company findByCompanyName(String company);
}
