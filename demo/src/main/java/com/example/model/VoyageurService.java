package com.example.model;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin
public class VoyageurService {

	VoyageurRepository voyageurRep;
	HoteRepository hoteRep;
	AppartementRepository aptRep;

	public VoyageurService(VoyageurRepository voyageurRep, AppartementRepository aptRep,HoteRepository hoteRep) {
				this.voyageurRep = voyageurRep;
				this.aptRep = aptRep;
				this.hoteRep = hoteRep;
	}
	
    //Afficher la liste des voyageurs
	
	@RequestMapping (value = "/getVoys", method = RequestMethod.GET)
	@ResponseStatus (HttpStatus.OK)
	public ResponseEntity<List<Voyageur>> getListVoyageurs(){
		return new ResponseEntity<>(voyageurRep.findAll(),HttpStatus.OK);
	}
	
	
	// Recuperer un Voyageur dont l'id est connu
	
	@RequestMapping (value = "/getVoy/{id_Voyageur}", method = RequestMethod.GET)
	@ResponseStatus (HttpStatus.OK)
	public ResponseEntity<Voyageur> getVoy(@PathVariable ("id_Voyageur") int id_Voyageur) {
		Voyageur v = null;
		for (Voyageur voy : voyageurRep.findAll()) {
			if (voy.getId_voy()==id_Voyageur ){
				v = voy;
			}
		}
		if(v!=null){
			return new ResponseEntity<>(v, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

	}
		
	//Ajouter un voyageur dans la liste
		
	@PostMapping("/addVoy")
			
	public ResponseEntity<Voyageur> addVoyageur(@RequestBody Voyageur voyageur) {

		voyageurRep.save(voyageur);
		return new ResponseEntity<>(voyageur, HttpStatus.CREATED);

		}
			
	//Supprimer un voyageur de la liste
			
	@RequestMapping(value = "/delVoy/{id_Voyageur}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Voyageur> supprimerVoyageur(@PathVariable("id_Voyageur") int id_Voyageur) throws Exception{
		int id=-1;
		for (Voyageur voy : voyageurRep.findAll()) {

			if(voy.getId_voy() == id_Voyageur) {
				id = id_Voyageur;
			}

		}
		if(id==-1) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else{
			voyageurRep.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	@PutMapping(path = "updateVoy")
	public ResponseEntity<Voyageur> updateVoy(@RequestBody Voyageur voyageur){
		for(Hote hote: hoteRep.findAll()){
			if(hote.getId_voy()==voyageur.getId_voy()){
				hote.copy(voyageur);
				hoteRep.save(hote);
				return new ResponseEntity<>(hote, HttpStatus.OK);
			}
		}
		voyageurRep.save(voyageur);
		return new ResponseEntity<>(voyageur, HttpStatus.OK);
	}
	@RequestMapping(value = "/rentApt/{id_Voyageur}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Voyageur> aptLoue(@PathVariable("id_Voyageur") int id_Voyageur,@RequestBody Appartement appartement) {
		Voyageur voyageur = voyageurRep.findById(id_Voyageur).get();
		Appartement apt = aptRep.findById(appartement.getId_Appartement()).get();
		if(voyageur.getAppartement_loue().contains(apt)){
			voyageur.getAppartement_loue().remove(apt);
			apt.setVoyageur(null);
			apt.setReserve(false);
			aptRep.save(apt);
			voyageurRep.save(voyageur);
		} else {
			voyageur.getAppartement_loue().add(apt);
			apt.setVoyageur(voyageur);
			apt.setReserve(true);
			voyageurRep.save(voyageur);
			aptRep.save(apt);

		}
		return new ResponseEntity<>(voyageur, HttpStatus.OK);
	}
	@RequestMapping(value = "/aptFav/{id_Voyageur}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Voyageur> aptFav(@PathVariable("id_Voyageur") int id_Voyageur,@RequestBody Appartement appartement) {
		Voyageur voyageur = voyageurRep.findById(id_Voyageur).get();
		Appartement apt = aptRep.findById(appartement.getId_Appartement()).get();
		if(voyageur.getAppartement_fav().contains(apt)){
			voyageur.getAppartement_fav().remove(apt);
			apt.getVoyageurs().remove(voyageur);
			aptRep.save(apt);
			voyageurRep.save(voyageur);
		} else {
			voyageur.getAppartement_fav().add(apt);
			apt.getVoyageurs().add(voyageur);
			voyageurRep.save(voyageur);
			aptRep.save(apt);

		}
		return new ResponseEntity<>(voyageur, HttpStatus.OK);
	}

}
