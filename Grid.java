package uk.ac.cam.dsjw2.MazeAI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Grid extends JPanel {
	public static ArrayList<Wall> walls;
	private int rows, cols;
	private int horLen, verLen; //length of each horizontal and vertical wall
	
	public Grid(int r, int c) {
		walls = new ArrayList<Wall>();
		rows = r;
		cols = c;
		horLen = 30;
		verLen = 30;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(10,10,40+horLen*rows,40+verLen*cols);
		
		for (Wall w : walls) g.drawLine(w.start_x,w.start_y,w.end_x, w.end_y);
		
		//for (int i=10; i<=1000; i+=30) g.drawLine(i,10,i,1010);
		//for (int i=10; i<=1000; i+=30) g.drawLine(10,i,1010,i);
	}

	public void addWall(Wall e) {
		
		e.start_x = 10+e.i.x*horLen ; 
		e.start_y = 10+e.i.x + e.j.x;
		e.end_x = 10+e.i.x + e.j.x;
		e.end_y = 10+e.i.x + e.j.x;
		
		walls.add(e);
		try {Thread.sleep(100);} catch (InterruptedException ex) {}
		repaint();
	}
	
	public void removeWall(Wall e) {
		walls.remove(e);
		try {Thread.sleep(100);} catch (InterruptedException ex) {}
		repaint();
	}
}
