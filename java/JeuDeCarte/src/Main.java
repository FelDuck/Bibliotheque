// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        /* Création des joueurs */
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nombre de joueurs : ");
        int nbJoueurs = Integer.parseInt(scanner.next()); // Saisie le nombre de joueur et valide qu'il est numérique.
        List<Joueur> joueurs = new ArrayList<>();

        if(nbJoueurs < 2 || nbJoueurs > 4) // valide que le nombre joueur respecte les règles du nombre de joueurs
        {
            System.out.println("Le nombre de joueurs doit être compris entre 2 et 4");
            System.exit(0);
        }

        // Saisie des noms et prénoms des joueurs
        for(int i = 0; i < nbJoueurs; i++) {
            System.out.print("Entrez le nom du joueur " + (i + 1) + " : ");
            String nom = scanner.next();
            System.out.print("Entrez le prénom du joueur " + (i + 1) + " : ");
            String prenom = scanner.next();
            joueurs.add(new Joueur(nom, prenom));
        }

        /* Création des paquets */
        EnsembleDeCartes carteJeu = new EnsembleDeCartes();
        Paquet cartePioche = new Paquet();
        Paquet carteDepot = new Paquet();
        int nbCartes = 0;

        int size = carteJeu.getCartes().size();
        /* Remplissage de la pioche */
        for(int z = 0; z < size; z++)
        {
            nbCartes++;
            cartePioche.ajoutCarte(carteJeu.piocherCartes());
        }

        /* Début de la partie */
        Partie partie = new Partie(joueurs, cartePioche, carteDepot);
        partie.demarrerPartie();


    }

}

