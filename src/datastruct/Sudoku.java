package datastruct;

import java.util.*;
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

		//Block
		for(int x=0; x<3; x++)
			for(int y=0; y<3; y++)
				res.addRectangleGroup(new Rect(x*3, y*3, (x+1)*3-1, (y+1)*3-1), CellsGroup.TYPE_BLOCK);

		res.createSyncedBlocks();

		//res.debug();
		return res;
	}

	private Sudoku(Cell[][] allCells) {
		super(allCells);
	}
	private void overlayAll(){
		for(Cell tmp:cells.values())
			tmp.overlayCell();
	}

	private boolean isSolved() {
		for(Cell tmp:cells.values())
			if(!tmp.hasValue()) return false;
		return true;
	}

	private void startSolver(){
		oneIterationSolver();
		Iterator<Cell> cells=this.cells.values().iterator();
		Cell actualCell=null;
		int possibility=Sudoku.NUM_POSSIBILITY;
		while(!isSolved()){
			updateResult(false, null);


			//CHOICE
			do{
				if(++possibility<=Sudoku.NUM_POSSIBILITY)
					continue; //if not change cell

				possibility=1;
				do{
					actualCell=cells.next();
					if(actualCell==null)
						return;
				}while(actualCell.hasValue());
			}while(actualCell.isPossible(possibility));

			//SET
			overlayAll();
			actualCell.setValue(possibility);

			try{//ignore error
				oneIterationSolver();
			}catch(Error e){}

		}
	}



	private void oneIterationSolver(){
		boolean somethingChanged=true;
		int counter=0;
		while(somethingChanged && counter<=10000){
			somethingChanged=false;
			counter++;

			//Group unicity
			for(CellsGroup tmp:cellsGroup)
				somethingChanged=tmp.checkUnicityAndSetValue()||somethingChanged;

			//Cell unicity
			for(Cell tmp:cells.values())
				somethingChanged=tmp.checkUnicityAndSetValue()||somethingChanged;

			//Synced block
			for(SyncedGroup tmp:groupsSynced)
				somethingChanged=tmp.checkSyncAndSetValue()||somethingChanged;
		}
	}

	@Override
	public void run() {
		try{
			startSolver();
			updateResult(true, null);
		}catch(Error e){
			updateResult(false, e.getMessage());
		}

	}

	private void updateResult(boolean solved, String errorString){
		//CREATE MAP
		int[][] allCells=new int[9][9];
		for(int x=0; x<9; x++)
			for(int y=0; y<9; y++)
				allCells[x][y]=cells.get(new Coord(x,y)).getValue();

		f.putData(allCells, solved, errorString);
		//System.out.print("1 Ciclo completato");
		//debug();
	}

	public void debug(){
	/*
		//PRINT GROUP AND SYNC
		for(CellsGroup tmp: cellsGroup)
			System.out.println(""+tmp);
		for(SyncedGroup tmp: groupsSynced)
			System.out.println(""+tmp);

		//PRINT MAP
		for(int y=0; y<9; y++){
			for(int x=0; x<9; x++){
				for(int pos=1; pos<=9; pos++){
					System.out.print(cells.get(new Coord(x,y)).isPossible(pos)?pos:" ");
				}
				if(x==2||x==5) System.out.print("||");
				else System.out.print("|");
			}

			if(y==2||y==5)System.out.print(
				"\n--------------------------------------------------------------------------------------------");
			if(y!=8)System.out.print(
				"\n--------------------------------------------------------------------------------------------");
			System.out.println();
		}
		*/
	}
}
