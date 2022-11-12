package frontend;

import javax.swing.*;

import datastruct.Sudoku;

import java.awt.*;

public class PanelCell extends JPanel{
	private final static Color selectedColor=new Color(100, 250, 255);
	private Color originalColor;
	private JLabel lbl=new JLabel();
	private int value=0;
	public int x, y;

	public PanelCell(int x, int y, Color col){ this(x, y, col, 0);}
	public PanelCell(int x, int y, Color col, int value){
		//coord
		this.x=x;
		this.y=y;

		//Color
		originalColor=col;
		this.setBackground(originalColor);

		//Value
		this.add(lbl);
		setValue(value);
	}

	//SETTER AND GETTER
	public int getValue(){
		return value;
	}
	public void setValue(int value){
		if(value<0||value>Sudoku.NUM_POSSIBILITY)
			return;

		this.value=value;
		if(value!=0) lbl.setText(value+"");
		else lbl.setText(" ");
	}
	public void addDigitToValue(int digit) {
		setValue(value*10+digit);
	}

	public void setSelected(boolean selected){
		if(selected)
			this.setBackground(selectedColor);
		else
			this.setBackground(originalColor);
	}


}
