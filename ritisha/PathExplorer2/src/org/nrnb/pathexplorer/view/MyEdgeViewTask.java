package org.nrnb.pathexplorer.view;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;


public class MyEdgeViewTask extends AbstractTask {
	private View<CyEdge> edgeView;
	private CyNetworkView netView;
	private CySwingAppAdapter adapter;

	public MyEdgeViewTask(View<CyEdge> edgeView, CyNetworkView netView, CySwingAppAdapter adapter) {
		this.edgeView = edgeView;
		this.netView = netView;
		this.adapter = adapter;
	}

	public void run(TaskMonitor tm) throws Exception {
		
		// Double edge width size
		double newEdgeWidthSize =  edgeView.getVisualProperty(BasicVisualLexicon.EDGE_WIDTH)*4;
				
		edgeView.setVisualProperty(BasicVisualLexicon.EDGE_WIDTH, newEdgeWidthSize);
		
		edgeView.setLockedValue(BasicVisualLexicon.EDGE_WIDTH,
				newEdgeWidthSize);
		
		VisualMappingManager visualMappingManager = adapter.getVisualMappingManager();
		VisualStyle style = visualMappingManager.getDefaultVisualStyle();
		style.apply(netView);
		netView.updateView();
	} 

}
