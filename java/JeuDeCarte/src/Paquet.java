import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Paquet {
    final private List<Cartes> cartes;
    private int nbCarte;

    //Constructeur du paquet
    public Paquet() {
        nbCarte = 0;
        cartes = new ArrayList<>();
    }

    //Mélanger les cartes
    public void melanger() {
        Collections.shuffle(this.cartes);
    }

    // Ajouter une carte au paquet
    public void ajoutCarte(Cartes newCarte) {
        cartes.add(newCarte);
        nbCarte++;
    }

    //retirer la première carte
    public Cartes retirerCarte() {
        Cartes carte = cartes.get(0);
        cartes.remove(0);
        nbCarte--;
        return carte;
    }

    //Retirer une carte particulière.
    public Cartes retirerCarte(int index) {
        Cartes carte = cartes.get(index);
        cartes.remove(index);
        nbCarte--;
        return carte;
    }

    //fonctions retournant l'index d'une carte ayant soit la meme couleur ou la meme valeurs que la carte donner
    public int checkCarte(Cartes carte){
        for(int i = 0; i < cartes.size(); i++){
            if (cartes.get(i).getValeur() == carte.getValeur() || cartes.get(i).getCouleur() == carte.getCouleur())
                return i;
        }
        return -1;
    }

    //Retourner le nombre de cartes
    public int getNbCarte() {
        return nbCarte;
    }

    //retourner la main du joueur
    public List<Cartes> getCartes() {
        return cartes;
    }
}
