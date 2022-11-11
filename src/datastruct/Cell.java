package datastruct;

import java.util.Arrays;

public class Cell {
	private int value, x, y;
	private boolean possibility[];

	public Cell(int x, int y){ this(x, y, 0);}

	public Cell(int x, int y, int num){
		if(num<1 || num>9){
			this.value=0;
			possibility=new boolean[Sudoku.NUM_POSSIBILITY];
			Arrays.fill(possibility, true);
		}
		else this.value=num;

		this.x=x;
		this.y=y;
	}

	public boolean checkUnicityAndSetValue(){
		if(possibility==null)
			return false;

		//check unique
		int counter=0, temp=-1;
		for(int num=1; num<=Sudoku.NUM_POSSIBILITY; num++){
			if(possibility[num-1]){
				temp=num;
				counter++;
			}
		}

		if(counter==0){ //error
			throw new Error("Error in CellsGroup, no posssibility left in group");
		}
		else if(counter==1){ //unique
			this.value=temp;
			possibility=null; //remove possibility
			return true;
		}

		return false;
	}


	//SETTER AND GETTER  -------------------------------------------
	public boolean isPossible(int num){
		if(num<1 && num>Sudoku.NUM_POSSIBILITY)
			throw new Error("Error in cell: trying to get possibility of invalid value");

		if(possibility==null)
			return value==num;
		else
			return possibility[num-1];
	}

	public boolean setPossible(int num, boolean possible){
		if(num<1 || num>Sudoku.NUM_POSSIBILITY)
			throw new Error("Error in cell: trying to get possibility of invalid value");

		if(!isValueSet()){
			if(possibility[num-1]!=possible){
				possibility[num-1]=possible;
				return true;
			}
		}
		return false;
	}

	protected boolean setValue(int num){
		if(num<1 && num>Sudoku.NUM_POSSIBILITY)
			throw new Error("Error in cell: trying to setting invalid value");
		if(isValueSet())
			return false;
		value=num;
		possibility=null;
		return true;
	}

	public int getValue(){
		return value;
	}

	@Override
	public String toString(){
		if(possibility==null || value>0){
			return "Cell["+x+","+y+"] -> v="+value+"\n";
		}
		String res="Cell["+x+","+y+"] ->(";

		for(int i=0; i<Sudoku.NUM_POSSIBILITY; i++){
			res+=possibility[i]?(i+1)+"":"-";
		}
		return res+")\n";
	}

	private boolean isValueSet(){
		return (value>0 || possibility==null);
	}
}
