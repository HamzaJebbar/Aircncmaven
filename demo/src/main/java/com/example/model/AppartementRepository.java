package com.example.model;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface AppartementRepository extends JpaRepository<Appartement,Integer> {
	
	List<Appartement> findAll();
}
