package datastruct;

import java.util.*;
import utility.Coord;
import utility.Rect;

public class SudokuSets {
	public final static int NUM_POSSIBILITY=9;
	protected ArrayList<CellsGroup> cellsGroup=new ArrayList<>();
	protected ArrayList<SyncedGroups> groupsSynced=new ArrayList<>();
	protected TreeMap<Coord, Cell> cells=new TreeMap<>();

	protected SudokuSets(Cell[][] allCells){ //TODO ADD CELLS
		for(int x=0; x<allCells.length; x++){
			for(int y=0; y<allCells[x].length; y++){
				this.cells.put(new Coord(x, y), allCells[x][y]);
			}
		}
	}

	public void addRectangleGroup(Rect rect, int type){
		Cell[] listGroup=new Cell[rect.getAreaMarginIncluded()];
		int counter=0;
		for(int y=rect.getTop(); y<=rect.getBottom(); y++){
			for(int x=rect.getLeft(); x<=rect.getRight(); x++){
				Cell tmp=cells.get(new Coord(x,y));
				if(tmp==null){
					throw new Error("Error in SudokuSets cell in group doesn't exist");
					//TODO not error
				}

				listGroup[counter++]=tmp;
			}
		}

		cellsGroup.add(new CellsGroup(listGroup, type));
	}

	//SYNCED
	public void createSyncedBlocks(){
		for(CellsGroup tmp1:cellsGroup){
			for(CellsGroup tmp2:cellsGroup){
				if(tmp1.type!=tmp2.type){
					CellsGroup sync1=tmp1.minus(tmp2), sync2=tmp2.minus(tmp1);
					if(sync1!=null && sync2!=null)
						groupsSynced.add(new SyncedGroups(sync1, sync2));
				}
			}
		}
	}

}
