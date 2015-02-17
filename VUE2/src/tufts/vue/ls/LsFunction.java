package tufts.vue.ls;

import tufts.vue.VUE;

public class LsFunction {

	public static LsFunction mthis = null;
	public LsFunction()
	{
		mthis = this;
	}
	
	public String getActiveMapLocation()
	{
		return VUE.getActiveMap().getSaveLocation();
	}
}
