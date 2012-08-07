package org.nrnb.pathexplorer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
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
import org.nrnb.pathexplorer.view.MyNetViewTaskFactory;
import org.cytoscape.model.events.NetworkAddedEvent;

public class PathExplorer extends AbstractCySwingApp {
	
	CyTableFactory myTableFactory;
	CyNetworkManager myNetManager;
	CyNetworkTableManager myNetTableManager;
	
	public PathExplorer(CySwingAppAdapter adapter)
	{
		super(adapter);
	  	final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();
		CyRow row;
		//Long myNodeTableSUID;
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
	  	CyTable tempTable;
	  	List<CyNode> allNodes = new ArrayList<CyNode>();
		//Add inclusionFactor attribute for all node
	  	
	  	myTableFactory = adapter.getCyTableFactory();
	  	myNetManager = adapter.getCyNetworkManager();
	  	
	  	//for all existing networks in the system
	  	allNets = myNetManager.getNetworkSet();
	  	for(CyNetwork currNet : allNets)
	  	{
	  		tempTable = myTableFactory.createTable("IFTable", "Node", CyNode.class, false, true);
	  		tempTable.createColumn("inclusionFactor", boolean.class, true, true);
	  		myNetTableManager.setTable(currNet, CyNode.class, "IFTable", tempTable);
	  		
	  		allNodes = currNet.getNodeList();
	  		for(CyNode currNode : allNodes)
	  		{
	  			row = tempTable.getRow(currNode);
		  		row.set("inclusionFactor", true);
	  		}
	  	}
	  	
	  	//for newly added networks
	  	MyNetAddedListener addNodeTableToNet = new MyNetAddedListener(myTableFactory);
	  	//addNodeTableToNet.handleEvent(new NetworkAddedEvent(myNetManager, CyNetwork net));
	  	
	  	
	  	/*CyTable myNodeTable = myTableFactory.createTable("nodeTable", "Node", CyNode.class, false, true);
	  	myNodeTable.createColumn("inclusionFactor", boolean.class, true, true);
	  	myNodeTableSUID = myNodeTable.getSUID();
	  	
	  	
	  	myAppManager = adapter.getCyApplicationManager();
	  	currNet = myAppManager.getCurrentNetwork();
	  	allNodes = currNet.getNodeList();
	  	
	  	for(CyNode currNode : allNodes)
	  	{
	  		row = myNodeTable.getRow(currNode);
	  		row.set("inclusionFactor", true);
	  	}
	  	*/
	  	//Add as Source in context menu of Node
	  	
	  	registrar.registerService(new AddAsSource(adapter),
	                                  CyNodeViewContextMenuFactory.class,
	                                  new Properties());
	  	
	  	//Exclude nodes with.. in context menu of network
	  	Properties excludeNodesProps = new Properties();
	  	excludeNodesProps.setProperty("ENABLE_FOR", "networkAndView");
	  	excludeNodesProps.setProperty("PREFERRED_ACTION", "NEW");
	  	excludeNodesProps.setProperty("PREFERRED_MENU", "NETWORK_EDIT_MENU");
	  	excludeNodesProps.setProperty("ACCELERATOR", "cmd x");
	  	excludeNodesProps.setProperty("MENU_GRAVITY", "0.1f");
	  	excludeNodesProps.setProperty("TITLE", "Exclude Nodes With..");
	  	
	  	registrar.registerService(new MyNetViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, excludeNodesProps);
	}
	

}
