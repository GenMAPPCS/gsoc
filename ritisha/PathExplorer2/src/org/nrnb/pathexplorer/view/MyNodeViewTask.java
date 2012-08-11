package org.nrnb.pathexplorer.view;

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
	private double nodeBorderSizeBound;
	private VisualMappingManager visualMappingManager;
	
	public MyNodeViewTask(View<CyNode> nodeView, CyNetworkView netView, VisualMappingManager visualMappingManager) {
		this.nodeView = nodeView;
		this.netView = netView;
		//this.adapter = adapter;
		this.visualMappingManager = visualMappingManager;
		this.nodeBorderSizeBound = nodeView.getVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH)*20;
	}

	public void run(TaskMonitor tm) throws Exception {
		
		// Double node border size
		double newNodeBorderSize =  nodeView.getVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH)*2;
		
		if(newNodeBorderSize < nodeBorderSizeBound)
		{
			nodeView.setVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH, newNodeBorderSize);
			
			nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH,
					newNodeBorderSize);
			
			VisualStyle style = visualMappingManager.getDefaultVisualStyle();
			style.apply(netView);
			netView.updateView();
		}
	} 
}
