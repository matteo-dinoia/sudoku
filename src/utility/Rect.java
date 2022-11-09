package utility;

public class Rect {
	private Coord topLeft, bottomRight;

	public Rect(Coord margin1, Coord margin2){
		this(margin1.x, margin1.y, margin2.x, margin2.y);
	}

	public Rect(int x1, int y1, int x2, int y2){
		if(x1>x2){ //sort x1 < x2
			int t=x1;
			x1=x2;
			x2=t;
		}
		if(y1>y2){ //sort y1 < y2
			int t=y1;
			y1=y2;
			y2=t;
		}

		topLeft=new Coord(x1, y1);
		bottomRight=new Coord(x2, y2);
	}

	//GETTER AND SETTER
	public int getTop(){
		return topLeft.y;
	}
	public int getBottom(){
		return bottomRight.y;
	}
	public int getLeft(){
		return topLeft.x;
	}
	public int getRight(){
		return bottomRight.x;
	}

	public int getAreaMarginIncluded() {
		return (getRight()+1-getLeft())*(getBottom()+1-getTop());
	}
}
