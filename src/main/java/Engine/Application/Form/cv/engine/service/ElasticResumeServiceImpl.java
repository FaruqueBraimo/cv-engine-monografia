package Engine.Application.Form.cv.engine.service;


import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.reporitory.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
