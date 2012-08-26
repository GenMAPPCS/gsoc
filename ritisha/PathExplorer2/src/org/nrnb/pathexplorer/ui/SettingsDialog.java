package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.view.model.CyNetworkView;
import org.nrnb.pathexplorer.PathExplorer;

public class SettingsDialog extends JDialog{

	private static final long serialVersionUID = 8557472793959930782L;
	JLabel headingLabel, nodeBorderWidthLabel, edgeWidthLabel;
	JTextField nodeBorderWidthInput, edgeWidthInput;
	JButton setButton;
	CyNetworkView netView;
	CySwingAppAdapter adapter;
	
	public SettingsDialog(CyNetworkView netView, CySwingAppAdapter adapter)
	{
		super();
		this.netView = netView;
		this.adapter = adapter;
		headingLabel = new JLabel("Set path properties");
		nodeBorderWidthLabel = new JLabel("Node Border Width");
		edgeWidthLabel = new JLabel("Edge Width");
		nodeBorderWidthInput = new JTextField(PathExplorer.NodeBorderWidthInPathsValue.toString());
		edgeWidthInput = new JTextField(PathExplorer.EdgeWidthInPathsValue.toString());
		setButton = new JButton("Set");
		
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// take the values, set the Properties and refresh networks
				PathExplorer.nodeBorderWidthProps.setProperty
					(PathExplorer.NodeBorderWidthInPaths, nodeBorderWidthInput.getText());
				PathExplorer.NodeBorderWidthInPathsValue = Double.valueOf(nodeBorderWidthInput.getText());
				
				PathExplorer.edgeWidthProps.setProperty
					(PathExplorer.EdgeWidthInPaths, edgeWidthInput.getText());
				PathExplorer.EdgeWidthInPathsValue = Double.valueOf(edgeWidthInput.getText());
				
				dispose();
			}
		});
		
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
 
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(headingLabel)
                .addComponent(nodeBorderWidthLabel)
                .addComponent(edgeWidthLabel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(nodeBorderWidthInput)
                    .addComponent(edgeWidthInput)
                    .addComponent(setButton))
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
        	.addComponent(headingLabel)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(nodeBorderWidthLabel)
                .addComponent(nodeBorderWidthInput))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(edgeWidthLabel)
                .addComponent(edgeWidthInput))
			.addComponent(setButton)
        );
 
        setTitle("Settings");
        pack();
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
