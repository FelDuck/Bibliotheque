namespace CardGame;

public class Partie
{
    private List<Joueur> joueurs;
    private Paquet pioche;
    private Paquet depot;

    //Constructeur d'une partie.
    public Partie(List<Joueur> joueurs, Paquet pioche, Paquet depot)
    {
        this.joueurs = joueurs;
        this.pioche = pioche;
        this.depot = depot;
    }

    //Débuter la partie.
    public void DemarrerPartie()
    {
        DistribuerCartes(); // Distribuer les cartes
        Random random = new Random();
        int index = random.Next(joueurs.Count); // Choisir un joueur au hasard pour commencer

        //Débuter la partie jusqu'à un gagnant
        while (true)
        {
            Tour(joueurs[index]); // Tour d'un joueur
            index++; //Changer au joueur suivant
            if (index == joueurs.Count) //Si dernier joueur va au premier joueur.
            {
                index = 0;
            }
        }
    }

    //Distribuer les cartes aux joueurs
    private void DistribuerCartes()
    {
        for (int i = 0; i < 8; i++)
        {
            foreach (Joueur joueur in joueurs)
            {
                joueur.GetMainJoueur().AjoutCarte(pioche.RetirerCarte());
            }
        }
    }

    //Si la piche est vide il faut la remplacer par le dépôt mélangé.
    private void PiocheVide()
    {
        int x = depot.GetNbCarte();
        for (int i = 0; i < x - 1; i++) //transférer les carte du bépôt vers la pioche sauf la dernière.
        {
            Cartes carte = depot.RetirerCarte();
            pioche.AjoutCarte(carte);
        }

        pioche.Melanger(); //Mélanger la pioche.
        Console.WriteLine("! --- La pioche a été remplie !");
    }

    //Tour d'un joueur
    private void Tour(Joueur joueur)
    {
        if (depot.GetNbCarte() == 0) // En début de partie le dépôt est vide, on ajoute une carte de départ.
        {
            depot.AjoutCarte(joueur.GetMainJoueur().RetirerCarte());
            OutputInfo();
            return;
        }
        else
        {
            int index = joueur.GetMainJoueur().CheckCarte(depot.GetCartes()[depot.GetCartes().Count - 1]);

            if (index != -1) //Valide si le joueur peut jouer
            {
                depot.AjoutCarte(joueur.GetMainJoueur().RetirerCarte(index));

                if (joueur.GetMainJoueur().GetNbCarte() == 0) //Valide si le joueur a gagné
                {
                    Console.WriteLine($"{joueur} a gagné!");
                    Environment.Exit(0);
                }

                Console.WriteLine();
                Console.WriteLine($"{joueur} a joué la carte {depot.GetCartes()[depot.GetCartes().Count - 1]}");
            }
            else // Sinon, il pioche une carte
            {
                joueur.GetMainJoueur().AjoutCarte(pioche.RetirerCarte());

                if (pioche.GetNbCarte() == 0)
                {
                    PiocheVide();
                }

                Console.WriteLine();
                Console.WriteLine($"{joueur} a pioché une carte");
            }
        }

        OutputInfo(); // Affichage du tour.
        System.Threading.Thread.Sleep(1000);
    }

    //Affichage du tour de jeu
    private void OutputInfo()
    {
        string nbCarteAffiche;
        Console.WriteLine();
        string output = "";
        int nbCartesTotal = 0;
        output = "";

        foreach (Joueur joueur in joueurs) // Affichage de l'entête
        {
            output += $"-- {joueur.GetNumJoueur()}--     ";
        }

        output += "- Dépôt -     - Pioche -     - Total -";
        Console.WriteLine(output);

        output = "";

        foreach (Joueur joueur in joueurs) // Affichage du nombre de carte des joueurs
        {
            nbCarteAffiche = "";
            if (joueur.GetMainJoueur().GetNbCarte() < 10) //Valide s'il faut ajouter un espace pour les nombres en bas de 10
                nbCarteAffiche = " ";

            nbCarteAffiche += joueur.GetMainJoueur().GetNbCarte();
            nbCartesTotal += joueur.GetMainJoueur().GetNbCarte();
            output += $"| {nbCarteAffiche} |     ";
        }

        nbCarteAffiche = "";

        if (depot.GetNbCarte() < 10) //Valide s'il faut ajouter un espace pour les nombres en bas de 10
            nbCarteAffiche = " ";

        nbCarteAffiche += depot.GetNbCarte();
        nbCartesTotal += depot.GetNbCarte();
        output += $"|  {nbCarteAffiche}";
        nbCarteAffiche = "";

        if (pioche.GetNbCarte() < 10) //Valide s'il faut ajouter un espace pour les nombres en bas de 10
            nbCarteAffiche = " ";

        nbCarteAffiche += pioche.GetNbCarte();
        nbCartesTotal += pioche.GetNbCarte();
        output += $"   |     |   {nbCarteAffiche}   |     |   {nbCartesTotal}  |";
        Console.WriteLine(output);

        output = "";

        foreach (Joueur joueur in joueurs)
        {
            output += "------     ";
        }

        output += "---------     ----------     ---------";
        Console.WriteLine(output);

        if (depot.GetNbCarte() != 0)
        {
            Console.WriteLine($"La carte sur le dépôt: {depot.GetCartes()[depot.GetCartes().Count - 1]}");
        }
        else
        {
            Console.WriteLine("La carte sur le dépôt: Aucune");
        }

        foreach (Joueur joueur in joueurs)
        {
            Console.WriteLine($"{joueur} a le jeu: {string.Join(", ", joueur.GetMainJoueur().GetCartes())}");
        }
    }
}