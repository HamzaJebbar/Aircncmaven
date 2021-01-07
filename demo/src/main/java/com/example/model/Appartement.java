package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="appartement")
public class Appartement {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_Appartement;
	private String adresse;
	private int nbr_chambres;
	private int nbr_salle_bains;
	private double prix_nuit;
	private boolean reserve;
	private int nbrPersonne_max;

	@ManyToMany(targetEntity=Voyageur.class, mappedBy = "appartement_fav")
	@JsonIgnore
	private List<Voyageur> voyageurs= new ArrayList<Voyageur>();

	@ManyToOne
	@JsonIgnore
	private Hote hote;

	@ManyToOne
	@JsonIgnore
	private Voyageur voyageur;

	//Constructeur par defaut
	public Appartement() {
		this.adresse="";
		this.nbr_chambres=1;
		this.nbr_salle_bains=1;
		this.prix_nuit=40.00;
		this.nbrPersonne_max=1;
		this.reserve=false;
	}

	public Appartement(String adresse, int nbr_chambres, int nbr_salle_bains, double prix_nuit, int nbrPersonne_max,
					   List<Voyageur> voyageurs, Hote hote, Voyageur voyageur) {
		this.adresse = adresse;
		this.nbr_chambres = nbr_chambres;
		this.nbr_salle_bains = nbr_salle_bains;
		this.prix_nuit = prix_nuit;
		this.nbrPersonne_max = nbrPersonne_max;
		this.voyageurs = voyageurs;
		this.hote = hote;
		this.voyageur = voyageur;
		this.reserve = false;
	}

	public int getId_Appartement() {
		return id_Appartement;
	}

	public void setId_Appartement(int id_Appartement) {
		this.id_Appartement = id_Appartement;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getNbr_chambres() {
		return nbr_chambres;
	}

	public void setNbr_chambres(int nbr_chambres) {
		this.nbr_chambres = nbr_chambres;
	}

	public int getNbr_salle_bains() {
		return nbr_salle_bains;
	}

	public void setNbr_salle_bains(int nbr_salle_bains) {
		this.nbr_salle_bains = nbr_salle_bains;
	}

	public double getPrix_nuit() {
		return prix_nuit;
	}

	public void setPrix_nuit(double prix_nuit) {
		this.prix_nuit = prix_nuit;
	}

	public boolean isReserve() {
		return reserve;
	}

	public void setReserve(boolean reserve) {
		this.reserve = reserve;
	}

	public int getNbrPersonne_max() {
		return nbrPersonne_max;
	}

	public void setNbrPersonne_max(int nbrPersonne_max) {
		this.nbrPersonne_max = nbrPersonne_max;
	}

	public List<Voyageur> getVoyageurs() {
		return voyageurs;
	}

	public void setVoyageurs(List<Voyageur> voyageurs) {
		this.voyageurs = voyageurs;
	}

	public Hote getHote() {
		return hote;
	}

	public void setHote(Hote hote) {
		this.hote = hote;
	}

	public Voyageur getVoyageur() {
		return voyageur;
	}

	public void setVoyageur(Voyageur voyageur) {
		this.voyageur = voyageur;
	}
}
