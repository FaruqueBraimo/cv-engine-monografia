package Engine.Application.Form.cv.engine.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Experience {

    @Id
    @GeneratedValue
    @Column(name = "experienceId")
    private UUID objectID ;
    private String companyName;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private String description;
    private boolean correntWork;

}
