package Engine.Application.Form.cv.engine.resource;


import Engine.Application.Form.cv.engine.message.ResponseMessage;
import Engine.Application.Form.cv.engine.model.Job;
import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.model.UserEntity;
import Engine.Application.Form.cv.engine.service.JobService;
import Engine.Application.Form.cv.engine.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class JobResource {

    private final JobService jobService;
    private final ResumeService resumeService;


    @GetMapping("/jobs")
    public ResponseEntity<Iterable<Job>> getJobs() {
        return  ResponseEntity.ok().body(jobService.getJobs());
    }



    @PostMapping("/job/create")
    private ResponseEntity<ResponseMessage> saveJob(@RequestBody Job job) {
        String message = "";
        String jobId = UUID.randomUUID().toString();


        try {
            job.setJobId(jobId);
            job.setCreatedAt(new Date());
            job.setJobStatus("opened");
            jobService.saveJob(job);

            message = "A Vaga " + job.getTitle()+ " foi criada" ;

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }

}
