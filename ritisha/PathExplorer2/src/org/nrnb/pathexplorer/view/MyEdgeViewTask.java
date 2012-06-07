package org.nrnb.pathexplorer.view;

import org.cytoscape.model.CyEdge;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;


public class MyEdgeViewTask extends AbstractTask {
	private View<CyEdge> edgeView;
	private CyNetworkView netView;

	public MyEdgeViewTask(View<CyEdge> edgeView, CyNetworkView netView) {
		this.edgeView = edgeView;
		this.netView = netView;
	}

	public void run(TaskMonitor tm) throws Exception {
		
		// Double edge width size
		double newXSize =  edgeView.getVisualProperty(BasicVisualLexicon.EDGE_WIDTH)*2;
				
		edgeView.setVisualProperty(BasicVisualLexicon.EDGE_WIDTH, newXSize);
		
		netView.updateView();
	} 

}
