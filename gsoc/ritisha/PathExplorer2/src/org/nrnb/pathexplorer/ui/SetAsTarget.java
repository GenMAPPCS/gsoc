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
import org.nrnb.pathexplorer.logic.FindAllPaths;

public class SetAsTarget implements CyNodeViewContextMenuFactory{
	CySwingAppAdapter adapter;
	
	public SetAsTarget(CySwingAppAdapter adapter){
		this.adapter = adapter;		
	}
	public CyMenuItem createMenuItem(final CyNetworkView netView, final View<CyNode> nodeView)
	{
		
		JMenuItem menuItem = new JMenuItem("Set As Target");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				FindAllPaths pathsFinder = new FindAllPaths(netView , nodeView.getModel(), adapter);
				System.out.println("In SetAsTarget, calling allPathsMethos");
				pathsFinder.allPathsMethod_target();
			}
		});
		float gravity = 1.0f;
		CyMenuItem setAsTarget = new CyMenuItem(menuItem, gravity);
		return setAsTarget;
	}


}
