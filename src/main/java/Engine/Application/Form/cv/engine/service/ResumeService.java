package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.resume.ResumeDatabase;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.StandardCopyOption;
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


    public File getResumeFile(String resumeName, String job_folder) {

        File result = null;
        try {

            Path root = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + job_folder);
            Path file = root.resolve(resumeName);
            Resource resource = new UrlResource(file.toUri());
            result = resource.getFile();

            Files.copy(resource.getInputStream(), root, StandardCopyOption.REPLACE_EXISTING);


            if (resource.exists() || resource.isReadable()) {
            } else {
                throw new RuntimeException("Falha ao ler o Cv!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void deleteResume(Resume resume) throws IOException {
        Path root = Paths.get(resume.getPath());
        Files.delete(root);
    }


    public  void locateFile(String job_folder, String  resumeName) throws URISyntaxException, IOException {
        Path root = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + job_folder + "/" + resumeName);
        File file = new File(root.toAbsolutePath().toString());

        try {
            Runtime.getRuntime().exec("explorer.exe  /select," + file.getAbsolutePath());
        } catch (Exception ex) {
            System.out.println("Error - " + ex);
        }
    }


    public void deleteAllResume(String job_folder) {
        Path root = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + job_folder + "/");
        FileSystemUtils.deleteRecursively(root.toFile());

    }

    public void createDirectory(String name) throws IOException {
        resumeDatabase.createDirectory(name);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
    }
}
