package backend;

public class SudokuSolverRunnable implements Runnable {
	
	private SudokuTabella mappa;
	
	//finito?
	public boolean finished=false;
	public boolean hasFinished() {
		return finished;
	}
	
	
	//input e output
	public SudokuSolverRunnable(int[][] map) {
		mappa=new SudokuTabella(map);
	}
	public int[][] getSolvedUntilNow(){
		return mappa.getMappa();
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
