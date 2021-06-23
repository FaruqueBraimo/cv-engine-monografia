/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Application.Form.cv.engine.resource;

import Engine.Application.Form.cv.engine.model.Candidate;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import java.time.LocalDate;
import java.util.List;
import java.lang.IllegalStateException;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faruq
 */
@RestController
@RequestMapping("api/candidates")

public class CandidateResouce {
     private static final List<Candidate> CANDIDATES =  Arrays.asList(
     new Candidate( 1l ,"Faruque ", "Braimo", LocalDate.now(), "Moz", "Matreco", "55ss" ),
     new Candidate(2l, "Aida ", "Varice", LocalDate.now(), "AO", "Matreco", "55ss" ),
     new Candidate(3l,"Helio ", "Zandamela", LocalDate.now(), "Moz", "Matreco", "55ss" )        
    
    );
         
    @GetMapping(path = "{candidateId}")
    public Candidate getCandide(@PathVariable("candidateId") Long candidateId ){
        
        return CANDIDATES.stream()
                .filter(candidate -> candidateId.equals(candidate.getCandidateId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Candidate " + candidateId+ "Does not Exist"));
        
    }
    
    
}
