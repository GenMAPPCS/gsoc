package org.nrnb.pathexplorer.view;

import org.cytoscape.app.swing.CySwingAppAdapter;
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
	private CySwingAppAdapter adapter;
	
	public MyNodeViewTask(View<CyNode> nodeView, CyNetworkView netView, CySwingAppAdapter adapter) {
		this.nodeView = nodeView;
		this.netView = netView;
		this.adapter = adapter;
		System.out.println("in node view task");
	}

	public void run(TaskMonitor tm) throws Exception {
		
		// Double node border size
		double newNodeBorderSize =  nodeView.getVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH)*2;
				
		nodeView.setVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH, newNodeBorderSize);
		
		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH,
				newNodeBorderSize);
		
		VisualMappingManager visualMappingManager = adapter.getVisualMappingManager();
		VisualStyle style = visualMappingManager.getDefaultVisualStyle();
		style.apply(netView);
		netView.updateView();
	} 
	

}
