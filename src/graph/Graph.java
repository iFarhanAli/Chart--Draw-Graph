package graph;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Graph {
	private Vertices c;
	public Graph( String title) {
		super();
	     c = new Vertices(title);
	  }
	
	public void  addChild(String parent, String child){
		c.addChild(parent, child);
	}
	
	public JScrollPane getCanvas(){
	    JScrollPane scrollFrame = new JScrollPane(c);
	    c.setSize(2000, 2000);
	    c.setPreferredSize(new Dimension(2000, 2000));
	    c.setAutoscrolls(true);
	    scrollFrame.setPreferredSize(new Dimension( 800,800));
	    scrollFrame.getViewport().setViewPosition(new Point(0,500));

	    return scrollFrame;
	}
	  

	public static void main(String[] args) {
		    JFrame frame = new JFrame("Graph");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    Graph c = new Graph("Services");
		   
		    c.addChild("Services", "RR");
		    c.addChild("Services", "TBS");
		    c.addChild("Services", "TBS");
		    c.addChild("Services", "TBS");
		    c.addChild("Services", "CRM");
		    c.addChild("Services", "TPS");
		    c.addChild("TPS", "TPS1");
		    c.addChild("TPS", "TPS1");
		    c.addChild("TPS", "TPS1");
		    c.addChild("TPS", "TPS1");
		    c.addChild("TPS", "TPS1");
		    c.addChild("TBS", "TPS1");
		    c.addChild("TBS", "TPS1");


		    c.addChild("TPS1", "TPS2");
		    c.addChild("TPS1", "TPS2");
		    c.addChild("TPS1", "TPS2");
		    JScrollPane scroller = c.getCanvas();
		    frame.add(scroller); 
		    frame.setSize(800, 800);
		    frame.setLocationRelativeTo(null);
		    frame.setVisible(true);
	//	    scroller.getViewport().setViewPosition(new Point(0,500));


		  }

}
