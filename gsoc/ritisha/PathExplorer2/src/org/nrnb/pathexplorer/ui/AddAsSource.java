package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.nrnb.pathexplorer.logic.FindAllPaths;


public class AddAsSource implements CyNodeViewContextMenuFactory{
	
	CySwingAppAdapter adapter;
	VisualMappingManager visualMappingManager;
	VisualStyle originalStyle;
	
	public AddAsSource(CySwingAppAdapter adapter){
		this.adapter = adapter;
		this.visualMappingManager = adapter.getVisualMappingManager();
		this.originalStyle = visualMappingManager.getDefaultVisualStyle();
		
	}
	public CyMenuItem createMenuItem(final CyNetworkView netView, final View<CyNode> nodeView)
	{
		
		JMenuItem menuItem = new JMenuItem("Add as Source");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				if(!visualMappingManager.getDefaultVisualStyle().equals(originalStyle))
				{
					originalStyle.apply(netView);
				}
				FindAllPaths pathsFinder = new FindAllPaths(netView , nodeView.getModel(), adapter);
				pathsFinder.allPathsMethod();
				//SteadyFlowImplementer mySteadyFlow = new SteadyFlowImplementer(allPaths, netView);
				//mySteadyFlow.implementSteadyFlow(adapter);	
			}
		});
		float gravity = 1.0f;
		CyMenuItem addAsSource = new CyMenuItem(menuItem, gravity);
		return addAsSource;
	}

}
