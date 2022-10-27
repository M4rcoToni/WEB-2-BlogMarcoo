package com.example.web2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.web2.model.Postagens;

@Repository
public interface RepositoryPostagens extends JpaRepository<Postagens, Integer>{
    
}
