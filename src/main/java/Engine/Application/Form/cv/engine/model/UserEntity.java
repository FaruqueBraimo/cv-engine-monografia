package Engine.Application.Form.cv.engine.model;


import Engine.Application.Form.cv.engine.util.Indice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(indexName = Indice.user_INDEX)
@Setting(settingPath = "static/es-settings.json")
@Builder
public class UserEntity {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text)
    private String username;
    @Field(type = FieldType.Text)
    private String password;

}
