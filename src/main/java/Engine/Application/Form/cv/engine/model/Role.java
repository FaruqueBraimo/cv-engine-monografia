package Engine.Application.Form.cv.engine.model;


import Engine.Application.Form.cv.engine.util.Indice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.util.UUID;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(indexName = Indice.ROLE_INDEX)
@Setting(settingPath = "static/es-settings.json")
public class Role {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text)
    private String name;

}
