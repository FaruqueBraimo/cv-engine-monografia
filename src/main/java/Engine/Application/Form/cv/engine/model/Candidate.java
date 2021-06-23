/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Application.Form.cv.engine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author faruq
 */
@Entity
public class Candidate {

    @Id
    @GeneratedValue
    private UUID candidateId ;
    private String name;
    private String last_name;
    private LocalDate birth_date;
    private String nacionality;
    private String self_descrition;
    private String user_id;

    public Candidate(UUID candidateId, String name, String last_name, LocalDate birth_date, String nacionality, String self_descrition, String user_id) {
        this.candidateId = candidateId;
        this.name = name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.nacionality = nacionality;
        this.self_descrition = self_descrition;
        this.user_id = user_id;
    }

    public Candidate() {

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

   

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the birth_date
     */
    public LocalDate getBirth_date() {
        return birth_date;
    }

    /**
     * @param birth_date the birth_date to set
     */
    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    /**
     * @return the nacionality
     */
    public String getNacionality() {
        return nacionality;
    }

    /**
     * @param nacionality the nacionality to set
     */
    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    /**
     * @return the self_descrition
     */
    public String getSelf_descrition() {
        return self_descrition;
    }

    /**
     * @param self_descrition the self_descrition to set
     */
    public void setSelf_descrition(String self_descrition) {
        this.self_descrition = self_descrition;
    }

    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the id
     */
    public UUID getCandidateId() {
        return candidateId;
    }

    /**
     */
    public void setCandidateId(UUID candidateId) {
        this.candidateId = candidateId;
    }

    
}
