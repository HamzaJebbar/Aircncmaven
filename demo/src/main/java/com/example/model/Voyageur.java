package com.example.model;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
//@Table(name="voyageur")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Voyageur {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_Voyageur;
	private String nom;
	private String prenom;
	private int age;
	private String sexe;

	@ManyToMany(targetEntity=Appartement.class)
	@JoinTable(
			name = "VoyAptFav",
			joinColumns = {@JoinColumn(name = "id_Voyaguer")},
			inverseJoinColumns = {@JoinColumn(name = "id_Appartement")}
	)
	private List<Appartement> appartement_fav= new ArrayList<Appartement>();

	@OneToMany(cascade= CascadeType.ALL, mappedBy="voyageur")
	private List<Appartement> appartement_loue= new ArrayList<Appartement>();

	//Constructeur par defaut
	public Voyageur() {
		this.nom="";
		this.prenom="";
		this.age=23;
		this.sexe="femme";
		
	}

	//Construction d'initialisation

	public Voyageur(String nom, String prenom, int age, String sexe, List<Appartement> appartement_fav, List<Appartement> appartement_loue) {
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.sexe = sexe;
		this.appartement_fav = appartement_fav;
		this.appartement_loue = appartement_loue;
	}


	
	//Les getters
	public int getId_voy() {
		return this.id_Voyageur;
	}

	public void setId_voy(int id) {
		this.id_Voyageur=id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public List<Appartement> getAppartement_fav(){
		return appartement_fav;
	}


	public void setAppartement_fav(List<Appartement> appartement_fav) {
		this.appartement_fav = appartement_fav;
	}

	public List<Appartement> getAppartement_loue() {
		return appartement_loue;
	}

	public void setAppartement_loue(List<Appartement> appartement_loue) {
		this.appartement_loue = appartement_loue;
	}

	//Affichage
	
	@Override
    public String toString (){
        return "Voyageur[id:" + getId_voy() + ",nom:" + nom+ ",prenom:"+ prenom + ",age:" + age + ",sexe:" + sexe +"] " ;
    }
	
	
	
	
}
