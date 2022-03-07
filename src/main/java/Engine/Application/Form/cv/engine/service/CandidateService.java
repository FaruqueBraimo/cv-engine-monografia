package Engine.Application.Form.cv.engine.service;


import Engine.Application.Form.cv.engine.model.Candidate;
import Engine.Application.Form.cv.engine.model.Job;
import Engine.Application.Form.cv.engine.reporitory.CandidateRepository;
import Engine.Application.Form.cv.engine.reporitory.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate saveJob(Candidate candidate) {
       return candidateRepository.save(candidate);
    }

    public void deleteCandidate(String id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isPresent()) {
            candidateRepository.delete(candidate.get());
        }
    }

    public Iterable<Candidate> getJobs() {
        return candidateRepository.findAll();

    }
}
