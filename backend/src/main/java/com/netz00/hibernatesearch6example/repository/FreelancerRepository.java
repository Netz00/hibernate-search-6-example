package com.netz00.hibernatesearch6example.repository;

import com.netz00.hibernatesearch6example.model.Comment;
import com.netz00.hibernatesearch6example.model.Freelancer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {


    /**
     * Paged fetching child entities (comments) over parent (freelancer)
     *
     * @param freelancer of freelancer
     * @param pageable   comments page
     * @return
     */
    @Query("SELECT comment FROM Freelancer f INNER JOIN f.comments comment WHERE f = :freelancer")
    Page<Comment> findBy(@Param("freelancer") Freelancer freelancer, Pageable pageable);

}


