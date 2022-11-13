package datastruct;

import java.util.*;
import utility.Coord;
import utility.Rect;

public class SudokuSets {
	public static int NUM_POSSIBILITY=9;
	public static boolean DEBUG=false;
	protected ArrayList<CellsGroup> cellsGroup=new ArrayList<>();
	protected ArrayList<SyncedGroup> groupsSynced=new ArrayList<>();
	protected TreeMap<Coord, Cell> cells=new TreeMap<>();

	protected SudokuSets(Cell[][] allCells){
		for(int x=0; x<allCells.length; x++){
			for(int y=0; y<allCells[x].length; y++){
				this.cells.put(new Coord(x, y), allCells[x][y]);
			}
		}
	}

	public void addRectangleGroup(Rect rect, int type){
		Set<Cell> listGroup=new HashSet<>();

		for(int y=rect.getTop(); y<=rect.getBottom(); y++){
			for(int x=rect.getLeft(); x<=rect.getRight(); x++){
				Cell tmp=cells.get(new Coord(x,y));
				if(tmp==null)
					throw new Error("Error in SudokuSets cell in group doesn't exist");

				listGroup.add(tmp);
			}
		}

		cellsGroup.add(new CellsGroup(listGroup, type));
	}

	//SYNCED
	public void createSyncedBlocks(){
		for(CellsGroup tmp1:cellsGroup){
			for(CellsGroup tmp2:cellsGroup){
				if(tmp1.getType()==tmp2.getType())
					continue; //ignore equals

				CellsGroup sync1=tmp1.minus(tmp2), sync2=tmp2.minus(tmp1);
				//if no empty or full group
				if(tmp1.getSize()!=0 && tmp2.getSize()!=0 && tmp1.getSize()!=sync1.getSize() && tmp2.getSize()!=sync2.getSize())
					groupsSynced.add(new SyncedGroup(sync1, sync2));
			}
		}
	}

}
