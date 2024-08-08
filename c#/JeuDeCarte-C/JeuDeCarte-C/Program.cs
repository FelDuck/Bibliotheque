using System;
using System.Collections.Generic;

namespace CardGame
{
    class Program
    {
        public static void Main(string[] args)
         
        {
            Console.Write("Entrez le nombre de joueurs : ");
            int nbJoueurs = int.Parse(Console.ReadLine());
            List<Joueur> joueurs = new List<Joueur>();

            if (nbJoueurs < 2 || nbJoueurs > 4) //Validation du nombre de joueurs
            {
                Console.WriteLine("Le nombre de joueurs doit être compris entre 2 et 4");
                Environment.Exit(0);
            }
            
            //Saisie du nom et prénom des joueurs
            for (int i = 0; i < nbJoueurs; i++)
            {
                Console.Write($"Entrez le nom du joueur {i + 1} : ");
                string nom = Console.ReadLine();
                Console.Write($"Entrez le prénom du joueur {i + 1} : ");
                string prenom = Console.ReadLine();
                joueurs.Add(new Joueur(nom, prenom));
            }
            
            EnsembleDeCartes carteJeu = new EnsembleDeCartes(); // Création des 52 cartes
            Paquet cartePioche = new Paquet(); //Création de la pioche
            Paquet carteDepot = new Paquet(); //Création du dépot
            int nbCartes = 0;

            int size = carteJeu.GetCartes().Count;
            for (int z = 0; z < size; z++)
            {
                nbCartes++;
                cartePioche.AjoutCarte(carteJeu.PiocherCartes());
            }

            Partie partie = new Partie(joueurs, cartePioche, carteDepot); //Création de la partie
            partie.DemarrerPartie(); // Début de la partie
        }
    }
}