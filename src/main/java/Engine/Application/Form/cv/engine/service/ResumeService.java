package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.resume.ResumeDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class ResumeService {

    private ResumeDatabase resumeDatabase = new ResumeDatabase();
    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    ;

    public void saveResumes(MultipartFile file, String job_folder) {
        try {
            Path root = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + job_folder + "/");
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar Cvs: " + e.getMessage());
        }
    }

    public List<Resume> getResumeByJob(String job) throws IOException {
        return resumeDatabase.loadResume(job);
    }

    public void deleteResume(Resume resume) throws IOException {
        Path root = Paths.get(resume.getPath());
        Files.delete(root);
    }

    public void deleteAllResume(String job_folder) {
        Path root = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + job_folder + "/");
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public void createDirectory(String name) throws IOException {
        resumeDatabase.createDirectory(name);
    }
}
