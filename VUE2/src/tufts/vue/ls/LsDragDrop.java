package tufts.vue.ls;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tufts.vue.LWComponent;
import tufts.vue.LWContainer;
import tufts.vue.LWKey;
import tufts.vue.LWLink;
import tufts.vue.LWNode;
import tufts.vue.MapViewer;
import tufts.vue.Size;
import tufts.vue.VueTool;

public class LsDragDrop {
	public static  LsDragDrop mthis = null;
	public LsDragDrop( ){
		mthis = this;
	}

	// ----------drag drop ---------------------------------
	public void onMapViewerMousePressed(MapViewer view, 
			float mapX, float mapY)
	{
		//+ls;@140727;
		view.mhitcomponent = view.pickNode(mapX, mapY);
		LWComponent hitcomp = view.mhitcomponent;
		
		// TODO: this is yet another special case
		if (hitcomp != null ) {
			//System.out.println("+ls>mapivewer-pickNode:"+hitls.label);
			hitcomp.mlscomp.setPressLocation(0, 0);
			
			if( hitcomp.getParent() != null)
			{
			    Point2D.Float pmap = new Point2D.Float(mapX,mapY);
			    Point2D.Float zp2 = hitcomp.getParent().transformMapToZeroPoint(pmap);
			    //System.out.println("+ls>mapviewer.mousePressed():zp2(x,y)=("+zp2.x+","+zp2.y+")");
				Point2D ptloc = hitcomp.getLocation();
				
				hitcomp.mlscomp.setPressLocation((float) (zp2.x -ptloc.getX()),
						(float) (zp2.y -ptloc.getY()) );
			 }
		  }
	}

	public LWComponent onMapViewerMousePressedHit(LWComponent hitComponent, VueTool activeTool)
	{
		// +ls@140729;
        if( (hitComponent != null) && (activeTool.getToolName().equals("Node Tool")))
        {
        	hitComponent = null;
        }
        return hitComponent;
	}
	public boolean onNodeToolHandleSelectionRelease(MapViewer viewer, LWNode node)
	{
		boolean adnode = false;							// +ls@140730;
        LWComponent hitcomp = viewer.mhitcomponent;
        if( hitcomp != null) 
        {
        	if( hitcomp instanceof LWNode)
        	{
        		hitcomp.addChild(node);
        		adnode = true;
        	}
        }
        return adnode;
	}

    public void onSetAsChildAndLocalize(LWComponent c, LWContainer container, 
    		Point2D.Float mapPoint)
    {
    	Point2D.Float zp = container.transformMapToZeroPoint(mapPoint);
    	c.mlscomp.setRelativeLocation(zp.x, zp.y);
    }

    public void onReparentTo(Point2D.Float mapxy, final LWContainer container, 
    		Collection<LWComponent> possibleChildren)
    {
    	// +ls@140727; record new location and return;
    	for (LWComponent c : possibleChildren) {
            if (c.getParent() == container)
            {
            	Point2D.Float zp = container.transformMapToZeroPoint(mapxy);
            	Point2D.Float pl = c.mlscomp.getPressLocation();
            	c.mlscomp.setRelativeLocation( zp.x-pl.x, zp.y-pl.y);
            }
        }
    }
    
    // --- LWNote; ---
    public void layoutChildrenSingleColumn(LWNode parent, float baseX, float baseY, Size result)
    {
    	float x0 = baseX;											// +ls@140727;
        float y0 = baseY;
        float maxWidth = 0;
        boolean first = true;
        
        //float x = baseX;											// +ls@140727;
        float y = baseY;
         
        for (LWComponent c : parent.getChildren()) {
            if (c instanceof LWLink) // todo: don't allow adding of links into a manged layout node!
                continue;
            if (c.isHidden())
                continue;
            if (first)
                first = false;
            else
            { 
            	//x += mthis.ChildHorizontalGap * mthis.getScale();	// +ls@140727;
                y += parent.ChildVerticalGap * parent.getScale();
            }
            
            Point2D.Float rl = c.mlscomp.getRelativeLocation();
            double x1 = rl.x; // +ls@140727;
            double y1 = rl.y;
            if( x1 <= baseX || y1 <= baseY)		// situation when load old map; +ls@140730;
            {
            	x1=baseX;
            	y1 = y;
            	
            	c.mlscomp.setRelativeLocation((float) x1, (float) y1);
            	
            	y += c.getLocalHeight();
            }
            c.setLocation(x1, y1);
            
            double x2 = c.getLocalWidth() + x1;
            double y2 = c.getLocalHeight() + y1;
            if( x0 < x2)
            	x0 = (float) x2;
            if( y0 < y2)
            	y0 = (float) y2;
            
            if (result != null) {
                // track max width
                float w = c.getLocalBorderWidth();
                if (w > maxWidth)
                    maxWidth = w;
            }
        }

        if (result != null) {
            //result.width = maxWidth;
        	double x3 = x0 - baseX;
        	double y3 = (y0 - baseY);
        	if( result.width < x3)
        		result.width = (float) x3; // +ls@140727;
        	if( result.height < y3)
        		result.height = (float) y3;
        }
    }	
}
