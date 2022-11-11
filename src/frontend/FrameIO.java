package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import datastruct.Cell;
import datastruct.Sudoku;

public class FrameIO implements ActionListener{

	private JFrame frame=new JFrame();
	private JButton btnRisolvi=new JButton("Risolvi");
	private JTextField[][] sudokuField=new JTextField[9][9];


	public FrameIO() {
		initGui();

		//TODO Remove
		//EASY
		/*
		int m[][]={
			{0,0,5,  0,0,0,  0,4,0},
			{0,0,4,  0,0,3,  5,0,8},
			{0,6,8,  0,0,4,  3,0,1},

			{0,0,0,  6,8,0,  0,3,0},
			{0,0,2,  0,0,0,  8,0,0},
			{0,3,0,  0,2,9,  0,0,0},

			{5,0,7,  2,0,0,  1,8,0},
			{6,0,9,  5,0,0,  4,0,0},
			{0,8,0,  0,0,0,  2,0,0}
		};
		for(int x=0; x<9; x++){
			for(int y=0; y<9; y++){
				sudokuField[y][x].setText(""+m[x][y]);
			}
		}*/
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

		frame.setSize(225, 250);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void initGui() {
		//panel and layout
		JPanel principale=new JPanel();
		frame.add(principale);
		principale.setLayout(new BorderLayout());

		//button
		btnRisolvi.setActionCommand("Risolvi");
		btnRisolvi.addActionListener(this);
		principale.add(btnRisolvi, BorderLayout.SOUTH);

		//matrix of number
		JPanel griglia=new JPanel();
		griglia.setLayout(new GridLayout(9, 9));
		principale.add(griglia, BorderLayout.NORTH);

		for(int y=0; y<9; y++) {
			for(int x=0; x<9; x++) {
				sudokuField[x][y]=new JTextField(3);
				if((y/3+x/3)%2!=0)
					sudokuField[x][y].setBackground(Color.LIGHT_GRAY);
				griglia.add(sudokuField[x][y]);
			}
		}


	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("Risolvi")) {
			//disable button and run
			btnRisolvi.setEnabled(false);
			new Thread(Sudoku.getNormalSudoku(this, getMap())).start();
		}
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

	public void setMap(Cell[][] map) {
		for(int y=0; y<9; y++) {
			for(int x=0; x<9; x++) {
				if(map[x][y].getValue()==0)
					sudokuField[x][y].setText("_");
				else
					sudokuField[x][y].setText(map[x][y].getValue()+"");
			}
		}
		debug(map);

	}


	//MAIN
	public static void main(String[] args) {
		new FrameIO();
	}

	public void debug(Cell[][] map){
		System.out.println();
		for(int y=0; y<9; y++){
			for(int x=0; x<9; x++){
				for(int i=1; i<=3; i++){
					if(map[x][y].isPossible(i)){
						System.out.print(i);
					}
					else {
						System.out.print("-");
					}
				}
				System.out.print("    ");
			}
			System.out.println();
			for(int x=0; x<9; x++){
				for(int i=4; i<=6; i++){
					if(map[x][y].isPossible(i)){
						System.out.print(i);
					}
					else {
						System.out.print("-");
					}
				}
				System.out.print("    ");
			}
			System.out.println();
			for(int x=0; x<9; x++){
				for(int i=7; i<=9; i++){
					if(map[x][y].isPossible(i)){
						System.out.print(i);
					}
					else {
						System.out.print("-");
					}
				}
				System.out.print("    ");
			}
			if(y%3==2){
				System.out.println();
			}
			System.out.println();
			System.out.println();
		}
		System.out.println("-----------------------------------------------------");
	}
}
