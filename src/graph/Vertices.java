package graph;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.util.*;

public class Vertices extends JPanel implements ActionListener, MouseListener {
	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	HashMap<String, ArrayList<String>> parentChildMap = new HashMap<String, ArrayList<String>>();

	private final int LINE_CURVE_FACTOR = 135;
	private final Color VERTEX_COLOR = Color.DARK_GRAY;
	private final Color LINE_COLOR = Color.LIGHT_GRAY;
	private final Color FONT_COLOR = Color.DARK_GRAY;
	Timer timer;
	public double rootNodeY = 900;
	private Vertex firstCircle;
	private boolean LINE_ANIMATION = true;

	public void enableLineAnimation(boolean enable) {
		LINE_ANIMATION = enable;
	}

	public Vertices() {
		super();
		// TODO Auto-generated constructor stub
		timer = new Timer(20, this);
		timer.setInitialDelay(190);
		timer.start();
		this.addMouseListener(this);
	}

	public Vertices(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Vertices(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Vertices(String title) {
		super();
		firstCircle = new Vertex(100.0, rootNodeY, 5.0, title, null);
		add(firstCircle);
		parentChildMap.put(" ", new ArrayList<String>());
		timer = new Timer(20, this);
		timer.setInitialDelay(190);
		timer.start();

		this.addMouseListener(this);

	}

	public void add(Vertex circle) {
		vertices.add(circle);
	}

	public void remove(ArrayList<Vertex> childCircleList) {
		vertices.removeAll(childCircleList);
		childCircleList.clear();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (Vertex circle : vertices) {
			step(g, circle);
		}
	}

	private void step(Graphics g, Vertex c) {
		Graphics2D g2 = (Graphics2D) g;

		// Create the circle
		Ellipse2D circle = new Ellipse2D.Double();
		double lineCurrX = c.getLineCurrX();
		double lineCurrY = c.getLineCurrY();
		if (lineCurrX< c.getCenterX() &&LINE_ANIMATION) {
			double yCurve = new Double(c.getParentY()).intValue() + 5;
			CubicCurve2D cc = new CubicCurve2D.Double();
			cc.setCurve(new Double(c.getParentX()).intValue(),
					new Double(c.getParentY()).intValue(),
					new Double(c.getParentX()).intValue() + LINE_CURVE_FACTOR,
					yCurve, new Double(lineCurrX).intValue() - LINE_CURVE_FACTOR,
					c.lineCurrY, new Double(lineCurrX).intValue(),
					new Double(c.lineCurrY).intValue());
			g2.setColor(LINE_COLOR);
			g2.draw(cc);

		} else {
			double yCurve = new Double(c.getParentY()).intValue() + 5;
			CubicCurve2D cc = new CubicCurve2D.Double();
			cc.setCurve(new Double(c.getParentX()).intValue(),
					new Double(c.getParentY()).intValue(),
					new Double(c.getParentX()).intValue() + LINE_CURVE_FACTOR,
					yCurve, new Double(c.getCenterX()).intValue()
							- LINE_CURVE_FACTOR, c.getCenterY(),
					new Double(c.getCenterX()).intValue(),
					new Double(c.getCenterY()).intValue());
			g2.setColor(LINE_COLOR);
			g2.draw(cc);

			if (c.currXPos < c.getCenterX() + c.getRadius()) {

				circle.setFrameFromCenter(c.getCenterX(), c.getCenterY(),
						c.currXPos + c.getRadius(), c.currYPos + c.getRadius());
				c.currXPos++;
				c.currYPos++;
			} else {

				circle.setFrameFromCenter(c.getCenterX(), c.getCenterY(),
						c.currXPos + c.getRadius(), c.currYPos + c.getRadius());
			}

		}

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.TRUETYPE_FONT, 25);
		g2.setFont(font);
		g.setColor(FONT_COLOR);
		g.drawString(c.getTitle(), new Double(c.getCenterX() - c.getRadius()
				- 30).intValue(), new Double(c.getCenterY()).intValue() - 20);

		g2.setPaint(VERTEX_COLOR);
		if (parentChildMap.get(c.getTitle()) == null) {
			g2.setPaint(LINE_COLOR);
		}
		g2.fill(circle);

		g2.draw(circle);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p = e.getPoint();

		for (Vertex circle : vertices) {
			if (circle.clickedOnVertex(p)) {
				if (circle.vertices.size() > 0 ) {
					remove(circle.getAllChildList());
					if(!circle.isCollapsed){
					circle.isCollapsed = true;
					mouseClicked(e); 
					}
					break;
				} else {
					circle.isCollapsed = false;
					for(Vertex cc:circle.getSiblings()){
						System.out.println("Sibling"+cc.getTitle());
					}
					remove(circle.getSiblings());
				
					System.out.println("Clicked");
					ArrayList<String> itemsList = (ArrayList<String>) parentChildMap
							.get(circle.getTitle());
					double xPoint = circle.getCenterX() + 200;
					double yPoint = 0;

					for (int i = 0; itemsList != null && i < itemsList.size(); i++) {

						if (circle.vertices.size() == 0 && itemsList.size() > 2) {
							yPoint = circle.getCenterY();
						} else if (circle.vertices.size() % 2 != 0) {
							yPoint = circle.getCenterY()
									- 120
									* roundZeroValue(
											(circle.vertices.size() / 2), 1);
						} else {
							yPoint = circle.getCenterY()
									+ 120
									* roundZeroValue(circle.vertices.size() / 2,
											1);
						}
						String title = itemsList.get(i);
						System.out.println(circle.vertices.size());
						Vertex c = new Vertex(xPoint, yPoint,
								circle.getRadius(), title, circle);

						add(c);

						circle.vertices.add(c);
					}

					break;
				}
			}
			repaint();
		}
	}

	private int roundZeroValue(int inVal, int outVal) {
		if (inVal == 0)
			return outVal;
		return inVal;
	}

	int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}


	public void mouseClickedOld(MouseEvent e) {
	}

	public void addChild(String parent, String child) {

		ArrayList<String> list = (ArrayList<String>) parentChildMap.get(parent);
		if (list == null) {
			list = new ArrayList<String>();
			list.add(child);
			parentChildMap.put(parent, list);
		} else {
			list.add(child);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
