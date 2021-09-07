package Engine.Application.Form.cv.engine.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Job {

    @Id
    @GeneratedValue
    private UUID jobId;
    private String title;
    private String JobType;
    private String salary;
    private String description;
    private String JobStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Company company;


}
