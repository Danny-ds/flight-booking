package com.example.campaign.flight_service.fw.repo;

import com.example.campaign.flight_service.enums.JobStatus;
import com.example.campaign.flight_service.fw.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cb-dhana
 */
public interface IJobsRepo extends JpaRepository<JobEntity, Long> {

    List<JobEntity> findByScheduledAtLessThanAndStatusIn(long time, List<Integer> asList);
}
