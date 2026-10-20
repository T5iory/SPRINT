package itu.webdynamique.framework;

/**
 * Représente un fichier reçu depuis un formulaire HTML multipart.
 * Classe réutilisable du framework, utilisable par toutes les applications.
 *
 * Les attributs de type MapFile dans les objets métier seront automatiquement
 * remplis par le DataBinder à partir des parts de la requête multipart.
 */
public class MapFile {

    /** Nom du fichier original (ex: "photo.jpg") */
    private String nom;

    /** Extension du fichier (ex: "jpg") */
    private String extension;

    /** Contenu du fichier sous forme de tableau d'octets */
    private byte[] binaire;

    public MapFile() {
    }

    public MapFile(String nom, String extension, byte[] binaire) {
        this.nom = nom;
        this.extension = extension;
        this.binaire = binaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getBinaire() {
        return binaire;
    }

    public void setBinaire(byte[] binaire) {
        this.binaire = binaire;
    }

    @Override
    public String toString() {
        return "MapFile{" +
                "nom='" + nom + '\'' +
                ", extension='" + extension + '\'' +
                ", taille=" + (binaire != null ? binaire.length : 0) + " octets" +
                '}';
    }
}
