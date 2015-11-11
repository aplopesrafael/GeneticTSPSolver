/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.tspgenetic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author william
 */
public class GraphSceneImpl extends GraphScene {
    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;
	private List<int[]> coords;
	private List<Double> tour;
	private int maxX;
	private int maxY;
	private int height;
	private int width;


    public GraphSceneImpl(int width, int height) {
    	this.width = width;
    	this.height = height;
        this.mainLayer = new LayerWidget(this);
        this.connectionLayer = new LayerWidget(this);
        this.addChild(this.mainLayer);
        this.addChild(this.connectionLayer);
        

//        this.addNode(new int[]{0,0});
//        this.addNode(new int[]{100,100});
//        Widget w1 = this.mainLayer.getChildren().get(0);
//        Widget w2 = this.mainLayer.getChildren().get(1);
//        this.connectNodes(w1, w2);
    }
    
    public void setCoords(List<int[]> coords) {
		if (coords != null) {
			this.coords = new ArrayList<int[]>();
			maxX = 0;
			maxY = 0;
			int padding = 15;
			for (int[] city : coords) {
				if (maxX < city[0])
					maxX = city[0];
				if (maxY < city[1])
					maxY = city[1];
			}
			for (int[] city : coords) {
				this.coords
						.add(new int[] {
								(int) (city[0] * (width - padding)
										/ (double) maxX + padding / 2.0),
								(int) (city[1] * (height - padding)
										/ (double) maxY + padding / 2.0) });
			}
		}
	}

    public void paintGraph () {
        
    	if (mainLayer.getChildren().size() == 0) {
			for (int[] city : coords) {
				this.addNode(city);
			}
		}
    	
    	if (tour != null) {
    		connectionLayer.removeChildren();
    		List<Widget> widgets = this.mainLayer.getChildren();
	        for (int i =0; i< tour.size()-1; i++) {
	            Widget w1 = widgets.get(tour.get(i).intValue() - 1);
	            Widget w2 = widgets.get(tour.get(i + 1).intValue() - 1);
                this.connectNodes(w1, w2);
	        }
	        this.connectNodes(widgets.get(tour.get(tour.size()-1).intValue() - 1),widgets.get(tour.get(0).intValue() - 1));
    	}
    	//this.revalidate();
    }
    
    private void connectNodes(Widget source, Widget target) {
        ConnectionWidget conn = new ConnectionWidget(this);
        //conn.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        conn.setTargetAnchor(AnchorFactory.createRectangularAnchor(target));
        conn.setSourceAnchor(AnchorFactory.createRectangularAnchor(source));
        connectionLayer.addChild(conn);
    }
    @Override
    protected Widget attachNodeWidget(Object node) {
        int[] city = (int[])node;
        CircleWidget widget = new CircleWidget(this,3,Color.BLUE);
        //widget.getActions().addAction(ActionFactory.createMoveAction());
        widget.setPreferredLocation(new Point(city[0],city[1]));
        mainLayer.addChild(widget);
        return widget;
    }

    @Override
    protected Widget attachEdgeWidget(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void attachEdgeSourceAnchor(Object e, Object n, Object n1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void attachEdgeTargetAnchor(Object e, Object n, Object n1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class CircleWidget extends Widget {
        private int r;
        private Color color;
        public CircleWidget (Scene scene, int radius, Color color) {
            super (scene);
            r = radius;
            this.color = color;
        }
        @Override
        protected Rectangle calculateClientArea () {
            return new Rectangle (- r, - r, 2 * r + 1, 2 * r + 1);
        }
        @Override
        protected void paintWidget () {
            Graphics2D g = getGraphics ();
            if (color != null) {
                g.setColor (this.color);
                g.fillOval (- r, - r, 2 * r, 2 * r);
            }else{
                g.setColor(getForeground());
                g.drawOval (- r, - r, 2 * r, 2 * r);
            }
        }
    }

	public List<Double> getTour() {
		return tour;
	}

	public void setTour(List<Double> tour) {
		this.tour = tour;
	}

	public List<int[]> getCoords() {
		return coords;
	}
    

    
}