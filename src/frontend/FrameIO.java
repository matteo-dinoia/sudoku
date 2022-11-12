package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import datastruct.*;

public class FrameIO implements MouseListener, KeyListener{
	private static final int GAP=2;
	private JFrame frame=new JFrame();
	private JPanel btnRisolvi=new JPanel();
	private JLabel[][] sudokuField=new JLabel[9][9];


	public FrameIO() {
		initGui();

		//ULTRA EXTREME
		sudokuField[0][0].setText(""+8);
		sudokuField[1][0].setText(""+6);
		sudokuField[4][0].setText(""+2);
		sudokuField[3][1].setText(""+7);
		sudokuField[7][1].setText(""+5);
		sudokuField[8][1].setText(""+9);

		sudokuField[4][3].setText(""+6);
		sudokuField[6][3].setText(""+8);
		sudokuField[1][4].setText(""+4);
		sudokuField[2][5].setText(""+5);
		sudokuField[3][5].setText(""+3);
		sudokuField[8][5].setText(""+7);

		sudokuField[1][7].setText(""+2);
		sudokuField[6][7].setText(""+6);
		sudokuField[2][8].setText(""+7);
		sudokuField[3][8].setText(""+5);
		sudokuField[5][8].setText(""+9);

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
				sudokuField[x][y]=new JLabel(" ");
				sudokuField[x][y].setVerticalAlignment(JLabel.CENTER);
				JPanel p=new JPanel();
				p.add(sudokuField[x][y]);
				p.addMouseListener(this);
				if((y/3+x/3)%2!=0) p.setBackground(new Color(180, 180, 180));
				else p.setBackground(new Color(220, 220, 220));
				colons.add(p);
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
		//create map
		int[][] map=new int[9][9];

		//put inside value
		int temp;
		for(int y=0; y<9; y++) {
			for(int x=0; x<9; x++) {
				try {  temp=Integer.parseInt(sudokuField[x][y].getText());}
				catch (Exception e) {temp=0;}

				map[x][y]=temp;
			}
		}

		return map;
	}
	public void setMap(int[][] map) {
		for(int y=0; y<9; y++) {
			for(int x=0; x<9; x++) {
				if(map[x][y]==0)
					sudokuField[x][y].setText("_");
				else
					sudokuField[x][y].setText(map[x][y]+"");
			}
		}
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

	private JPanel selection;
	private Color oldColorSelection;
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
	@Override public void mouseClicked(MouseEvent event) {
		JPanel newSelection=((JPanel)event.getComponent());

		if(newSelection==btnRisolvi){
			newSelection.setBackground(new Color(200, 200, 200));
			solve();
			return;
		}

		//old reset
		if(selection!=null)
			selection.setBackground(oldColorSelection);
		//new
		oldColorSelection=newSelection.getBackground();
		newSelection.setBackground(new Color(100, 250, 255));
		selection=newSelection;
	}

	@Override public void keyReleased(KeyEvent arg0) {}
	@Override public void keyTyped(KeyEvent event) {}
	@Override public void keyPressed(KeyEvent event) {
		switch(event.getKeyCode()){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
			case KeyEvent.VK_W:
				System.out.println("TEST1");
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_TAB:
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_D:
				System.out.println("TEST2");
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
			case KeyEvent.VK_S:
				System.out.println("TEST3");
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_A:
				System.out.println("TEST4");
				break;
			case KeyEvent.VK_ENTER:
				System.out.println("TEST5");
				break;
			case KeyEvent.VK_BACK_SPACE:
			case KeyEvent.VK_CANCEL:
				System.out.println("TEST6");
				break;
		}

	}




}
