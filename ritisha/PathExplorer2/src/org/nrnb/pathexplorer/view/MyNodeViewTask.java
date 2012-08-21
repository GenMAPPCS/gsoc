package org.nrnb.pathexplorer.view;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
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
	private VisualMappingManager visualMappingManager;
	private CyTable tempTable;
	private CyNetwork myNet;
	
	public MyNodeViewTask(View<CyNode> nodeView, CyNetworkView netView, VisualMappingManager visualMappingManager) {
		this.nodeView = nodeView;
		this.netView = netView;
		//this.adapter = adapter;
		this.visualMappingManager = visualMappingManager;
		this.myNet = netView.getModel();
		this.tempTable = myNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
	}

	public void run(TaskMonitor tm) throws Exception {
		
		//set inPaths to true
		CyRow row;
		row = tempTable.getRow(nodeView.getModel().getSUID());
		row.set("inPaths", true);
		
		// Double node border size
		//double newNodeBorderSize =  nodeView.getVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH) + 
		//		nodeView.getVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH)*0.5;
		double newNodeBorderSize = 12.0;	
		//if(newNodeBorderSize < 20)
		//{
			nodeView.setVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH, 10.0);//newNodeBorderSize);
			
			nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 10.0);
					//newNodeBorderSize);
			
			VisualStyle style = visualMappingManager.getDefaultVisualStyle();
			style.apply(netView);
			netView.updateView();
		//}
	}
	
	public void removeBorderMethod()
	{
		nodeView.setVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH, 0.0);
		
		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH,
				0.0);
		
		VisualStyle style = visualMappingManager.getDefaultVisualStyle();
		style.apply(netView);
		netView.updateView();
	}
}
