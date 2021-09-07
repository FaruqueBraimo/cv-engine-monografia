package Engine.Application.Form.cv.engine.resource;


import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.model.UserEntity;
import Engine.Application.Form.cv.engine.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ResumeResource {

    private final ResumeService resumeService;
    @GetMapping( "/resumes/{job}")
    public ResponseEntity<List<Resume>> getUsers(@PathVariable String job) throws IOException {
        return  ResponseEntity.ok().body(resumeService.getResumeByJob(job));
    }
}
