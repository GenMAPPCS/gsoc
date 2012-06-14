package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenuItem;

import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.nrnb.pathexplorer.flow.SteadyFlowImplementer;
import org.nrnb.pathexplorer.logic.FindAllPaths;


public class AddAsSource implements CyNodeViewContextMenuFactory{
	public CyMenuItem createMenuItem(final CyNetworkView netView, final View<CyNode> nodeView)
	{
		JMenuItem menuItem = new JMenuItem("Add as Source");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				FindAllPaths pathsFinder = new FindAllPaths(netView.getModel(), nodeView.getModel());
				List<LinkedList<CyNode>> allPaths;
				allPaths = pathsFinder.allPathsMethod();
				SteadyFlowImplementer mySteadyFlow = new SteadyFlowImplementer(allPaths, netView);
				mySteadyFlow.implementSteadyFlow();	
			}
		});
		float gravity = 1.0f;
		CyMenuItem addAsSource = new CyMenuItem(menuItem, gravity);
		return addAsSource;
	}

}
