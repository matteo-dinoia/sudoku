package datastruct;

import java.util.*;

public class CellsGroup {
	private Set<Cell> cells=new HashSet<>();
	public static final int TYPE_LINE=1, TYPE_BLOCK=2, TYPE_SYNC=3;
	private int type;


	public CellsGroup(Set<Cell> cells, int type){
		if(cells!=null)
			this.cells.addAll(cells);
		this.type=type;
	}

	public boolean checkUnicityAndSetValue(){
		boolean somethingChanged=false;

		for(int checkVal=1; checkVal<=Sudoku.NUM_POSSIBILITY; checkVal++){
			//Check if unique in group
			int counter=0;
			Cell foundCell=null;
			for(Cell tmp:cells){
				if(tmp.getValue()==checkVal){
					counter=1;
					foundCell=tmp;
					break;
				}
				else if(tmp.isPossible(checkVal)){
					counter++;
					foundCell=tmp;
				}

			}

			if(counter==0){ //error
				throw new Error("Error in CellsGroup, no posssibility left in group");
			}
			else if(counter==1){ //unique
				somethingChanged=foundCell.setValue(checkVal);
				for(Cell tmp:cells) //remove possibility
					somethingChanged=tmp.setPossible(checkVal, false)||somethingChanged;
			}
		}

		return somethingChanged;
	}

	@Override
	public String toString(){
		String res="Cells (type="+type+") group contains\n";
		for(Cell tmp : cells)
			res+="\t\t"+tmp.toString();

		return res;
	}

	public CellsGroup minus(CellsGroup tmp2) {
		Set<Cell> res=new HashSet<>();
		res.addAll(this.cells);
		res.removeAll(tmp2.cells);
		return new CellsGroup(res, type);
	}

	public boolean containsValueOrPossibility(int value){
		for(Cell tmp : cells)
			if(tmp.getValue()==value || tmp.isPossible(value))
				return true;

		return false;
	}

	public boolean removePossibility(int value){
		boolean somethingChanged=false;
		for(Cell tmp : cells)
			somethingChanged|=tmp.setPossible(value, false);

		return somethingChanged;
	}

	public int getSize(){
		return cells.size();
	}

	public int getType() {
		return type;
	}
}