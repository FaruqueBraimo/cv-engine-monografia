package Engine.Application.Form.cv.engine.reporitory;


import Engine.Application.Form.cv.engine.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByCategoryName(String categoryName);
}
