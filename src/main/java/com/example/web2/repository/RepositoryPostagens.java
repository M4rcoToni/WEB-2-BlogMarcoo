package com.example.web2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.web2.model.Postagens;

import java.util.List;

@Repository
public interface RepositoryPostagens extends JpaRepository<Postagens, Integer> {
    List<Postagens> findPostagensByTituloLike(String titulo);

    List<Postagens> findFirstByOrderByIdDesc();

    List<Postagens> findPostagensByTipo(int tipo);
}
