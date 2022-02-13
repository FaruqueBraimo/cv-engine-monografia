package Engine.Application.Form.cv.engine.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue
    private UUID companyId;
    private String companyName;
    private String description;
//    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Job> jobs;

}
