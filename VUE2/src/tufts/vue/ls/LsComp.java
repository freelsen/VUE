package tufts.vue.ls;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import tufts.vue.LWComponent;

public class LsComp {
	
	// +ls@140727;
    private Point2D.Float mpressloc = new Point2D.Float(0,0);
    public Point2D.Float getPressLocation(){ return mpressloc; }
    public void setPressLocation(float x, float y)
    {
    	mpressloc.x = x;
    	mpressloc.y = y;
    }

    private Point2D.Float mrelloc = new Point2D.Float(0,0);
    public Point2D.Float getRelativeLocation() { return mrelloc; }
    public void setRelativeLocation(float x, float y)
    {
    	mrelloc.x = x;
    	mrelloc.y = y;
    }   
}
