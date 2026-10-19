package itu.webdynamique.app.model;

import itu.webdynamique.framework.MapFile;

public class Employe {
    private int id;
    private String nom;
    private String prenom;
    private double salaire;
    private MapFile rectoCin;
    private MapFile versoCin;

    public Employe() {
    }

    public Employe(int id, String nom, String prenom, double salaire) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public MapFile getRectoCin() {
        return rectoCin;
    }

    public void setRectoCin(MapFile rectoCin) {
        this.rectoCin = rectoCin;
    }

    public MapFile getVersoCin() {
        return versoCin;
    }

    public void setVersoCin(MapFile versoCin) {
        this.versoCin = versoCin;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", salaire=" + salaire +
                ", rectoCin=" + rectoCin +
                ", versoCin=" + versoCin +
                '}';
    }
}
