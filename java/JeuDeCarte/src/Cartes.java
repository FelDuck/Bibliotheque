public class Cartes {
    private Valeurs valeur; // représente le symbole de la carte de 1 à 13
    private Couleurs couleur; // représente la couleur Trèfle, coeur, carreau ou pique

    //Constructeur de la carte
    public Cartes(Valeurs valeur, Couleurs couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return valeur + " de " + couleur;
    }

    public Valeurs getValeur() {
        return valeur;
    }

    public void setValeur(Valeurs valeur) {
        this.valeur = valeur;
    }

    public Couleurs getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleurs couleur) {
        this.couleur = couleur;
    }
}
