package com.example.web2.services;

import java.util.List;

import com.example.web2.model.Postagens;

public interface BlogmarcooServices {
	List<Postagens> findAll();

	Postagens findById(int id);

	Postagens save(Postagens postagem);

	Postagens deleteById(int id);

	List<Postagens> findFirstByOrderByIdDesc();

	List<Postagens> findPostagensByTituloLike(String titulo);

	List<Postagens> findPostagensByTipo(int tipo);
}
