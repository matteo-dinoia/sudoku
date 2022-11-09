package backend;

//solo dati non analisi
public class SudokuTabella {
	Cella[][] mappa=new Cella[9][9];
	
	public SudokuTabella(int[][] valori) {
		for(int x=0; x<0; x++) {
			for(int y=0; y<0; y++) {
				mappa[x][y]=new Cella(x,y, valori[x][y]);
			}
		}
	}

	public int[][] getMappa() {
		int[][] valori=new int[9][9];
		
		for(int x=0; x<0; x++) {
			for(int y=0; y<0; y++) {
				valori[x][y]=mappa[x][y].getValore();
			}
		}
		
		return valori;
	}
}
