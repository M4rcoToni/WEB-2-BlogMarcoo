package com.example.web2.services.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.web2.model.Postagens;
import com.example.web2.repository.RepositoryPostagens;
import com.example.web2.services.BlogmarcooServices;

@Service
public class BlogmarcooServicesImpl implements BlogmarcooServices{
	@Autowired
	RepositoryPostagens repository;
	
	@Override
	public List<Postagens> findAll() {
		return repository.findAll();
	}

	@Override
	public Postagens findById(int id) {
		return repository.findById(id).get();
	}

	@Override
	public Postagens save(Postagens postagem) {
		return repository.save(postagem);
	}
	@Override
	public Postagens deleteById(int id) {
		return deleteById(id);
	}


}
