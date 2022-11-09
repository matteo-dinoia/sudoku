package datastruct;

public class CellsGroup {
	public Cell cells[];
	public static final int TYPE_LINE=1, TYPE_BLOCK=2, TYPE_SYNC=3;
	public int type;

	public CellsGroup(Cell cells[], int type){
		this.cells=cells;
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
		for(int i=0; i<cells.length; i++){
			res+="\t"+cells[i].toString();
		}
		return res;
	}

	public CellsGroup minus(CellsGroup tmp2) {
		CellsGroup tmp1=this;
		int counter=0;

		//COUNT ELEMENT
		for(Cell elementIn1:tmp1.cells){
			for(Cell elementIn2:tmp2.cells){
				if(elementIn1==elementIn2)
					continue;
			}
			counter++;
		}
		if(counter==0) return null;

		//CREATE ARRAY
		Cell res[]=new Cell[counter];
		counter=0;
		for(Cell elementIn1:tmp1.cells){
			for(Cell elementIn2:tmp2.cells){
				if(elementIn1==elementIn2)
					continue;
			}
			res[counter++]=elementIn1;
		}


		return new CellsGroup(res, TYPE_SYNC);
	}
}
