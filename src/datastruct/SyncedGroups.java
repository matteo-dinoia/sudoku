package datastruct;

public class SyncedGroups {
	private CellsGroup group1, group2;

	public SyncedGroups(CellsGroup group1, CellsGroup group2){
		this.group1=group1;
		this.group2=group2;
	}

	public boolean checkSyncAndSetValue(){
		boolean somethingChanged=false;

		for(int checkVal=1; checkVal<Sudoku.NUM_POSSIBILITY; checkVal++){
			//Check if present in both group
			boolean g1Contains=false, g2Contains=false;
			for(int i=0; i<group1.cells.length; i++){
				if(group1.cells[i].getValue()==checkVal || group1.cells[i].isPossible(checkVal)){
					g1Contains=true;
					break;
				}
			}
			for(int i=0; i<group2.cells.length; i++){
				if(group2.cells[i].getValue()==checkVal || group2.cells[i].isPossible(checkVal)){
					g2Contains=true;
					break;
				}
			}

			if(!g1Contains && g2Contains){ //to remove in group 2
				for(int i=0; i<group2.cells.length; i++)
					somethingChanged=group2.cells[i].setPossible(checkVal, false)||somethingChanged;
			}
			else if(g1Contains && !g2Contains){ //to remove in group 1
				for(int i=0; i<group1.cells.length; i++)
					somethingChanged=group1.cells[i].setPossible(checkVal, false)||somethingChanged;
			}
		}

		return somethingChanged;
	}
}
