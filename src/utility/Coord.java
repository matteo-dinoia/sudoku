package utility;

public class Coord implements Comparable<Coord>{
	public int x, y;

	public Coord(int x, int y) {
		this.x=x;
		this.y=y;
	}

	@Override
	public int compareTo(Coord second) {
		if(this.y<second.y)
			return -1;
		else if(this.y>second.y)
			return 1;

		if(this.x<second.x)
			return -1;
		else if(this.x>second.x)
			return 1;

		return 0;
	}
}
