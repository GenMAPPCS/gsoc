package org.nrnb.pathexplorer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.nrnb.pathexplorer.logic.MyNetAddedListener;
import org.nrnb.pathexplorer.ui.AddAsSource;
import org.nrnb.pathexplorer.ui.ExcludeNode;
import org.nrnb.pathexplorer.ui.SetAsTarget;
import org.nrnb.pathexplorer.view.MyNetViewTaskFactory;
import org.nrnb.pathexplorer.view.RefreshState;
import org.cytoscape.model.events.NetworkAddedListener;

public class PathExplorer extends AbstractCySwingApp {
	
	CyTableFactory myTableFactory;
	CyNetworkManager myNetManager;
	CyNetworkTableManager myNetTableManager;
	
	public PathExplorer(CySwingAppAdapter adapter)
	{
		super(adapter);
	  	final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();
		CyRow row;
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
	  	CyTable tempTable;
	  	List<CyNode> allNodes = new ArrayList<CyNode>();
		
	  	//Add inclusionFactor attribute for all node
	  	
	  	myNetManager = adapter.getCyNetworkManager();
	  	
	  	//for all existing networks in the system
	  	allNets = myNetManager.getNetworkSet();
	  	System.out.println("Got all networks, adding IF");
	  	for(CyNetwork currNet : allNets)
	  	{
	  		tempTable = currNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
	  		tempTable.createColumn("inclusionFactor", boolean.class, true, true);
	  		
	  		allNodes = currNet.getNodeList();
	  		for(CyNode currNode : allNodes)
	  		{
	  			row = tempTable.getRow(currNode.getSUID());
		  		row.set("inclusionFactor", true);
	  		}
	  	}
	  	
	  	//for newly added networks
	  	registrar.registerService(new MyNetAddedListener(), NetworkAddedListener.class, new Properties());
	  	
	  	//Add as Source in context menu of Node
	  	registrar.registerService(new AddAsSource(adapter),
	                                  CyNodeViewContextMenuFactory.class,
	                                  new Properties());
	  	System.out.println("Add as source registered");
	  	
	  	//Set as Target in Node context menu
	  	registrar.registerService(new SetAsTarget(adapter),CyNodeViewContextMenuFactory.class,
                new Properties());
	  	System.out.println("Set as Target registered");
	  	
	  	//Exclude Node in Node Context menu
	  	registrar.registerService(new ExcludeNode(adapter),CyNodeViewContextMenuFactory.class,
                new Properties());
	  	System.out.println("Exclude Node registered");
	  	
	  	//Refresh button in Network Context Menu
	  	Properties refreshProps = new Properties();
	  	refreshProps.setProperty("enableFor", "networkAndView");
	  	refreshProps.setProperty("preferredAction", "NEW");
	  	//refreshProps.setProperty("preferredMenu", "NETWORK_EDIT_MENU");
	  	refreshProps.setProperty("accelerator", "cmd x");
	  	refreshProps.setProperty("menuGravity", "0.1f");
	  	refreshProps.setProperty("title", "Refresh");
	  	
	  	registrar.registerService(new RefreshState(adapter), 
	  			NetworkViewTaskFactory.class, refreshProps);
	  	System.out.println("Refresh registered..");
	  	
	  	//Exclude nodes with.. in context menu of network
	  	Properties excludeNodesProps = new Properties();
	  	excludeNodesProps.setProperty("enableFor", "networkAndView");
	  	excludeNodesProps.setProperty("preferredAction", "NEW");
	  	//excludeNodesProps.setProperty("preferredMenu", "NETWORK_EDIT_MENU");
	  	excludeNodesProps.setProperty("accelerator", "cmd x");
	  	excludeNodesProps.setProperty("menuGravity", "0.1f");
	  	excludeNodesProps.setProperty("title", "Exclude Nodes With..");
	  	
	  	registrar.registerService(new MyNetViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, excludeNodesProps);
	  	System.out.println("Exclude nodes with registered..");
	}
	

}
