package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Job;

public interface JobService {

    public Job saveJob(Job job);
    public void deleteJob(String id);
    public Job findById(String id) ;
    Iterable<Job> getJobs();


}
