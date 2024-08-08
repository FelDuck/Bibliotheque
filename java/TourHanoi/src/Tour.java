public class Tour {
    /* La classe Tour gere une pile d'anneau */
    char id;
    int top = -1;
    Anneau[] stack;
    Tour(int size,char id){
        this.id=id;
        stack = new Anneau[size];

    }
    public boolean isFull(){
        /* Verifie si la pile est pleine */
       return (top == stack.length-1);
    }

    public boolean isEmpty(){
        /* Verifie si la pile est vide */
       return (top == -1);
    }

    public void push(Anneau A){
        /* Met un anneau dans la pile si la pile n'est pas pleine */
        if (!this.isFull())stack[++top]=A;
    }

    public Anneau pop(){
        /* Retourne et enleve l'anneau du dessus de la pile si elle n'est pas vide */
        if (!this.isEmpty()) return stack[top--];
        else return null;
    }

    public Anneau peek(){
        /* Retourne l'anneau du dessus de la pile si elle n'est pas vide */
        if (!this.isEmpty()) return stack[top];
        else return null;
    }

    public  void videTour(){
        /* Vide la tour */
        stack = new Anneau[stack.length];

    }

    public String toString(){
        /* Cree un string qui permet de visualiser les anneaux de la tour.*/
        String bigString = id+": ";
        for(int i=0;i < stack.length;i++){
            if (top>=i) bigString+=stack[i];
            else bigString += "-";
        }
        return bigString;
    }
}
