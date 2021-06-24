package com.GUI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class NewEntryPanel extends JPanel {
	private JTextField videoURL_TField;
	
	private DefaultTableModel URLTableModel;
	private JPanel VideoPanel;
	private JPanel PlaylistPanel;
	private JPanel ChannelPanel;
	
	public JTextField playlistURL_TField;
	public JTextField ChannelURL_TField;
	public JComboBox comboBox;
	public JTable URLTable;
	/**
	 * Create the panel.
	 */
	public NewEntryPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel leftPanel = new JPanel();
		add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch((String)comboBox.getSelectedItem()) {
				case "Videos":
					VideoPanel.setPreferredSize(new Dimension(300, 300));
					PlaylistPanel.setPreferredSize(new Dimension(0, 0));
					ChannelPanel.setPreferredSize(new Dimension(0, 0));
					
					VideoPanel.setVisible(true);
					PlaylistPanel.setVisible(false);
					ChannelPanel.setVisible(false);
					break;
				case "Playlist":
					VideoPanel.setPreferredSize(new Dimension(0, 0));
					PlaylistPanel.setPreferredSize(new Dimension(300, 300));
					ChannelPanel.setPreferredSize(new Dimension(0, 0));
					
					VideoPanel.setVisible(false);
					PlaylistPanel.setVisible(true);
					ChannelPanel.setVisible(false);
					break;
				case "Channel":
					VideoPanel.setPreferredSize(new Dimension(0, 0));
					PlaylistPanel.setPreferredSize(new Dimension(0, 0));
					ChannelPanel.setPreferredSize(new Dimension(300, 300));
					
					VideoPanel.setVisible(false);
					PlaylistPanel.setVisible(false);
					ChannelPanel.setVisible(true);
					break;
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Videos", "Playlist", "Channel"}));
		leftPanel.add(comboBox);
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		
		VideoPanel = new JPanel();
		VideoPanel.setPreferredSize(new Dimension(300, 300));
		centerPanel.add(VideoPanel);
		initVideoPanel();
		
		
		PlaylistPanel = new JPanel();
		PlaylistPanel.setVisible(false);
		PlaylistPanel.setPreferredSize(new Dimension(0, 0));
		centerPanel.add(PlaylistPanel);
		initPlaylistPanel();
		
		ChannelPanel = new JPanel();
		ChannelPanel.setVisible(false);
		ChannelPanel.setPreferredSize(new Dimension(0, 0));
		centerPanel.add(ChannelPanel);
		initChannelPanel();
	}	
	
	public void initChannelPanel() {
		JLabel ChannelURl_lbl = new JLabel("channel URL:");
		ChannelPanel.add(ChannelURl_lbl);
		
		ChannelURL_TField = new JTextField();
		ChannelPanel.add(ChannelURL_TField);
		ChannelURL_TField.setColumns(10);
	}
	
	public void initPlaylistPanel() {
		JLabel playlistURL_lbl = new JLabel("playlist URL:");
		PlaylistPanel.add(playlistURL_lbl);
		
		playlistURL_TField = new JTextField();
		PlaylistPanel.add(playlistURL_TField);
		playlistURL_TField.setColumns(10);
	}
	
	public void initVideoPanel() {
		JLabel videoURL_lbl = new JLabel("video URL:");
		VideoPanel.add(videoURL_lbl);
		
		videoURL_TField = new JTextField();
		VideoPanel.add(videoURL_TField);
		videoURL_TField.setColumns(10);
		
		JButton addVideoURL_btn = new JButton("add");
		addVideoURL_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLTableModel.insertRow(0, new Object[] {videoURL_TField.getText(), null});
				videoURL_TField.setText("");
				videoURL_TField.requestFocus();
			}
		});
		VideoPanel.add(addVideoURL_btn);
		
		URLTableModel = new DefaultTableModel(new Object[][] {},
		new String[] {
				"URL's", "delete"
		});
		
		URLTable = new JTable();
		URLTable.setModel(URLTableModel);
		URLTable.getColumnModel().getColumn(0).setPreferredWidth(185);
		URLTable.getColumnModel().getColumn(1).setPreferredWidth(15);

		Action deleteTableArrReadingRow = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	int modelRow = Integer.valueOf( e.getActionCommand() );
		    	URLTableModel.removeRow(modelRow);
		    }
		};
		
		ButtonColumnAll buttonColumnTable_panel1 = new ButtonColumnAll(URLTable, deleteTableArrReadingRow, 1);
		
		URLTable.setPreferredSize(new Dimension(200, 200));
		VideoPanel.add(URLTable);
	}
}
