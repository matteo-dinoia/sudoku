package datastruct;

import frontend.FrameIO;
import utility.Coord;
import utility.Rect;

public class Sudoku extends SudokuSets implements Runnable{
	FrameIO f;
	public static Sudoku getNormalSudoku(FrameIO f, int cellsValue[][]){
		Sudoku res;

		//Create cells
		Cell[][] allCells=new Cell[9][9];
		for(int x=0; x<9; x++)
			for(int y=0; y<9; y++)
				allCells[x][y]=new Cell(x, y, cellsValue[x][y]);
		res=new Sudoku(allCells);
		res.f=f;

		//Row and column
		for(int i=0; i<9; i++){
			res.addRectangleGroup(new Rect(i, 0, i, 8), CellsGroup.TYPE_LINE);
			res.addRectangleGroup(new Rect(0, i, 8, i), CellsGroup.TYPE_LINE);
		}

		//Row and column
		for(int x=0; x<3; x++)
			for(int y=0; y<3; y++)
				res.addRectangleGroup(new Rect(x*3, y*3, (x+1)*3-1, (y+1)*3-1), CellsGroup.TYPE_BLOCK);

		res.createSyncedBlocks();

		res.debug();
		return res;
	}

	private Sudoku(Cell[][] allCells) {
		super(allCells);
	}

	private void startSolver(){
		boolean somethingChanged=true;
		int counter=0; //TODO remove
		while(somethingChanged && counter<=10000){ //TODO CHANGE CONDITION TO &&
			somethingChanged=false;
			counter++;

			//Group unicity
			for(CellsGroup tmp:cellsGroup)
				somethingChanged=tmp.checkUnicityAndSetValue()||somethingChanged;

			//Cell unicity
			for(Cell tmp:cells.values())
				somethingChanged=tmp.checkUnicityAndSetValue()||somethingChanged;

			//Synced block
			for(SyncedGroups tmp:groupsSynced)
				somethingChanged=tmp.checkSyncAndSetValue()||somethingChanged;
		}

		System.out.println(counter);
	}

	@Override
	public void run() {
		try{
			startSolver();
		}catch(Error e){
			e.printStackTrace();
		}

		debug();
	}

	public void debug(){
		for(CellsGroup tmp: cellsGroup){
			System.out.println(""+tmp);
		}
		Cell[][] allCells=new Cell[9][9];
		for(int x=0; x<9; x++)
			for(int y=0; y<9; y++)
				allCells[x][y]=cells.get(new Coord(x,y));
		f.setMap(allCells);
	}
}
