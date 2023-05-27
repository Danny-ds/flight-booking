package com.example.campaign.flight_service.fw.service;

import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.entity.JobEntity;
import com.example.campaign.flight_service.fw.jobs.Job;
import com.example.campaign.flight_service.fw.repo.IJobsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author cb-dhana
 */

@Service
public class JobService {
    private IJobsRepo jobsRepo;

    public JobService(@Autowired IJobsRepo jobsRepo) {
        this.jobsRepo = jobsRepo;
    }

    public Optional<JobEntity> getJob(Long jobId) throws Exception {
        return jobsRepo.findById(jobId);
    }

    public JobEntity createOrUpdateJob(JobEntity jobEntity) {
        return jobsRepo.save(jobEntity);
    }

    public List<JobEntity> fetchJobsToExecute() {
        return jobsRepo.findByScheduledAtLessThanAndStatusIn(DateUtils.createNow().getTime().getTime(), Arrays.asList(JobStatus.SCHEDULED.getCode(), JobStatus.RESCHEDULED.getCode()));
    }

    public JobEntity updateJobStatus(JobEntity job, JobStatus jobStatus) {
        job.setStatus(jobStatus.getCode());
        jobsRepo.save(job);
        return job;
    }
}
