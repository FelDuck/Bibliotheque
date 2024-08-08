namespace CardGame;
// Classe pourl'objet carte avec sa couleur et sa valeur.
public class Cartes
{
    private Valeurs valeur;
    private Couleurs couleur;

    //Constructeur de cartes
    public Cartes(Valeurs valeur, Couleurs couleur)
    {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    public override string ToString()
    {
        return valeur + " de " + couleur;
    }

    public Valeurs GetValeur()
    {
        return valeur;
    }

    public void SetValeur(Valeurs valeur)
    {
        this.valeur = valeur;
    }

    public Couleurs GetCouleur()
    {
        return couleur;
    }

    public void SetCouleur(Couleurs couleur)
    {
        this.couleur = couleur;
    }
}