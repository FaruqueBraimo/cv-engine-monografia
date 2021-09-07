package Engine.Application.Form.cv.engine.resource;


import Engine.Application.Form.cv.engine.message.ResponseMessage;
import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.model.UserEntity;
import Engine.Application.Form.cv.engine.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ResumeResource {

    private final ResumeService resumeService;

    @GetMapping("/resumes/{job}")
    public ResponseEntity<List<Resume>> getUsers(@PathVariable String job) throws IOException {
        return ResponseEntity.ok().body(resumeService.getResumeByJob(job));
    }

    @PostMapping("/resumes/upload")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files ) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).stream().forEach(file -> {
                resumeService.saveResumes(file, "desenvolvedor-java-junio");
                fileNames.add(file.getOriginalFilename());
            });

            message = "Houve sucesso no carregado de" + fileNames ;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Falha ao carregar!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
