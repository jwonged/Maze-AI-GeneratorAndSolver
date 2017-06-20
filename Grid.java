package uk.ac.cam.dsjw2.MazeAI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

public class Grid extends JPanel {
	public static ArrayList<Edge> edges;
	private HashSet<Index> hashset;
	private static ArrayList<Cell> path;
	private int rows, cols;
	private int horLen, verLen; //length of each horizontal and vertical wall
	
	public Grid(int r, int c) {
		edges = new ArrayList<Edge>();
		hashset = new HashSet<Index>();
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
		g.drawRect(10,10,40+horLen*(rows-1),40+verLen*(cols-1));
		
		for (Edge e : edges) {
			g.drawLine(10+e.i.row*horLen, 10+e.i.col*verLen, 10+e.j.row*horLen, 10+e.j.col*verLen);
		}
		g.setColor(Color.LIGHT_GRAY);
		for (Cell c : path) {
			g.fillRect(12+c.x*horLen, 12+c.y*verLen,horLen-3,verLen-3);
		}
		
	}
	
	public void addStep(Cell c) {
		//color steps in a path taken through the maze
		path.add(c);
	}

	public void addEdge(Edge e) {
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
			for (Edge e : edges) {
				if ((e.i.row == right.x && e.i.col == right.y &&
					e.j.row == left.x && e.j.col == left.y) ||
					(e.i.row == left.x && e.i.col == left.y &&
					e.j.row == right.x && e.j.col == right.y)) {
						
						toRemove.add(e);
				}
			}
		} else {
			//left index = bottom.x, bottom.y
			//right index = bottom.y, top.x + 1
			for (Edge e : edges) {
				if ((e.i.row == top.x && e.i.col == top.y &&
					e.j.row == bottom.x && e.j.col == bottom.y) ||
					(e.i.row == bottom.x && e.i.col == bottom.y &&
					e.j.row == top.x && e.j.col == top.y)) {
						
						toRemove.add(e);
				}
			}
		}
		edges.removeAll(toRemove);
		
		//try {Thread.sleep(100);} catch (InterruptedException ex) {}
		repaint();
	}
	
}
