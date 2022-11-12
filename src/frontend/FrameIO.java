package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import datastruct.*;

public class FrameIO implements MouseListener, KeyListener{
	private static final int GAP=2;
	private JFrame frame=new JFrame();
	private JPanel btnRisolvi=new JPanel();
	private PanelCell[][] sudokuField=new PanelCell[9][9];
	private PanelCell selection;


	public FrameIO() {
		initGui();

		//ULTRA EXTREME
		sudokuField[0][0].setValue(8);
		sudokuField[1][0].setValue(6);
		sudokuField[4][0].setValue(2);
		sudokuField[3][1].setValue(7);
		sudokuField[7][1].setValue(5);
		sudokuField[8][1].setValue(9);

		sudokuField[4][3].setValue(6);
		sudokuField[6][3].setValue(8);
		sudokuField[1][4].setValue(4);
		sudokuField[2][5].setValue(5);
		sudokuField[3][5].setValue(3);
		sudokuField[8][5].setValue(7);

		sudokuField[1][7].setValue(2);
		sudokuField[6][7].setValue(6);
		sudokuField[2][8].setValue(7);
		sudokuField[3][8].setValue(5);
		sudokuField[5][8].setValue(9);

		frame.setFocusTraversalKeysEnabled(false); //for getting tab
		frame.addKeyListener(this);
		frame.pack();
		//frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void initGui() {

		//Rows
		JPanel rows=new JPanel();
		rows.setSize(300, 300);
		rows.setMinimumSize(rows.getSize());
		rows.setPreferredSize(rows.getSize());
		rows.setLayout(new GridLayout(10, 1, GAP, GAP));
		frame.add(rows);

		for(int y=0; y<9; y++) {
			//colon
			JPanel colons=new JPanel();
			colons.setLayout(new GridLayout(1, 9, GAP, GAP));
			rows.add(colons);

			for(int x=0; x<9; x++) {
				Color col=new Color(220, 220, 220);
				if((y/3+x/3)%2!=0) col=new Color(180, 180, 180);

				sudokuField[x][y]=new PanelCell(x,y, col);
				sudokuField[x][y].addMouseListener(this);
				colons.add(sudokuField[x][y]);
			}
		}

		//Btn
		btnRisolvi.addMouseListener(this);
		btnRisolvi.add(new JLabel("TESTasdsaad"));
		btnRisolvi.setBackground(Color.RED);
		rows.add(btnRisolvi);

	}

	//Setto o getto car delle
	private int[][] getMap() {
		int[][] map=new int[9][9];
		for(int y=0; y<9; y++)
			for(int x=0; x<9; x++)
				map[x][y]=sudokuField[x][y].getValue();
		return map;
	}
	public void setMap(int[][] map) {
		for(int y=0; y<9; y++)
			for(int x=0; x<9; x++)
				sudokuField[x][y].setValue(map[x][y]);
	}


	//MAIN
	public static void main(String[] args) {
		new FrameIO();
	}

	private boolean solving=false;
	public void solve(){
		if(solving)
			return;

		new Thread(Sudoku.getNormalSudoku(this, getMap())).start();
		solving=true;

	}


	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
	@Override public void mouseClicked(MouseEvent event) {
		setSelected(event.getComponent());
	}
	private void setSelected(Component component){
		//stop selection during and after solve
		if(solving) return;

		//Old selection
		if(selection!=null) selection.setSelected(false);
		selection=null;

		//btn solve
		if(((JPanel)component)==btnRisolvi){
			((JPanel)component).setBackground(new Color(200, 200, 200));
			solve();
			return;
		}

		//Cell
		selection=((PanelCell)component);
		selection.setBackground(new Color(100, 250, 255));
	}

	@Override public void keyReleased(KeyEvent arg0) {}
	@Override public void keyTyped(KeyEvent event) {}
	@Override public void keyPressed(KeyEvent event) {
		if(selection==null)
			return;

		final int dim=9;
		switch(event.getKeyCode()){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
			case KeyEvent.VK_W:
				setSelected(sudokuField[selection.x][(selection.y+dim-1)%dim]);
				break;
			case KeyEvent.VK_0:
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
			case KeyEvent.VK_6:
			case KeyEvent.VK_7:
			case KeyEvent.VK_8:
			case KeyEvent.VK_9:
				if(Sudoku.NUM_POSSIBILITY<=9)
					selection.setValue(event.getKeyChar()-'0');
				else
					selection.addDigitToValue(event.getKeyChar()-'0');
				//also do right
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_TAB:
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_D:
				setSelected(sudokuField[(selection.x+1)%dim][selection.y]);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
			case KeyEvent.VK_S:
			case KeyEvent.VK_ENTER:
				setSelected(sudokuField[selection.x][(selection.y+1)%dim]);
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_A:
				setSelected(sudokuField[(selection.x+dim-1)%dim][selection.y]);
				break;
			case KeyEvent.VK_BACK_SPACE:
			case KeyEvent.VK_CANCEL:
				selection.setValue(0);
				break;

		}

	}




}
