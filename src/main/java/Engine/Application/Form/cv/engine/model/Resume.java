package Engine.Application.Form.cv.engine.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Resume {

    private String id;
    private String name;
    private String path;
    private String content;
    private String language;
}
