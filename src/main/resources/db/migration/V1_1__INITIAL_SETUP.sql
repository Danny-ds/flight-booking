create
database if not exists flight_service;

use
flight_service;
create table jobs
(
    id           bigint AUTO_INCREMENT primary key,
    status       int                default 0,
    context      text null,
    job_type     int       not null,
    scheduled_at timestamp not null,
    created_at   timestamp not null DEFAULT CURRENT_TIMESTAMP,
    updated_at   timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table batch_jobs
(
    id         bigint AUTO_INCREMENT primary key,
    job_id     bigint    not null,
    job_type   int       not null,
    status     int       not null,
    context    text null,
    created_at timestamp not null DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    constraint fk_batch_jobs_to_jobs foreign key (job_id) references jobs (id)
);