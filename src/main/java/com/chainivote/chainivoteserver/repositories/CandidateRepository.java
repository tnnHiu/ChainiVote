package com.chainivote.chainivoteserver.repositories;


import com.chainivote.chainivoteserver.entities.CandidateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

//    Page<CandidateEntity> findAll( Pageable pageable);

    @Query("SELECT c FROM CandidateEntity c JOIN FETCH c.poll WHERE c.poll.id = :pollId")
    Page<CandidateEntity> findByPollId(@Param("pollId") long pollId, Pageable pageable);

}
