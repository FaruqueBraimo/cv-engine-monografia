package Engine.Application.Form.cv.engine.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue
    private UUID categoryId;
    private String categoryName;
    private String description;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Job> jobs;
}
