public class Joueur {
    private static int nbJoueur = 0; // Incrémenteur de numéro de joueur
    final private int numJoueur; // Numéro séquentiel et unique du joueur
    private String nom;
    private String prenom;
    private Paquet mainJoueur; // Ensemble de cartes du joueur

    public Joueur(String nom, String prenom) {
        nbJoueur++; // Attribut le numéro de joueur suivant
        this.numJoueur = nbJoueur;
        this.nom = nom;
        this.prenom = prenom;
        mainJoueur = new Paquet(); // Création du paquet vide du joueur
    }

    @Override
    public String toString() {
        return "Joueur #" + numJoueur + " : " + prenom + " " + nom ;
    }

    public int getNumJoueur(){
        return numJoueur;
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

    public Paquet getMainJoueur() {
        return mainJoueur;
    }

}
