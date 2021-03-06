package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Resume;

public interface ElasticResumeService {

    public Resume saveResume(Resume resume) ;
    public Resume findById(String id) ;
    public void delete(String id) ;

    Iterable<Resume> findAll();
    public  Long countResumeByJob(String job);
}
