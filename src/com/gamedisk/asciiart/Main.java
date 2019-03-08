package com.gamedisk.asciiart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {
	
	public static JProgressBar pb;	
	public static String path = "";
	public static String outputPath = "";
	public static BufferedImage image = null;
	int imageWidth, imageHeight;
	public static Dimension screenSize = new Dimension();
	
	Main(){
		pb = new JProgressBar();
		pb.setValue(0);
		pb.setMaximum(140500);
		pb.setStringPainted(true);
		pb.setForeground(new Color(0, 255, 0));
		this.add(pb);
		this.setSize(450, 75);
		this.setResizable(false);
		this.setLocation((screenSize.width / 2) - (this.getWidth() / 2), (screenSize.height / 2) - (this.getHeight() / 2));
		this.setUndecorated(true);
		
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		pb.setMaximum(imageWidth * imageHeight);
	}
	
	public static void main(String[] args) {
		//Setting standart LookAndFeel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
								
		Object[] options = {"OK"};
		
		JOptionPane.showOptionDialog(null, "Hello!\nThis is an Ascii-Art creator.\nSelect yout image, click \"Open\" and then select the output-text-file.\nit is very easy!\nTry it!", "Welcome", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images (.jpg, .png, .gif, .bmp, .jpeg)", "jpg", "png", "gif", "bmp", "jpeg");
		chooser.addChoosableFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		
		int returnValue = chooser.showOpenDialog(null);
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getPath();
		} else{
			JOptionPane.showMessageDialog(null, "Vorgang vom Benutzer abgebrochen. Die Anwendung wird beendet.", "Abgebrochen", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		chooser.removeChoosableFileFilter(filter);
		
		filter = new FileNameExtensionFilter("Text File (.txt)", "txt");
		
		chooser.addChoosableFileFilter(filter);
		
		returnValue = chooser.showSaveDialog(null);
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			outputPath = chooser.getSelectedFile().getPath();
		} else {
			JOptionPane.showMessageDialog(null, "Vorgang vom Benutzer abgebrochen. Die Anwendung wird beendet.", "Abgebrochen", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Main main = new Main();
		main.setVisible(true);
		main.task();
	}
	
	public void task() {
		String[][] chars;
		StringBuilder sb = new StringBuilder();
		String result;
		int counter = 0;
				
		chars = new String[imageWidth][imageHeight];
		
		try{
			for(int i = 0; i < imageHeight; i++) {
				for(int j = 0; j < imageWidth; j++) {
					Color color = new Color(image.getRGB(j, i));
					int average = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
					System.out.println(average);
					image.setRGB(j, i, new Color(average, average, average).getRGB());
					System.out.println(color.getRed());
					if(color.getRed() > 230) {
						chars[j][i] = "@";
					}else if(color.getRed() > 205) {
						chars[j][i] = "%";
					}else if(color.getRed() > 180) {
						chars[j][i] = "#";
					}else if(color.getRed() > 155) {
						chars[j][i] = "*";
					}else if(color.getRed() > 130) {
						chars[j][i] = "+";
					}else if(color.getRed() > 105) {
						chars[j][i] = "=";
					}else if(color.getRed() > 80) {
						chars[j][i] = "-";
					}else if(color.getRed() > 55) {
						chars[j][i] = ":";
					}else if(color.getRed() > 30) {
						chars[j][i] = ".";
					}else {
						chars[j][i] = "0";
					}
					
					counter++;
					pb.setValue(counter);
				}
				
				System.out.println(true);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < imageHeight; i++) {
			for(int j = 0; j < imageWidth; j++) {
				sb.append(chars[j][i]);
			}
			sb.append("\n");
		}
		
		result = sb.toString();
		 
		 try {
			 Path path2 = Paths.get(outputPath);
			 Files.write(path2, result.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 System.out.println(imageWidth + " " + imageHeight + " " + counter);
		 
		 JOptionPane.showMessageDialog(null, "Fertig! Das Bild wurde konvertiert!", "Fertig!", JOptionPane.INFORMATION_MESSAGE);
		 System.exit(0);
	}
		
}
