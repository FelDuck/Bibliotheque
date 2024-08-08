namespace CardGame;
// Paquet de cartes, joueur, pioche et dépot
public class Paquet
{
    private List<Cartes> cartes;
    private int nbCarte;

    public Paquet()
    {
        nbCarte = 0;
        cartes = new List<Cartes>();
    }

    //Mélanger un paquet.
    public void Melanger()
    {
        Random rng = new Random();
        int n = cartes.Count;
        while (n > 1)
        {
            n--;
            int k = rng.Next(n + 1);
            Cartes value = cartes[k];
            cartes[k] = cartes[n];
            cartes[n] = value;
        }
    }

    //Ajouter une carte au paquet.
    public void AjoutCarte(Cartes newCarte)
    {
        cartes.Add(newCarte);
        nbCarte++;
    }

    //Retirer la première carte du paquet.
    public Cartes RetirerCarte()
    {
        Cartes carte = cartes[0];
        cartes.RemoveAt(0);
        nbCarte--;
        return carte;
    }
    
    //Retirer une carte précise d'un paquet.
    public Cartes RetirerCarte(int index)
    {
        Cartes carte = cartes[index];
        cartes.RemoveAt(index);
        nbCarte--;
        return carte;
    }

    //Vérifier la valeur d'une carte si elle est jouable.
    public int CheckCarte(Cartes carte)
    {
        for (int i = 0; i < cartes.Count; i++)
        {
            if (cartes[i].GetValeur() == carte.GetValeur() || cartes[i].GetCouleur() == carte.GetCouleur())
            {
                return i;
            }
        }
        return -1;
    }

    //Retourner le nombre de carte d'un paquet.
    public int GetNbCarte()
    {
        return nbCarte;
    }

    //Retourner la liste de cartes.
    public List<Cartes> GetCartes()
    {
        return cartes;
    }
}