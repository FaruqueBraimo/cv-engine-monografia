package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.resume.ResumeDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ResumeService {

    private  ResumeDatabase resumeDatabase  = new ResumeDatabase();

    public List<Resume> getResumeByJob(String job) throws IOException {
        return resumeDatabase.loadResume(job);
    }

    public void createDirectory(String name) throws IOException {
        resumeDatabase.createDirectory(name);
    }
}
