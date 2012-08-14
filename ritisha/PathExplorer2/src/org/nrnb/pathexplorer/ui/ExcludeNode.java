package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;

public class ExcludeNode implements CyNodeViewContextMenuFactory{

	CySwingAppAdapter adapter;
	
	public ExcludeNode(CySwingAppAdapter adapter){
		this.adapter = adapter;		
	}
	
	public CyMenuItem createMenuItem(final CyNetworkView netView, final View<CyNode> nodeView)
	{
		JMenuItem menuItem = new JMenuItem("Exclude Node");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				CyNetwork net;
				CyNode node;
				net = netView.getModel();
				node = nodeView.getModel();
				CyTable tempTable = net.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
				CyRow row = tempTable.getRow(node.getSUID());
				row.set("inclusionFactor", false);
			}
		});
		float gravity = 1.0f;
		CyMenuItem excludeNode = new CyMenuItem(menuItem, gravity);
		return excludeNode;
	}

	

}
