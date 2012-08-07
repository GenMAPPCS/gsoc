package org.nrnb.pathexplorer.logic;

import java.util.List;
import java.util.ArrayList;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyNode;
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
		//get the default node table and myNodeTable and list of all nodes in the network
		CyTable myNodeTable, myDefaultNodeTable;
		List<CyNode> allNodes = new ArrayList<CyNode>();
		CyRow row1, row2;
		CyTableManager myTableManager = adapter.getCyTableManager();
		myDefaultNodeTable = myNet.getDefaultNodeTable();
		myNodeTable = myTableManager.getTable(mySUID);
		allNodes = myNet.getNodeList();
		
		//based on type of selectedColumn, proceed further
		if(selectedCol.getType().equals(String.class))
		{
			//if type is string, then selectedOp can be "Equals" or "Does not equal"
			if(selectedOp.equals("Equals"))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(selectedVal.equals(row1.getRaw(selectedCol.getName())))
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}
			}
			else if(selectedOp.equals("Does not equal"))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(!selectedVal.equals(row1.getRaw(selectedCol.getName())))
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}
			}
		}
		
		else if(selectedCol.getType().equals(Boolean.class))
		{
			//if type is Boolean, the selectedOp is only "="
			if(selectedVal.equals(true))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(row1.getRaw(selectedCol.getName()).equals(true))
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}		
			}
			else if(selectedVal.equals(false))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(row1.getRaw(selectedCol.getName()).equals(false))
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
		}
		
		else if(selectedCol.getType().equals(Integer.class))
		{
			//selectedOp can be =, <, >, <=, >=, != 
			if(selectedOp.equals("="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Integer)row1.getRaw(selectedCol.getName())).compareTo((Integer)selectedVal) == 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("!="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Integer)row1.getRaw(selectedCol.getName())).compareTo((Integer)selectedVal) != 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("<"))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Integer)row1.getRaw(selectedCol.getName())).compareTo((Integer)selectedVal) < 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals(">"))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Integer)row1.getRaw(selectedCol.getName())).compareTo((Integer)selectedVal) > 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("<="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Integer)row1.getRaw(selectedCol.getName())).compareTo((Integer)selectedVal) <= 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals(">="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Integer)row1.getRaw(selectedCol.getName())).compareTo((Integer)selectedVal) >= 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
		}
		
		else if(selectedCol.getType().equals(Long.class))
		{
			//selectedOp can be =, <, >, <=, >=, != 
			if(selectedOp.equals("="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Long)row1.getRaw(selectedCol.getName())).compareTo((Long)selectedVal) == 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("!="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Long)row1.getRaw(selectedCol.getName())).compareTo((Long)selectedVal) != 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("<"))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Long)row1.getRaw(selectedCol.getName())).compareTo((Long)selectedVal) < 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals(">"))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Long)row1.getRaw(selectedCol.getName())).compareTo((Long)selectedVal) > 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("<="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Long)row1.getRaw(selectedCol.getName())).compareTo((Long)selectedVal) <= 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals(">="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Long)row1.getRaw(selectedCol.getName())).compareTo((Long)selectedVal) >= 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
		}
		
		else if(selectedCol.getType().equals(Double.class))
		{
			//selectedOp can be =, <, >, <=, >=, != 
			if(selectedOp.equals("="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Double)row1.getRaw(selectedCol.getName())).compareTo((Double)selectedVal) == 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("!="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Double)row1.getRaw(selectedCol.getName())).compareTo((Double)selectedVal) != 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("<"))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Double)row1.getRaw(selectedCol.getName())).compareTo((Double)selectedVal) < 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals(">"))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Double)row1.getRaw(selectedCol.getName())).compareTo((Double)selectedVal) > 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals("<="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Double)row1.getRaw(selectedCol.getName())).compareTo((Double)selectedVal) <= 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
			else if(selectedOp.equals(">="))
			{
				for(CyNode currNode : allNodes)
				{
					row1 = myDefaultNodeTable.getRow(currNode);
					if(((Double)row1.getRaw(selectedCol.getName())).compareTo((Double)selectedVal) >= 0)
					{
						row2 = myNodeTable.getRow(currNode);
						row2.set("inclusionFactor", false);
					}
				}	
			}
		}
	}
}
