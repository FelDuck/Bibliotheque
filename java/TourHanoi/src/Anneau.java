public class Anneau {
    /* La classe Anneau cree un object qui sera definie par une grandeur qui sera inserer dans la tour.*/
    int size;
    Anneau(int size){
        this.size=size;
    }
    public String toString(){
        return Integer.toString(size);
    }
}
