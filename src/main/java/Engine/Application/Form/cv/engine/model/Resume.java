package Engine.Application.Form.cv.engine.model;


import Engine.Application.Form.cv.engine.search.helper.Indice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import javax.persistence.Id;
import java.util.Date;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(indexName = Indice.RESUME_INDEX)
@Setting(settingPath = "static/es-settings.json")
public class Resume {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String path;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Text)
    private String language;
    @Field(type = FieldType.Integer)
    private int pages;
    @Field(type = FieldType.Date)
    private Date createdAt;
    @Field(type = FieldType.Text)
    private String job;
}
