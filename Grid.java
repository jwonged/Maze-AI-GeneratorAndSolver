package uk.ac.cam.dsjw2.MazeAI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Grid extends JPanel {
	public static ArrayList<Edge> edges;
	private static ArrayList<Cell> path;
	private Cell start, end;
	private int rows, cols;
	private int horLen, verLen; //length of each horizontal and vertical wall
	
	public Grid(int r, int c) {
		edges = new ArrayList<Edge>();
		path = new ArrayList<Cell>();
		
		rows = r;
		cols = c;
		horLen = 30;
		verLen = 30;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		System.out.println("paint stuff");
		g.drawRect(10,10,horLen*(rows),verLen*(cols));
		
		for (Edge e : edges) {
			g.drawLine(10+e.i.row*horLen, 10+e.i.col*verLen, 10+e.j.row*horLen, 10+e.j.col*verLen);
		}
		
		g.setColor(Color.GREEN);
		if (start!=null) g.fillRect(12+end.x*horLen, 12+end.y*verLen,horLen-3,verLen-3);
		if (end != null) g.fillRect(12+start.x*horLen, 12+start.y*verLen,horLen-3,verLen-3);
		g.setColor(Color.LIGHT_GRAY);
		for (Cell c : path) {
			g.fillRect(12+c.x*horLen, 12+c.y*verLen,horLen-3,verLen-3);
		}
		
		
	}
	
	public void addStep(Cell c) {
		//color steps in a path taken through the maze
		path.add(c);
		repaint();
	}

	public void addEdge(Edge e) {
		if (edges.size() > ((rows+1)*(cols+1)*4)) {
			System.out.println("Error: Overshot edges!");
			return;
		}
		edges.add(e);
		//try {Thread.sleep(100);} catch (InterruptedException ex) {}
		repaint();
	}
	public void removeEdge(Cell a, Cell b) {
		ArrayList<Edge> toRemove = new ArrayList<Edge>();
		//a-->b
		boolean sides = false;
		Cell top=null,bottom=null;
		Cell left=null,right=null;
		
		if (a.x == b.x) {
			//same row
			sides = true;
			if (a.y > b.y) { //right has bigger y
				left = b;
				right = a;
			} else {
				left = a;
				right = b;
			}
		} else {
			//vertical / same column
			if (a.x > b.x) { //top has smaller x
				top = b;
				bottom = a;
			} else {
				top = a;
				bottom = b;
			}
		}
		
		if (sides) {
			for (Edge e : edges) {
				if ((e.i.row == right.x && e.i.col == right.y &&
					e.j.row == left.x + 1 && e.j.col == left.y + 1) ||
					(e.j.row == right.x && e.j.col == right.y &&
					e.i.row == left.x + 1 && e.i.col == left.y + 1))
						toRemove.add(e);
			}
		} else {
			for (Edge e : edges) {//top and bottom
				if ((e.i.row == bottom.x && e.i.col == bottom.y &&
					e.j.row == bottom.x && e.j.col == bottom.y+1) ||
					(e.j.row == bottom.x && e.j.col == bottom.y &&
					e.i.row == bottom.x && e.i.col == bottom.y + 1))
						toRemove.add(e);
			}
		}
		
		edges.removeAll(toRemove);
		repaint();
	}
	public void addStartEnd(Cell s, Cell e) {
		start = s;
		end = e;
		repaint();
	}
	public void reEdge(Cell a, Cell b) {
		ArrayList<Edge> toRemove = new ArrayList<Edge>();
		//a-->b
		boolean sides = false;
		Cell top=null,bottom=null;
		Cell left=null,right=null;
		if (a.y == b.y) {
			//same col - horizontal wall
			if (a.x > b.x) {
				bottom = a;
				top = b;
			} else {
				bottom = b;
				top = a;
			}
			
		} else {
			//same row - vertical wall
			sides = true;
			if (a.x > b.x) {
				right = a;
				left = b;
			} else {
				right = b;
				left = a;
			}
		}
		
		if (sides) {
			//top index = (right.x,right.y)
			//bottom index = (left.x + 1, right.y)
			
			//top index = (right.x,right.y)
			//bottom index = (left.y+1, left.x +1)
			for (Edge e : edges) {
				if ((e.i.row == right.x && e.i.col == right.y &&   //top index
					e.j.row == left.x && e.j.col == left.y) || //bottom index
					(e.i.row == left.x && e.i.col == left.y && //or edge other way round
					e.j.row == right.x && e.j.col == right.y)) {
						
						toRemove.add(e);
				}
			}
		} else {
			//left index = bottom.x, bottom.y
			//right index = bottom.y, top.x + 1
			for (Edge e : edges) {
				if ((e.i.row == bottom.y+1 && e.i.col == bottom.x+1 &&
					e.j.row == bottom.y && e.j.col == bottom.x) ||
					(e.i.row == bottom.y && e.i.col == bottom.x &&
					e.j.row == bottom.y+1 && e.j.col == bottom.x+1)) {
						
						toRemove.add(e);
				}
			}
		}
		edges.removeAll(toRemove);
		
		//try {Thread.sleep(100);} catch (InterruptedException ex) {}
		repaint();
	}
	
}
