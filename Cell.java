package uk.ac.cam.dsjw2.MazeAI;

import java.util.ArrayList;

public class Cell {
	public boolean visited;
	public ArrayList<Cell> neighbours; //physical neighbours
	public ArrayList<Cell> paths; //accessible next cells
	public int x,y; //array index
	public Cell parent; //for generator
	public Cell ptr; //backpointer for the solver to track solution
	
	public Cell(int x,int y) {
		this.x = x;
		this.y = y;
		visited = false;
		neighbours = new ArrayList<Cell>();
		paths = new ArrayList<Cell>();
		parent = null;
		ptr = null;
	}
}
