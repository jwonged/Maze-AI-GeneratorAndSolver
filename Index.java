package uk.ac.cam.dsjw2.MazeAI;

import java.util.ArrayList;

public class Index {
	public int row, col;
	public ArrayList<Index> connectTo;
	public Index(int row, int col) {
		this.row = row; 
		this.col = col;
		connectTo = new ArrayList<Index>();
	}
}
