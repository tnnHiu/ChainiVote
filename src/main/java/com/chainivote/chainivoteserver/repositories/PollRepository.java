package com.chainivote.chainivoteserver.repositories;

import com.chainivote.chainivoteserver.entities.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<PollEntity, Long> {

}
