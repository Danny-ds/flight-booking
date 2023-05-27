package com.example.campaign.flight_service.fw.entity;

import com.example.campaign.flight_service.fw.constants.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * @author cb-dhana
 */
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = Constants.Table.BATCH_JOBS)
public class BatchJobEntity {
    @Column(name = Constants.Table.BatchJobs.ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = Constants.Table.BatchJobs.JOB_ID)
    private JobEntity jobEntity;

    @Column(name = Constants.Table.BatchJobs.CONTEXT)
    private String context;

    @Column(name = Constants.Table.BatchJobs.JOB_TYPE)
    private int jobType;

    @Column(name = Constants.Table.BatchJobs.STATUS)
    private int status;

    @Column(name = Constants.Table.Jobs.CREATED_AT)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = Constants.Table.Jobs.UPDATED_AT)
    @UpdateTimestamp
    private Timestamp updatedAt;
}

