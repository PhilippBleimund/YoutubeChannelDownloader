package com.GUI;

import javax.swing.JPanel;

import com.github.kiulian.downloader.model.videos.formats.Format;

import YoutubeDownload.YoutubeConnector;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;

interface DownloadListenerInterface{
	void downloadAction();
}

abstract class DownloadListener implements DownloadListenerInterface {

	public abstract void downloadAction();
}

public class VideoElementPanel extends JPanel {

	public String title;
	public String videoId;
	public Format downloadVideoURL;
	public Format downloadAudioURl;
	public boolean Downloadable = true;
	
	public YoutubeConnector YoutubeConnect;
	
	private List<DownloadListenerInterface> listeners = new ArrayList<DownloadListenerInterface>();
	
	/**
	 * Create the panel.
	 */
	public VideoElementPanel(String title, String videoId, Format downloadVideoURL, Format downloadAudioURL, YoutubeConnector YoutubeConnect) {
		
		this.title = title;
		this.videoId = videoId;
		this.downloadVideoURL = downloadVideoURL;
		this.downloadAudioURl = downloadAudioURL;
		this.YoutubeConnect = YoutubeConnect;
		init();
	}
	
	private void init() {
		setBackground(Color.GRAY);
		setLayout(new BorderLayout(0, 0));
		
		JLabel VideoTitle_lbl = new JLabel(title);
		add(VideoTitle_lbl, BorderLayout.CENTER);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(Color.GRAY);
		add(controlPanel, BorderLayout.EAST);
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton donwload_btn = new JButton(">");
		donwload_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (DownloadListenerInterface hl : listeners)
		            hl.downloadAction();
			}
		});
		donwload_btn.setOpaque(false);
		donwload_btn.setBorderPainted(false);
		donwload_btn.setPreferredSize(new Dimension(15, 15));
		donwload_btn.setMargin(new Insets(0,0,0,0));
		controlPanel.add(donwload_btn);
		
		JButton Downloadable_btn = new JButton("x");
		Downloadable_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Downloadable  = !Downloadable;
				if(Downloadable) {
					setBackground(Color.GRAY);
					controlPanel.setBackground(Color.GRAY);
				}else {
					setBackground(Color.LIGHT_GRAY);
					controlPanel.setBackground(Color.LIGHT_GRAY);
				}
			}
		});
		Downloadable_btn.setOpaque(false);
		Downloadable_btn.setBorderPainted(false);
		Downloadable_btn.setPreferredSize(new Dimension(15, 15));
		Downloadable_btn.setMargin(new Insets(0,0,0,0));
		controlPanel.add(Downloadable_btn);
	}
	
	public void addListener(DownloadListenerInterface toAdd) {
        listeners.add(toAdd);
    }

}
