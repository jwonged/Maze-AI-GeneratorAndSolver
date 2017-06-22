package uk.ac.cam.dsjw2.MazeAI;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
	private ArrayList<ArrayList<Cell>> maze;
	private ArrayList<ArrayList<Index>> dual;
	private int x,y; 
	public Cell origin;
	public Cell end;
	
	public Maze(int x, int y) {
		this.x = x;
		this.y = y;
		
		//initialize cells and dual
		maze = new ArrayList<ArrayList<Cell>>(x);
		dual = new ArrayList<ArrayList<Index>>(x+1);
		
		for (int j=0; j<=x; j++) {
			if (j<x) maze.add(new ArrayList<Cell>(y));
			dual.add(new ArrayList<Index>(y+1));
			
			for (int i=0; i<=y; i++) {
				if (i<y && j<x) maze.get(j).add(new Cell(j,i));
				dual.get(j).add(new Index(j,i));
			}
			
		}
		
		origin = maze.get(0).get(0);
		
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
	public void initDual(Grid grid) {
		//Create a dual graph
		for (ArrayList<Index> row : dual) {
			for (Index c : row) {
				if (c.row > 0) c.connectTo.add(dual.get(c.row-1).get(c.col));
				if (c.col > 0) c.connectTo.add(dual.get(c.row).get(c.col-1));
				if (c.row < x) c.connectTo.add(dual.get(c.row+1).get(c.col));
				if (c.col < y) c.connectTo.add(dual.get(c.row).get(c.col+1));
			}
		}
		//send all edges to GUI
		for (ArrayList<Index> row : dual) {
			for (Index c : row) {
				for (Index i : c.connectTo) grid.addEdge(new Edge(c,i));
			}
		}
		
	}
	
	public void iterate() {
		System.out.println(maze.size());
		for (ArrayList<Cell> row : maze) {
			for (Cell c : row) {
				System.out.print("("+c.x+","+c.y+")");
				for (Cell n : c.neighbours) System.out.print("("+n.x+","+n.y+")");
				System.out.println();
			}
			System.out.println();
		}
	}
	
	public void dfsgenerator(Grid grid) {
		//dfs maze
		Stack<Cell> stack = new Stack<Cell>();
		stack.add(origin);
		origin.visited = true;
		
		while (!stack.isEmpty()) {
			Cell current = stack.pop();
			for (Cell n : current.neighbours) {
				if (!n.visited) {
					n.visited = true;
					stack.push(n);
					current.paths.add(n);
					grid.removeEdge(current,n);
					
				}
			}
		}
		System.out.println("end");
	}
	
	public void dfsi (Grid grid, Cell current, Cell parent) {
		if (current.y == this.y-1) end = current;
		if (current.visited || current == null) return;
		
		System.out.println("Visiting: (" +current.x+","+current.y+")");
		current.visited = true;
		if (parent != null) {
			System.out.println("Removing: (" +current.x+","+current.y+")"+" parent: ("+parent.x+","+parent.y+")");
			grid.removeEdge(current,parent);
			parent.paths.add(current);
		}
		
		for (Cell n : current.neighbours) {
			if (!n.visited) dfsi(grid,n, current);
			
		}
		
		
	}
	
	public void generateMaze(Grid grid) {
		helper(maze.get(0).get(0),grid,null);
		System.out.print("End: "+end.x+","+end.y);
		grid.addStartEnd(origin,end);
		
	}
	
	private void helper(Cell current, Grid grid, Cell parent) {
		if (current.y == this.y-1) end = current;
		if (current.visited || current == null) return;

		Random rand = new Random();
		current.visited = true;
		if (parent != null) grid.removeEdge(current,parent); 
		
		Cell n;
		while(!current.neighbours.isEmpty()) {
			int randNum = rand.nextInt(current.neighbours.size());

			n = current.neighbours.remove(randNum);
			
			current.paths.add(n);
			helper(n,grid, current);
			
		}
	}
	
	public void backTrackmaze(Grid grid) {
		//remove all neighbours, leave only possible paths
		//iterative w stack
		Random rand = new Random();
		origin = maze.get(0).get(0);
		Stack<Cell> stack = new Stack<Cell>();
		stack.add(origin);
		
		while (!stack.isEmpty()) {
			Cell current = stack.pop();
			Cell n;
			
			current.visited = true;
			if (current.y == this.y-1) end = current;
			
			//Take rand neighbour: if not visited, add as path and push to stack
			while (!current.neighbours.isEmpty()) {
				int randNum = rand.nextInt(current.neighbours.size());
				n = current.neighbours.remove(randNum);
				
				if (!n.visited) {
					n.visited = true;
					current.paths.add(n);
					 grid.removeEdge(current,n);
					stack.push(n);
				}
			}
		}
		
		
	}
	
}
