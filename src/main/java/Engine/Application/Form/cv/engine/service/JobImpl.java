package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Job;
import Engine.Application.Form.cv.engine.reporitory.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobImpl implements  JobService{

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(String id) {

    }

    @Override
    public Job findById(String id) {
        return null;
    }

    @Override
    public Iterable<Job> getJobs() {
        return jobRepository.findAll();
    }

}
