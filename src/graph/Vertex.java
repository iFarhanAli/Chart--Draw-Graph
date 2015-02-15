package graph;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Vertex extends JPanel  {

	// Data members for Circle center and radius
	private double centerX, centerY;
	public double currXPos,currYPos,angle,childCount,lineCurrX,lineCurrY;
	private double radius;
	private  Timer timer;
	private String title;
	public boolean  isCollapsed = true;
	public Vertex parent;
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	ArrayList<Vertex> vertices = new ArrayList<Vertex>();

	// No-argument constructor
	Vertex() {
	    centerX = 200;
	    centerY = 200;
	    radius = 10;
	    currXPos = centerX;
	    currYPos = centerY;
	    
	}

	// Full-argument constructor
	Vertex( double x, double y, double r, String title,Vertex parent) {
	    centerX = x;
	    centerY = y;
	    radius = r;
	    currXPos = centerX;
	    currYPos = centerY;
	    if(parent != null){
	    lineCurrX = parent.getCenterX();
	    lineCurrY = parent.getCenterY();
	    }
	    else{
	    	lineCurrX = getCenterX();
	    	lineCurrY = getCenterY();
	    }
	    this.title =title;
	    this.parent = parent;
	}

	public double getParentX(){
		if(parent == null){
			return this.getCenterX();
		}
		return parent.getCenterX();
	}
	
	public double getParentY(){
		if(parent == null){
			return this.getCenterY();
		}
		return parent.getCenterY();
	}
	public double getCurrX()
	{
		double val =currXPos++;
		return val;
	}
	
	public double getCurrY()
	{
		double val =currYPos++;
		return val;
	}
	
	public double getLineCurrX(){
		double val  = lineCurrX;
		lineCurrX += 20;
		return val;
	}
	
	public double getLineCurrY(){
		double val = lineCurrY;
		if (lineCurrY < getCenterY()) {
			lineCurrY -= 10;
		} else {
			lineCurrY += 10;
		}
		return val;
	}

	public void setCenterX(double x){this.centerX = x;}
	public void setCenterY(double y){this.centerY = y;}
	public void setRadius(double r){radius = r;}
	public double getCenterX(){return centerX;}
	public double getCenterY(){return centerY;}
	public double getRadius(){return radius;}
	public ArrayList getAllChildList(){
		childCount = 0;
		return clearList(vertices);
	}
	
	public ArrayList<Vertex> clearList(ArrayList<Vertex> list){
		ArrayList<Vertex> tempList = new ArrayList<Vertex>();
		for (Vertex circle: list) {
			tempList.addAll(clearList(circle.vertices));
		}
		list.addAll(tempList);
		return list;
	}
	
	public void updateExpansionY(Vertex c){
		
		c.currXPos = c.centerX;
		c.currYPos = c.centerY;
		//c.expandY = 0;

		for (Vertex circle: c.vertices) {
			updateExpansionY(circle);
		}

	}
	
	public ArrayList<Vertex> getSiblings(){
		ArrayList<Vertex> tempList = new ArrayList<Vertex>();
		if(parent != null){
		for (Vertex circle: parent.vertices) {
			if(!this.equals(circle )){
				System.out.println("Cirlcle11: "+circle.getTitle());
			tempList.addAll(circle.getAllChildList());
			}
			else{
				System.out.println("Cirlcle: "+circle.getTitle());
			}
		}
		}
		return tempList;
	}
public boolean clickedOnVertex(Point p){
	
	 if (  (Math.pow((p.getX() - centerX), 2)) + (Math.pow((p.getY() - centerY), 2)) < (Math.pow((radius+10), 2))){
           return  true;
     }
	 return false;
}
	}// end class Vertex
