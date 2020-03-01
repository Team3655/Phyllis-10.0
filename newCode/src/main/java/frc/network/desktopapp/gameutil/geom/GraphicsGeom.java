package frc.network.desktopapp.gameutil.geom;

import java.awt.Graphics;
import java.awt.Rectangle;

import frc.network.desktopapp.gameutil.geom.g2D.PointR2;

public class GraphicsGeom {
	Graphics g;
	PointR2 viewPoint;
	int width;
	int height;
	
	public GraphicsGeom(Graphics g) {
		this.g=g;
		width=0;
		height=0;
		viewPoint=new PointR2(0,0);
	}
	
	public GraphicsGeom(Graphics g,PointR2 viewPoint) {
		this.g=g;
		width=0;
		height=0;
		this.viewPoint=viewPoint;
	}
	
	public GraphicsGeom(Graphics g,PointR2 viewPoint,int width,int height) {
		this.g=g;
		this.width=width;
		this.height=height;
		this.viewPoint=viewPoint;
	}
	
	public void setViewPoint(PointR2 viewPoint) {
		this.viewPoint=viewPoint;
	}
	
	public void setWidth(int width) {
		this.width=width;
	}
	
	public void setHeight(int height) {
		this.height=height;
	}
	
	public void setSize(int width, int height) {
		this.width=width;
		this.height=height;
	}
	
	public void setRect(Rectangle r) {
		width=r.width;
		height=r.height;
		viewPoint=new PointR2(r.x,r.y);
	}
	
	public void setG(Graphics g) {
		this.g=g;
	}
	
	public void drawLine(Line l) {
		
	}
	
	public void drawLine(frc.network.desktopapp.gameutil.geom.g2D.LineSegR2 l) {
		g.drawLine((int) Math.floor(l.endPoint1().getX() - viewPoint.getX()), (int) Math.floor(l.endPoint1().getY() - viewPoint.getY()),(int) Math.floor(l.endPoint2().getX() - viewPoint.getX()), (int) Math.floor(l.endPoint2().getY() - viewPoint.getY()));
	}
	
	//Finish this
	private frc.network.desktopapp.gameutil.geom.g2D.LineSegR2 cropLine(Line l){
		return null;
	}
	
	
}
