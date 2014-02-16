package info.coremodding.coremoddinginstaller.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class CoreModdingInstallerFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static JFrame frmCoreModdingInstaller = new JFrame();
	public static JPanel pnlCoreModdingInstaller = new JPanel();
	
	private String status;
	private JCheckBox[] boxes;
	
	private Scanner in;
	private String url = "https://raw.github.com/CoreModding/CoreModdingInstaller/master/data";
	private String data = "";
	
	private JPanel checkBoxes = new JPanel(new WrapLayout(WrapLayout.LEFT));
	private JScrollPane checkedListBox1 = new JScrollPane(checkBoxes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JButton install = new JButton("Install/Update selected mods");
	private JLabel statusLabel = new JLabel(status);
	
	public CoreModdingInstallerFrame() {

	    checkedListBox1.setPreferredSize(new Dimension(234, 184));
	    checkedListBox1.setSize(0, 1000);
	    install.setPreferredSize(new Dimension(234, 21));
	    pnlCoreModdingInstaller.add(checkedListBox1);
	    pnlCoreModdingInstaller.add(install, BorderLayout.CENTER);
	    pnlCoreModdingInstaller.add(statusLabel);
	    frmCoreModdingInstaller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frmCoreModdingInstaller.getContentPane().add(pnlCoreModdingInstaller, BorderLayout.CENTER);
	    frmCoreModdingInstaller.setTitle("CORE Modding Installer");
	    frmCoreModdingInstaller.setSize(new Dimension(258, 275));
	    frmCoreModdingInstaller.setLocationRelativeTo(null);
	    frmCoreModdingInstaller.setResizable(false);
	    frmCoreModdingInstaller.setVisible(true);
	    this.status = "Downloading data...";
	    statusLabel.setText(status);
	    try { in = new Scanner(new URL(url).openStream()); } catch(Exception e) { }
	    if(in != null) while(in.hasNextLine()) { data += in.nextLine() + "\n" + ","; }
	    if(!data.equals("")) { boxes = new JCheckBox[data.split(",").length]; }
	    if(data != null) {
	        for(int i = 0; i < data.split(",").length; i+=3) {
	        	JCheckBox cb = new JCheckBox(data.split(",")[i].trim() + " - " + data.split(",")[i + 1] + new String(new char[100]).replace("\0", " ")); // Horrible yet the only way to make it wrap properly (that I know of)
	        	boxes[i] = cb;
	            checkBoxes.add(cb);
	        }
	    } else { checkBoxes.add(new JLabel("No mod data available")); install.setEnabled(false); status = "ERROR"; statusLabel.setText(status); }
	    install.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        status = "Installing Mods...";
		        statusLabel.setText(status);
		        String os = System.getProperty("os.name");
		        String location = null;
		        if(os.toLowerCase().startsWith("windows")) location = System.getenv("USERPROFILE") + "/appdata/roaming/.minecraft/mods/";
		        if(os.toLowerCase().startsWith("mac")) location = System.getProperty("user.home") + "/Library/Application Support/minecraft/mods/";
		        if(location == null) try { throw new Exception("OS not recognized!"); } catch(Exception e) { }
		        for (int i = 0; i < boxes.length; i+=3) {
		        	if(boxes[0].isSelected()) {
		        		downloadFile(data.split(",")[i + 2], location + data.split(",")[i].trim() + data.split(",")[i + 1].trim() + data.split(",")[i + 2].substring(data.split(",")[2].length() - 5).trim());
		        	}
		        	
		        }
		        status = "      Complete!      ";
		        statusLabel.setText(status);
			}
	    });
	    this.status = "Waiting for input...";
	    statusLabel.setText(status);
	}
	
	public static void downloadFile(String URL, String location) {
		URL url;
        	URLConnection con;
        	DataInputStream dis; 
        	FileOutputStream fos; 
        	byte[] fileData;  
        	try {
	            	url = new URL(URL);
	            	con = url.openConnection();
            		dis = new DataInputStream(con.getInputStream());
            		fileData = new byte[con.getContentLength()]; 
            		for (int q = 0; q < fileData.length; q++) { 
                		fileData[q] = dis.readByte();
            	}
            	dis.close();
            	fos = new FileOutputStream(new File(location));
            	fos.write(fileData);
            	fos.close();
	        }
        	catch(Exception m) {
            	System.out.println(m);
        	}
	}
}
