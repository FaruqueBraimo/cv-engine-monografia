package Engine.Application.Form.cv.engine.resource;


import Engine.Application.Form.cv.engine.message.ResponseMessage;
import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.service.ElasticResumeService;
import Engine.Application.Form.cv.engine.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "api")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class ResumeResource {

    private final ResumeService resumeService;
    private final ElasticResumeService elasticResumeService;

    @GetMapping("/resumes/{job}")
    public ResponseEntity<List<Resume>> getUsers(@PathVariable String job) throws IOException {
       // elasticResumeService.saveResume(resumeService.getResumeByJob(job).get(2));
        List<Resume> resumesList = resumeService.getResumeByJob(job);
//        resumesList.stream().forEach(e ->
//                elasticResumeService.saveResume(e)
//        );

        return ResponseEntity.ok().body(resumeService.getResumeByJob(job));
    }

    @PostMapping("/resumes/upload")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).stream().forEach(file -> {
                resumeService.saveResumes(file, "desenvolvedor-java-junior");
                fileNames.add(file.getOriginalFilename());
            });

            message = "Houve sucesso no carregado de" + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Falha ao carregar!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    @GetMapping("/openResume/{jobFolder}/{resumeName}")
    public ResponseEntity<InputStreamResource> getTermsConditions(@PathVariable String jobFolder, @PathVariable String resumeName) throws FileNotFoundException {
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        Path root = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + jobFolder + "/" + resumeName);
        File file = new File(root.toAbsolutePath().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + file.getName());

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    @GetMapping("/locateResume/{jobFolder}/{resumeName}")
    public ResponseEntity<String> locateFile(@PathVariable String jobFolder, @PathVariable String resumeName) throws URISyntaxException, IOException {
        resumeService.locateFile(jobFolder, resumeName);
        return new ResponseEntity<>("Localizado!", HttpStatus.OK);

    }
}