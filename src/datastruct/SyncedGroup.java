package datastruct;

public class SyncedGroup {
	private CellsGroup group1, group2;

	public SyncedGroup(CellsGroup group1, CellsGroup group2){
		this.group1=group1;
		this.group2=group2;
	}

	public boolean checkSyncAndSetValue(){
		boolean somethingChanged=false;

		for(int checkVal=1; checkVal<Sudoku.NUM_POSSIBILITY; checkVal++){
			//Check if present in both group
			boolean g1Contains=group1.containsValueOrPossibility(checkVal);
			boolean g2Contains=group2.containsValueOrPossibility(checkVal);

			//remove to resync
			if(!g1Contains && g2Contains)
				group2.removePossibility(checkVal);
			else if(g1Contains && !g2Contains)
				group1.removePossibility(checkVal);
		}

		return somethingChanged;
	}

	@Override
	public String toString(){
		return "Synced group\n\t"+group1+"\n\t"+group2;
	}
}
