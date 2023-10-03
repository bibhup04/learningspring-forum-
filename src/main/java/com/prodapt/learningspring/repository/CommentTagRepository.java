package com.prodapt.learningspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.learningspring.entity.CommentTag;

@Repository
public interface CommentTagRepository extends JpaRepository<CommentTag, Integer> {

}