package frontend;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import backend.SudokuSolverRunnable;
import interfaceListener.UpdateMapListener;

public class Finestra extends JFrame implements ActionListener, UpdateMapListener{
	private static final long serialVersionUID = 206677201194940947L;
	
	private JButton btnRisolvi=new JButton("Risolvi");
	private JTextField[][] sudokuField=new JTextField[9][9];
	

	public Finestra() {
		initGui();
		
		setSize(225, 250);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void initGui() {
		//pannello e layout
		JPanel principale=new JPanel();
		this.add(principale);
		principale.setLayout(new BorderLayout());
		
		//Bottone in basso
		btnRisolvi.setActionCommand("Risolvi");
		btnRisolvi.addActionListener(this);
		principale.add(btnRisolvi, BorderLayout.SOUTH);
		
		//lista di testi in alto
		JPanel griglia=new JPanel();
		griglia.setLayout(new GridLayout(9, 9));
		principale.add(griglia, BorderLayout.NORTH);
		
		for(int y=0; y<9; y++) {
			for(int x=0; x<9; x++) {
				sudokuField[x][y]=new JTextField(3);
				griglia.add(sudokuField[x][y]);
			}
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		//controllo se premuto tasto risolvi
		if(event.getActionCommand().equals("Risolvi")) {
			
			//disabilito bottoni e runno
			btnRisolvi.setEnabled(false);
			new Thread(new SudokuSolverRunnable(getMap())).start();
		}
		
	}
	
	
	//Setto o getto car delle 
	private int[][] getMap() {
		//creo mappa
		int[][] map=new int[9][9];
		
		//la riempo
		for(int y=0; y<9; y++) {
			for(int x=0; x<9; x++) {
				int temp=0;
				
				//evitando problemi di parse
				try {
					temp=Integer.getInteger(sudokuField[x][y].getText());
				}catch (Exception e) {
					temp=0;
				}
				
				map[x][y]=temp;
			}
		}
		
		return map;
	}
	
	@Override
	public void setMap(int[][] map) {
		for(int y=0; y<9; y++) {
			for(int x=0; x<9; x++) {
				sudokuField[x][y].setText(map[x][y]+"");
			}
		}
	}


	//MAIN
	public static void main(String[] args) {
		new Finestra().setVisible(true);
	}


}
