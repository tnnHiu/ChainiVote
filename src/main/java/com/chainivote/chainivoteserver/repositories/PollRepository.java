package com.chainivote.chainivoteserver.repositories;

import com.chainivote.chainivoteserver.entities.PollEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<PollEntity, Long> {

//    @Query("SELECT (p.title, p.id, p.chainId, p.creator, p.description, p.endTime, p.startTime, p.status, p.urlImage) " +
//            "FROM PollEntity p")
//    Page<PollEntity> findAllWithoutCandidate(Pageable pageable);

}
