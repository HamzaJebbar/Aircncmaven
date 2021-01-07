package com.example.model;


import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
public class HoteService {
    HoteRepository hoteRep;
    AppartementRepository aptRep;

    public HoteService(HoteRepository hoteRep, AppartementRepository aptRep) {

        this.hoteRep = hoteRep;
        this.aptRep = aptRep;
    }
    //Afficher la liste des Hotes

    @RequestMapping (value = "/getHotes", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public ResponseEntity<List <Hote>> getListHotes(){

        return new ResponseEntity<>(hoteRep.findAll(), HttpStatus.OK);
    }


    // Recuperer un Hote dont l'id est connu

    @RequestMapping (value = "/getHote/{id_Hote}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public ResponseEntity<Hote> Hote(@PathVariable ("id_Hote") int id_Hote) {
        for (Hote hote : hoteRep.findAll()) {
            if (hote.getId_voy()==id_Hote ){

                return new ResponseEntity<>(hote, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);    }

    //Ajouter un Hote dans la liste

    @PostMapping("/addHote")
    public ResponseEntity<Hote> addHote(@RequestBody Hote hote) {

        hoteRep.save(hote);
        return new ResponseEntity<>(hote, HttpStatus.OK);
    }

    //Supprimer un Hote de la liste

    @RequestMapping(value = "/delHote/{id_Hote}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hote> supprimerHote(@PathVariable("id_Hote") int id_Hote) throws Exception{
        Hote h = null;
        for (Hote hote : hoteRep.findAll()) {
            if(hote.getId_voy() == id_Hote) {

                h = hote;
            }
        }
        if(h != null) {
            for(Appartement appartement: h.getAppartements()) {
                aptRep.delete(appartement);
            }
            hoteRep.delete(h);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
