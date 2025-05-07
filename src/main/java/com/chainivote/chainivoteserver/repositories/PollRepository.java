package com.chainivote.chainivoteserver.repositories;

import com.chainivote.chainivoteserver.entities.PollEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface PollRepository extends JpaRepository<PollEntity, Long> {

    Page<PollEntity> findAllPollsByCreator_Id(long uid, Pageable pageable);

    Page<PollEntity> findAllPollsByCategoryId(long categoryId, Pageable pageable);

}