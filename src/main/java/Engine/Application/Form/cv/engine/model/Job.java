package Engine.Application.Form.cv.engine.model;

import Engine.Application.Form.cv.engine.util.Indice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.annotation.Id;

import java.util.Date;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(indexName = Indice.JOB_INDEX)
@Setting(settingPath = "static/es-settings.json")
public class Job {

    @Id
    @Field(type = FieldType.Keyword)
    private String jobId;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Text)
    private String jobStatus;
    @Field(type = FieldType.Date)
    private Date createdAt;


}
