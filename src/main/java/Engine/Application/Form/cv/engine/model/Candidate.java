/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Application.Form.cv.engine.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author faruq
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue
    @Column(name = "candidateId")
    private UUID objectID ;
    private String name;
    private String last_name;
    private LocalDate birth_date;
    private String nacionality;
    private String self_descrition;
    private String user_id;
    private String province;
    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Experience> experience;
    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Skills> skills;



    
}
