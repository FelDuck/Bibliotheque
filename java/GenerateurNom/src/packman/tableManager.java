package packman;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class tableManager {
	
	final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	int[][] freqTable = new int[alphabet.length()+2][alphabet.length()+2];
	double[][] oddsTable = new double[alphabet.length()+2][alphabet.length()+2];
	String textName;
	int y,x;
	
	tableManager(String textName) {
		/* Les collone [x][0] et [0][y] contiennent les valeurs des char sous la forme d'integer*/
		for(int i=1;i<alphabet.length()+1;i++) {
			freqTable[i][0] = alphabet.charAt(i-1);
			freqTable[0][i] = alphabet.charAt(i-1);
		}
		addFromText(textName);	
	}
	/*Methode creant la table de probabilite*/
	public void createOdds(int[][] freqTable){
		
		int total;
		double odds = 0;
		for(int k=1;k<alphabet.length()+2;k++) {
			total=0;
			 for(int l=1;l<alphabet.length()+2;l++) {
				 total += freqTable[k][l];
			 }
			 /* A se stade les valeurs dans la table represente les probabiliter de piger y element apres x*/
			 if (total!=0) {
				 for(int m=1;m<alphabet.length()+2;m++) {
					 oddsTable[k][m] = (double)freqTable[k][m]/total; 
					 
//					 System.out.println(oddsTable[k][m]+" "+freqTable[k][m]+" "+total);
				 }
				 /* cree les valeurs charnieres de probabiliter*/
				 odds = 0;
				 for(int n=1;n<alphabet.length()+2;n++) {
					 odds += oddsTable[k][n];
					 if (oddsTable[k][n]!=0) oddsTable[k][n] = odds;
				 } 
			 }
		 }
		
		
	}
	/*Methode ajoutant un nom a la table de frequence*/
	public void addName(String name) {
		int x = 1;
		int y = 1;
		name = name.toLowerCase();
		
		/*Se sert des collones 0 pour trouver la bonne lettre*/
		while(!(freqTable[x][0]==name.charAt(0))) {
			x++;
        }
		/*La collone alphabet.length()+1 est la collone qui marque le vide donc ici c'est la premiere lettre*/
        freqTable[alphabet.length()+1][x]++;
        
        for(int i=1;i<name.length();i++) {
        	char charX = name.charAt(i-1);
        	char charY = name.charAt(i);
        	/*Se sert des collones 0 pour trouver la bonne lettre*/
        	x = 1;
        	while(!(freqTable[x][0]==charX)) {
        		x++;
        	}
        	y = 1;
        	while(!(freqTable[0][y]==charY)) {
        		y++;
        	}
        	
        	freqTable[x][y]++;
        	
        }
        /*La collone alphabet.length()+1 est la collone qui marque le vide donc ici c'est la derniere lettre*/
        freqTable[y][alphabet.length()+1]++;
	}
	/* Methode ajoutant une liste de nom d'un fichier texte*/
	public void addFromText(String textName) {

		this.textName = textName;
		
		
		
		File listName = new File(textName);
		try {
			Scanner reader = new Scanner(listName);
			 while (reader.hasNextLine()) {
			        String name = reader.nextLine();
			        addName(name);			            
			      }
			 createOdds(freqTable);
			 
			 
//			 for(int k=1;k<alphabet.length()+2;k++) {
//				 for(int l=1;l<alphabet.length()+2;l++) {
//					 System.out.print(oddsTable[k][l]+" ");
//				 } 
//				 System.out.print("\n");
//			 }
//			 
			 
			reader.close();	
		}catch(FileNotFoundException e) {
			System.out.println("lol");
			e.printStackTrace();
		}
	}
	
	public double[][] getOddsTable() {return oddsTable;}
	public int[][] getFreqTable() {return freqTable;}
	
	

}
