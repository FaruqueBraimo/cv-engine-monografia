package Engine.Application.Form.cv.engine.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Skills {

    @Id
    @GeneratedValue
    @Column(name = "skillId")
    private UUID objectID;
    private String name;

    private String level;


}
