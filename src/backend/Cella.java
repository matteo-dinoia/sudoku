package backend;
import utility.C;

public class Cella {
	private int valore;
	private boolean[] pos;
	private int possibili=9;
	
	private final C coord;
	
	public Cella(int x, int y){
		valore=0;
		rendiTuttoPossibile();
		
		this.coord=new C(x,y);
	}
	
	/**creo e rendo tutto vero array possibilità**/
	private void rendiTuttoPossibile() {
		
		pos=new boolean[9];
		
		for(int i=0; i<9; i++) {
			pos[i]=true;
		}
	}

	public Cella(int x, int y, int val) {
		if(val<0||val>9) {
			valore=0;
			rendiTuttoPossibile();
			
			System.err.println("Valore in cella <0 o >9");
		}
		else {
			valore=val;
		}
		
		this.coord=new C(x,y);
	}
	
	/**Inserire numero da 1 a 9
	 * In caso di problema ritorna errore 
	 * @throws Exception **/
	public boolean getPossibileByIndex(int index) throws Exception {
		if(index<1||index>9) {
			throw new Exception("Errore l'indice desiderato da ottenere non esiste");
		}
		else if(valore>0) {
			return (valore==index);
		}
		
		return getPossibile(index);	
	}
	
	/**Inserire numero da 1 a 9
	 * In caso di problema ritorna errore 
	 * @throws Exception **/
	public void setPossibileByIndex(int index, boolean val) throws Exception {
		if(index<1||index>9) {
			throw new Exception("Errore l'indice desiderato da modificare non esiste");
		}
		else if(valore>0) {
			return;
		}
		
		setPossibile(index, val);
		
		
		if(possibili==1) {
			setValore(findUnoPossibile());
		}
	}
	
	private int findUnoPossibile() {
		for(int i=1; i<=9; i++) {
			if(getPossibile(i)) return i;
		}
		return 0;
	}

	/**Set il valore se non già settato (da 1 a 9, 0 inutile)
	 * @throws Exception **/
	private void setValore(int index) throws Exception {
		if(index<0||index>9) {
			throw new Exception("Errore il valore da inserire non è valido");
		}
		else if(valore>0) {
			return;
		}
		
	}

	//BASE METODI
	private boolean getPossibile(int index) {
		return pos[index-1];
	}
	private void setPossibile(int index, boolean val) {
		possibili+= boolToInt(pos[index-1])-boolToInt(val);
		pos[index-1]=val;
	}
	public C getCoord() {
		return coord;
	}
	public int getValore() {
		return valore;
		
	}
	
	//UTILITA'
	private int boolToInt(boolean val) {
		return val?1:0;
	}
}
