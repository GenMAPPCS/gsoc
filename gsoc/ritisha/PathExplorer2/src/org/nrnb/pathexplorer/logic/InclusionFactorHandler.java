package org.nrnb.pathexplorer.logic;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableManager;

//this class currently handles setting of the inclusionFactor value in myNodeTable. A similar one for edges
//will be made

public class InclusionFactorHandler {
	
	private CySwingAppAdapter adapter;
	public InclusionFactorHandler(CySwingAppAdapter adapt)
	{
		this.adapter = adapt;
	}

	public void handleIF(Long mySUID, CyColumn selectedCol, String selectedOp, 
			Object selectedVal, CyNetwork myNet)
	{
		//get the default node table and myNodeTable
		CyTable myNodeTable, myDefaultNodeTable;
		CyTableManager myTableManager = adapter.getCyTableManager();
		myDefaultNodeTable = myNet.getDefaultNodeTable();
		myNodeTable = myTableManager.getTable(mySUID);
		
		//code to be written
	}
	

	
}
