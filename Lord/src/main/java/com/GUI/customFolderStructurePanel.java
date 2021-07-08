package com.GUI;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class customFolderStructurePanel extends JPanel {
	Random seed = new Random();
	/**
	 * Create the panel.
	 */
	public customFolderStructurePanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel TreePanel = new JPanel();
		add(TreePanel, BorderLayout.WEST);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("colors");
						node_1.add(new DefaultMutableTreeNode("blue"));
						node_1.add(new DefaultMutableTreeNode("violet"));
						node_1.add(new DefaultMutableTreeNode("red"));
						node_1.add(new DefaultMutableTreeNode("yellow"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("sports");
						node_1.add(new DefaultMutableTreeNode("basketball"));
						node_1.add(new DefaultMutableTreeNode("soccer"));
						node_1.add(new DefaultMutableTreeNode("football"));
						node_1.add(new DefaultMutableTreeNode("hockey"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("food");
						node_1.add(new DefaultMutableTreeNode("hot dogs"));
						node_1.add(new DefaultMutableTreeNode("pizza"));
						node_1.add(new DefaultMutableTreeNode("ravioli"));
						node_1.add(new DefaultMutableTreeNode("bananas"));
					add(node_1);
				}
			}
		));
		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
		    @Override
		    public void valueChanged(TreeSelectionEvent e) {
		        System.out.println(e.getPath().toString());
		    }
		});
		TreePanel.add(tree);
		
		JPanel controlPanel = new JPanel();
		add(controlPanel, BorderLayout.SOUTH);
		
		JButton addFile_btn = new JButton("add File");
		addFile_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		controlPanel.add(addFile_btn);
		
	}
}
