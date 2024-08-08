namespace CardGame;
// Classe qui fabrique un paquet de cartes (couleur x valeur) dans le cas présent 52 cartes
public class EnsembleDeCartes
{
    private List<Cartes> cartes;

    //Constructeur du paquet de cartes
    public EnsembleDeCartes()
    {
        cartes = new List<Cartes>();

        foreach (Couleurs couleur in Enum.GetValues(typeof(Couleurs)))
        {
            foreach (Valeurs valeur in Enum.GetValues(typeof(Valeurs)))
            {
                cartes.Add(new Cartes(valeur, couleur));
            }
        }

        Melanger();
    }

    public List<Cartes> GetCartes()
    {
        return cartes;
    }

    //Mélanger les cartes
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

    //Piocher une carte en retournant la valeur de la première carte et en l'enlevant du paquet.
    public Cartes PiocherCartes()
    {
        Cartes carte = cartes[0];
        cartes.RemoveAt(0);
        return carte;
    }
}