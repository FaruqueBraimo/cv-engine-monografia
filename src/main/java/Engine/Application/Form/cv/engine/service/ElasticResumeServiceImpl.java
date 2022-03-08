package Engine.Application.Form.cv.engine.service;


import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.reporitory.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ElasticResumeServiceImpl implements ElasticResumeService {

    private final ResumeRepository resumeRepository;

    public Resume saveResume(Resume resume) {
        return  resumeRepository.save(resume);
    }

    public Resume findById(String id) {
        return resumeRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(String id) {
        Optional<Resume> resume = resumeRepository.findById(id);
        if (resume.isPresent()) {
        resumeRepository.delete(resume.get());

        }
    }

    @Override
    public Iterable<Resume> findAll() {
        return resumeRepository.findAll();
    }

    @Override
    public Long countResumeByJob(String job) {
       return resumeRepository.countByJob(job);
    }

}
