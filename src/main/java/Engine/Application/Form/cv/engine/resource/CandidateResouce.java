/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Application.Form.cv.engine.resource;


import Engine.Application.Form.cv.engine.message.ResponseMessage;
import Engine.Application.Form.cv.engine.model.Candidate;
import Engine.Application.Form.cv.engine.model.Job;
import Engine.Application.Form.cv.engine.service.CandidateService;
import Engine.Application.Form.cv.engine.service.ElasticResumeService;
import Engine.Application.Form.cv.engine.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author faruq
 */
@RestController
@RequestMapping(value = "api")
@CrossOrigin("*")
@RequiredArgsConstructor

public class CandidateResouce {

    private final CandidateService candidateService;
    private final ElasticResumeService elasticResumeService;


    @GetMapping("/candidate")
    public ResponseEntity<Iterable<Candidate>> getJobs() {
        return  ResponseEntity.ok().body(candidateService.getJobs());
    }

    @PostMapping("/candidate/create")
    private ResponseEntity<ResponseMessage> saveJob(@RequestParam String candidate1, @RequestBody Candidate candidate) {

        System.out.println(candidate1);
        String message = "";
        String jobId = UUID.randomUUID().toString();
        try {
            candidate.setCandidateId(jobId);
            candidateService.saveJob(candidate);
            elasticResumeService.delete(candidate.getResumeId());
            message = "O candidato " + candidate.getNome()+ " foi adiciomnado" ;

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }


    }

    
}
