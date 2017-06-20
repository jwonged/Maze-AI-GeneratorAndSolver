package uk.ac.cam.dsjw2.MazeAI;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
	private ArrayList<ArrayList<Cell>> maze;
	Cell origin;
	Cell end;
	private int x,y;//width and height
	
	public Maze(int x, int y) {
		this.x = x;
		this.y = y;
		
		//initialize cells
		maze = new ArrayList<ArrayList<Cell>>(x);
		for (int j=0; j<x; j++) {
			maze.add(new ArrayList<Cell>(y));
			for (int i=0; i<y; i++) {
				maze.get(j).add(new Cell(j,i));
			}
		}
		
		//pre-compute neighbours
		initNeighbours();
	}
	
	private void initNeighbours() {
		//precompute the 4 physical neighbours around cell
		for (ArrayList<Cell> row : maze) {
			for (Cell c : row) {
				if (c.x > 0) c.neighbours.add(maze.get(c.x-1).get(c.y));
				if (c.y > 0) c.neighbours.add(maze.get(c.x).get(c.y-1));
				if (c.x < x-1) c.neighbours.add(maze.get(c.x+1).get(c.y));
				if (c.y < y-1) c.neighbours.add(maze.get(c.x).get(c.y+1));
			}
		}
	}
	
	public void iterate() {
		System.out.println(maze.size());
		for (ArrayList<Cell> row : maze) {
			for (Cell c : row) {
				System.out.print("("+c.x+","+c.y+")");
				for (Cell n : c.neighbours)
					System.out.print("("+n.x+","+n.y+")");
				System.out.println();

			}
			System.out.println();
		}
	}
	
	public void generateMaze() {
		//remove all neighbours, leave only possible paths
		Random rand = new Random();
		origin = maze.get(0).get(0);
		Stack<Cell> stack = new Stack<Cell>();
		stack.add(origin);
		
		while (!stack.isEmpty()) {
			Cell current = stack.pop();
			Cell check;
			
			current.visited = true;
			if (current.y == this.y-1) end = current;
			
			//Take rand neighbour: if not visited, add as path and push to stack
			while (current.neighbours.size()>0) {
				int randNum = rand.nextInt(current.neighbours.size());
				if (!(check = current.neighbours.remove(randNum)).visited) {
					current.paths.add(check);
					stack.push(check);
				}
			}
			
		}
		
	}
	
}
