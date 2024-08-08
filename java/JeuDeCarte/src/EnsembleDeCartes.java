import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnsembleDeCartes {
    private List<Cartes> cartes; // Contient les cartes du paquet

    public EnsembleDeCartes() {

        cartes = new ArrayList<>();
        // Construction du paquet de 52 cartes (4 couleurs x 13 cartes)
        for (Couleurs couleur : Couleurs.values()) {
            for (Valeurs valeur : Valeurs.values()) {
                cartes.add(new Cartes(valeur, couleur));
            }
        }
        melanger();
    }

    public List<Cartes> getCartes() {
        return cartes;
    }

    //Mélanger le paquet carte
    public void melanger() {
        Collections.shuffle(cartes);
    }

    // enlève une carte du paquet pour la retourner
    public Cartes piocherCartes(){
        Cartes carte = cartes.get(0);
        cartes.remove(0);
        return carte;
    }


}
