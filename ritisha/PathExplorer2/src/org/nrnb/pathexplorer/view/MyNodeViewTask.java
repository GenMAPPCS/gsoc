package org.nrnb.pathexplorer.view;

import java.awt.Color;
import java.awt.Paint;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class MyNodeViewTask extends AbstractTask{
	private View<CyNode> nodeView;
	private CyNetworkView netView;
	//private CySwingAppAdapter adapter;
	private VisualMappingManager visualMappingManager;
	
	public MyNodeViewTask(View<CyNode> nodeView, CyNetworkView netView, VisualMappingManager visualMappingManager) {
		this.nodeView = nodeView;
		this.netView = netView;
		//this.adapter = adapter;
		this.visualMappingManager = visualMappingManager;
	}

	public void run(TaskMonitor tm) throws Exception {
		
		// Double node border size
		double newNodeBorderSize =  nodeView.getVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH)*2;
		final Color newColor =  new Color((float)0.5, (float).2, (float).1);	
		
		nodeView.setVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH, newNodeBorderSize);
		nodeView.setVisualProperty(BasicVisualLexicon.NODE_PAINT, (Paint)newColor);
		
		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH,
				newNodeBorderSize);
		nodeView.setLockedValue(BasicVisualLexicon.NODE_PAINT,
				(Paint)newColor);
		
		//VisualMappingManager visualMappingManager = adapter.getVisualMappingManager();
		VisualStyle style = visualMappingManager.getDefaultVisualStyle();
		style.apply(netView);
		netView.updateView();
	} 
	

}
