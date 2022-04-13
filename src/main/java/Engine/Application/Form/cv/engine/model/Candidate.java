/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Application.Form.cv.engine.model;

import Engine.Application.Form.cv.engine.util.Indice;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author faruq
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(indexName = Indice.CANDIDATE_INDEX)
@Setting(settingPath = "static/es-settings.json")
@Builder
public class Candidate {

    @Id
    @Field(type = FieldType.Keyword)
    private String candidateId;
    @Field(type = FieldType.Text)
    private String nome;
    @Field(type = FieldType.Text)
    private String email;
    @Field(type = FieldType.Text)
    private String telefone;
    @Field(type = FieldType.Text)
    private String job;
    @Transient
    private String resumeId;



}
