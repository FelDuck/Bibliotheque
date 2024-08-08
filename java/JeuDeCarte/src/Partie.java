import java.util.List;

public class Partie {

    private List<Joueur> joueurs;
    private Paquet pioche;
    private Paquet depot;

    // Constructeur de la partie selon le nombre de joueurs
    public Partie(List<Joueur> joueurs, Paquet pioche, Paquet depot){
        this.joueurs = joueurs;
        this.pioche = pioche;
        this.depot = depot;
    }

    // Boucle de la partie jusqu'à l'obtention d'un vainqueur
    public void demarrerPartie() throws InterruptedException {
        distribuerCartes();
        int index = (int)(Math.random() * joueurs.size());
        while(true){
            tour(joueurs.get(index));
            index++;
            if(index == joueurs.size())
            {
                index = 0;
            }
        }
    }

    //Distribution aléatoire des cartes au joueurs.
    private void distribuerCartes(){
        for (int i = 0; i < 8; i++) {
            for (Joueur joueur : joueurs) {
                joueur.getMainJoueur().ajoutCarte(pioche.retirerCarte());
            }
        }
    }

    // Prendre les cartes du dépot pour recréer une nouvelle pioche.
    private void piocheVide(){
        int x = depot.getNbCarte();

        // reprend les cartes du dépôt sauf la dernière pour les mélanger à la pioche.
        for(int i = 0; i < x-1; i++){
            Cartes carte = depot.retirerCarte();
            pioche.ajoutCarte(carte);
        }
        pioche.melanger(); // Mélanger la pioche
        System.out.println("! --- La pioche a été remplie !");
    }

    // Tour d'un joueur
    private void tour(Joueur joueur) throws InterruptedException {
        if(depot.getNbCarte() == 0) // Début de jeu avec le premier joueur et un dépôt vide
        {
            depot.ajoutCarte(joueur.getMainJoueur().retirerCarte()); // Le premier joueur met une carte.
            outputInfo(); // Afficher le jeu
            return;
        }
        else
        {
            // Vérification si le joueur actif peut joueur
            int index = joueur.getMainJoueur().checkCarte(depot.getCartes().get(depot.getCartes().size() - 1));
            if(index != -1)
            {
                /* Si le joueur peut jouer */
                depot.ajoutCarte(joueur.getMainJoueur().retirerCarte(index));
                if(joueur.getMainJoueur().getNbCarte() == 0)
                {
                    System.out.println(joueur + " a gagné !");
                    System.exit(0);
                }
                System.out.println();
                System.out.println(joueur + " a joué la carte " + depot.getCartes().get(depot.getCartes().size() - 1));
            }
            else
            {
                /* Si le joueur ne peut pas jouer */
                joueur.getMainJoueur().ajoutCarte(pioche.retirerCarte());
                if(pioche.getNbCarte() == 0)
                {
                    piocheVide();
                }
                System.out.println();
                System.out.println(joueur + " a pioché une carte");
               // System.out.println("La carte sur le dépot: " + depot.getCartes().get(depot.getCartes().size() - 1));
            }
        }
        outputInfo();
        /* Wait for 1 second */
        Thread.sleep(1000);
    }

    //Affichage du résultat d'un tour.
    private void outputInfo()
    {
        String nbCarteAffiche;
        System.out.println();
        String output;
        int nbCartesTotal = 0; // Total de cartes en jeu pour vérification

        output = "";
        for (Joueur joueur : joueurs) {
            output += "-- " + joueur.getNumJoueur() + "--     ";
        }
        output += "- Dépot -     - Pioche -     - Total -";
        System.out.println(output);

        // Afficher le nombre de carte de chaque joueur
        output = "";
        for (Joueur joueur : joueurs) {
            nbCarteAffiche = "";
            if (joueur.getMainJoueur().getNbCarte() < 10) // Vérifier si 1 ou 2 nombres
                nbCarteAffiche = " ";
            nbCarteAffiche += joueur.getMainJoueur().getNbCarte(); // Additionne le total des cartes du joueur
            nbCartesTotal += joueur.getMainJoueur().getNbCarte(); // Additionne le grand total de cartes
            output += "| " + nbCarteAffiche + " |     ";
        }

        // Afficher le nombre carte du dépôt
        nbCarteAffiche = "";
        if (depot.getNbCarte() < 10 ) // Vérifier si 1 ou 2 nombres
                nbCarteAffiche = " ";
        nbCarteAffiche += depot.getNbCarte(); // Nombre de cartes du dépôt
        nbCartesTotal += depot.getNbCarte(); // Additionne le grand total de cartes
        output += "|  " + nbCarteAffiche;

        nbCarteAffiche = "";
        if (pioche.getNbCarte() < 10 ) // Vérifier si 1 ou 2 nombres
            nbCarteAffiche = " ";
        nbCarteAffiche += pioche.getNbCarte(); // Nombre de carte du dépôt
        nbCartesTotal += pioche.getNbCarte(); // Additionne le grand total de cartes
        output += "   |     |   " + nbCarteAffiche +"   |     |   "+nbCartesTotal+"  |";
        System.out.println(output); // Affiche le nombre de cartes

        output = "";
        for (Joueur joueur : joueurs) {
            output += "------     ";
        }
        output += "---------     ----------     ---------";
        System.out.println(output);

        // Affiche la carte sur le dépôt
        if(depot.getNbCarte() != 0)
        {
            System.out.println("La carte sur le dépot: " + depot.getCartes().get(depot.getCartes().size() - 1));
        }
        else
        {
            System.out.println("La carte sur le dépot: Aucune");
        }
        // Afficher la main des joueurs
        for (Joueur joueur : joueurs) {
            System.out.println(joueur+" a le jeu: "+joueur.getMainJoueur().getCartes());
        }

    }

}
