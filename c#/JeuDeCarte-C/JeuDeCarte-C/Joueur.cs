namespace CardGame;
//Classe qui fabrique un ou des joueurs
public class Joueur
{
    private static int nbJoueur = 0; // variable pour suivre le prochain numéro unique à un joueur
    private readonly int numJoueur; // numéro unique et fixe du joueur.
    private string nom;
    private string prenom;
    private Paquet mainJoueur; // Main du joueur.

    //Constructeur du joueur
    public Joueur(string nom, string prenom)
    {
        nbJoueur++;
        numJoueur = nbJoueur;
        this.nom = nom;
        this.prenom = prenom;
        mainJoueur = new Paquet();
    }

    public override string ToString()
    {
        return $"Joueur #{numJoueur} : {prenom} {nom}";
    }

    public int GetNumJoueur()
    {
        return numJoueur;
    }

    public string GetNom()
    {
        return nom;
    }

    public void SetNom(string nom)
    {
        this.nom = nom;
    }

    public string GetPrenom()
    {
        return prenom;
    }

    public void SetPrenom(string prenom)
    {
        this.prenom = prenom;
    }

    public Paquet GetMainJoueur()
    {
        return mainJoueur;
    }
}