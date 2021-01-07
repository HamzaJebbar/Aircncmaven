package com.example.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;



@RestController
@CrossOrigin
public class AppartementService {

	AppartementRepository aptRep;
	HoteRepository hoteRep;
	VoyageurRepository voyageurRep;

	public AppartementService(AppartementRepository aptRep, HoteRepository hoteRep,VoyageurRepository voyageurRep) {
		this.aptRep=aptRep;
		this.hoteRep = hoteRep;
		this.voyageurRep = voyageurRep;
	}
	
	//Afficher la liste des appartements
	
	@RequestMapping (value = "/getApts", method = RequestMethod.GET)
	@ResponseStatus (HttpStatus.OK)
	public ResponseEntity<List <Appartement>> getListeAppartement(){
		return new ResponseEntity<>(aptRep.findAll(), HttpStatus.OK);
	}
	
// Recuperer un Appartement dont l'id est connu
	
	@RequestMapping (value = "/getApt/{id_Appartement}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public ResponseEntity<Appartement> getApt(@PathVariable ("id_Appartement") int id_Appartement) {
		
        for (Appartement app : aptRep.findAll()) {
            if (app.getId_Appartement()==id_Appartement) {
                return new ResponseEntity<Appartement>(app, HttpStatus.OK);
            }
        }

		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
	
	
	//Ajouter un appartement dans la liste

		@RequestMapping (value = "/addApt/{id_Voyageur}", method = RequestMethod.POST)
		@ResponseStatus (HttpStatus.OK)
		public ResponseEntity<Appartement> addAppartement(@RequestBody Appartement appartement,
															@PathVariable ("id_Voyageur") int id_Voyageur) {
			Appartement apt;
			Hote h = null;
			for (Hote hote : hoteRep.findAll()) {
				if(hote.getId_voy() == id_Voyageur) {
					h = hote;
				}

			}
			if(h!=null){
				h.getAppartements().add(appartement);
				appartement.setHote(h);
				aptRep.save(appartement);
				return new ResponseEntity<>(appartement, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		//Supprimer un appartement de la liste

		@RequestMapping(value = "/delApt/{id_Appartement}", method = RequestMethod.DELETE)
		@ResponseStatus(HttpStatus.OK)
		public ResponseEntity<Appartement> supprimerAppartement(@PathVariable("id_Appartement") int id_Appartement) throws Exception{
			Appartement a=null;
			for (Appartement app :aptRep.findAll()) {
				if(app.getId_Appartement()==id_Appartement) {
					a=app;

				}
			}
			if(a!=null) {
				for(Voyageur v: a.getVoyageurs()){
					v.getAppartement_fav().remove(a);
					voyageurRep.save(v);
				}
				a.getVoyageurs().clear();
				aptRep.save(a);
				aptRep.deleteById(a.getId_Appartement());
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	
}

