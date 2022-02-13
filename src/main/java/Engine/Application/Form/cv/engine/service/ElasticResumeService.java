package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Resume;

public interface ElasticResumeService {

    public Resume saveResume(Resume resume) ;
    public Resume findById(String id) ;
    Iterable<Resume> findAll();
}
