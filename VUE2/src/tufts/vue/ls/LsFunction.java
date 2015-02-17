package tufts.vue.ls;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

import tufts.vue.DEBUG;
import tufts.vue.MapMouseEvent;
import tufts.vue.VUE;
import tufts.vue.VueTool;
import tufts.vue.VueToolbarController;
import tufts.vue.ZoomTool;

public class LsFunction {

	public static LsFunction mthis = null;
	public LsFunction()
	{
		mthis = this;
	}
	
	// -------- open dir = map dir; ------------------------
	public String getActiveMapLocation()
	{
		return VUE.getActiveMap().getSaveLocation();
	}
	
	// ----------shortkey on mapviewer; --------------------
    //+ls;140318;
    private boolean mfunkey = false;
	public boolean isFunctionKey(){ return mfunkey; }
    private VueTool[] getToolbarTopLevelTools()
    {
    	return VueToolbarController.getController().getTopLevelTools();
    }

    private void onToolKey( int idx)
    {
    	//+ls;140318;
        VueTool tool = null;
        VueTool[] tools = getToolbarTopLevelTools();
        
    	tool = tools[idx]; 
    	tool.actionPerformed(null);
    }
	public boolean onMapViewerKeyPressed(KeyEvent e)
	{
		boolean handled = false;
		
		final int keyCode = e.getKeyCode();
		
        int idx=0;
        if( keyCode == KeyEvent.VK_CONTROL )
        	mfunkey = true;
        //
        switch (keyCode) {
        //+ls; 140321; add new shortcut keys;
          case KeyEvent.VK_1:
          	onToolKey(0);	handled = true;
          	break;
          case KeyEvent.VK_2:
        	onToolKey(1);	handled = true;
          	break;
          case KeyEvent.VK_3:
        	onToolKey(2);	handled = true;
          	break;
          case KeyEvent.VK_4:
        	  onToolKey(3);	handled = true;
          	break;
          case KeyEvent.VK_5:
        	  onToolKey(4);	handled = true;
          	break;
          case KeyEvent.VK_6:
        	  onToolKey(5);	handled = true;
          	break;
          case KeyEvent.VK_7:
          case KeyEvent.VK_Q:
        	  onToolKey(6);	handled = true;
          	break;
        }
        
        return handled;
	}
	public void onMapViewerKeyReleased(KeyEvent e)
	{
		//+ls;140318; for node drag when node overlapped;
        final int keyCode = e.getKeyCode();       
        if( keyCode == KeyEvent.VK_CONTROL )
        	mfunkey = false;
	}
	
	// mousewheel zoom;
	//+ls;140321;
    public Point2D getMapXY( MouseWheelEvent e) { 
    	MapMouseEvent mapmouse = new MapMouseEvent(e);
    	return new Point2D.Float(mapmouse.getMapX(), mapmouse.getMapY());
    }
    public boolean onMapViewerMouseWheelMoved(MouseWheelEvent e)
    {
        if( (e.isControlDown()))
        {        
            boolean iswheelup = false;
            if( e.getWheelRotation() <0)
            	iswheelup = true;
            
            Point2D p = getMapXY(e);
            //if( e.getButton() == MouseEvent.BUTTON1)
        	//VueTool[] tools = getToolbarTopLevelTools();
            //ZoomTool zoomtool = (ZoomTool)tools[5];
            if ( iswheelup )
            	ZoomTool.setZoomSmaller(p);
            else
            	ZoomTool.setZoomBigger(p);
            
            e.consume(); 
            
            return true;
        }
        else
        	return false;
    }
    
}
