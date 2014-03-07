package info.coremodding.coremoddinginstaller.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Scanner;

import static org.apache.commons.io.FileUtils.copyURLToFile;

/**
 * The JFrame for the installer
 */
public class CoreModdingInstallerFrame extends JFrame
{
    
    private static final JFrame frmCoreModdingInstaller = new JFrame();
    
    private static final JPanel pnlCoreModdingInstaller = new JPanel();
    private static final long   serialVersionUID        = 1L;
    
    /**
     * @param url
     *            The url to download
     * @param location
     *            Where to download the file to
     */
    static void downloadFile(String url, String location)
    {
        try
        {
            copyURLToFile(new URL(url), new File(location));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    JCheckBox[]     boxes;
    
    String          data        = "";
    
    private Scanner in;
    String          status;
    final JLabel    statusLabel = new JLabel(this.status);
    
    /**
     * The mainc onstructor
     */
    public CoreModdingInstallerFrame()
    {
        JPanel checkBoxes = new JPanel(new WrapLayout());
        JScrollPane checkedListBox1 = new JScrollPane(checkBoxes,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JButton install = new JButton("Install/Update selected mods");
        String url = "https://raw.github.com/CoreModding/CoreModdingInstaller/master/data";
        checkedListBox1.setPreferredSize(new Dimension(234, 184));
        checkedListBox1.setSize(0, 1000);
        install.setPreferredSize(new Dimension(234, 21));
        pnlCoreModdingInstaller.add(checkedListBox1);
        pnlCoreModdingInstaller.add(install, BorderLayout.CENTER);
        pnlCoreModdingInstaller.add(this.statusLabel);
        frmCoreModdingInstaller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmCoreModdingInstaller.getContentPane().add(pnlCoreModdingInstaller,
                BorderLayout.CENTER);
        frmCoreModdingInstaller.setTitle("CORE Modding Installer");
        frmCoreModdingInstaller.setSize(new Dimension(258, 275));
        frmCoreModdingInstaller.setLocationRelativeTo(null);
        frmCoreModdingInstaller.setResizable(false);
        frmCoreModdingInstaller.setVisible(true);
        this.status = "Downloading data...";
        this.statusLabel.setText(this.status);
        try
        {
            this.in = new Scanner(new URL(url).openStream());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (this.in != null) while (this.in.hasNextLine())
        {
            this.data += this.in.nextLine() + "\n" + ",";
        }
        if (!this.data.equals(""))
        {
            this.boxes = new JCheckBox[this.data.split(",").length];
        }
        if (this.data != null)
        {
            for (int i = 0; i < this.data.split(",").length; i += 3)
            {
                JCheckBox cb = new JCheckBox(this.data.split(",")[i].trim()
                        + " - " + this.data.split(",")[i + 1]
                        + new String(new char[100]).replace("\0", " ")); // Horrible
                this.boxes[i] = cb;
                checkBoxes.add(cb);
            }
        } else
        {
            checkBoxes.add(new JLabel("No mod data available"));
            install.setEnabled(false);
            this.status = "ERROR";
            this.statusLabel.setText(this.status);
        }
        install.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                CoreModdingInstallerFrame.this.status = "Installing Mods...";
                CoreModdingInstallerFrame.this.statusLabel
                        .setText(CoreModdingInstallerFrame.this.status);
                String os = System.getProperty("os.name");
                String location = null;
                if (os.toLowerCase().startsWith("windows")) location = System
                        .getenv("USERPROFILE")
                        + "/appdata/roaming/.minecraft/mods/";
                if (os.toLowerCase().startsWith("mac")) location = System
                        .getProperty("user.home")
                        + "/Library/Application Support/minecraft/mods/";
                if (location == null) try
                {
                    throw new Exception("OS not recognized!");
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                for (int i = 0; i < CoreModdingInstallerFrame.this.boxes.length; i += 3)
                {
                    if (CoreModdingInstallerFrame.this.boxes[0].isSelected())
                    {
                        downloadFile(
                                CoreModdingInstallerFrame.this.data.split(",")[i + 2],
                                location
                                        + CoreModdingInstallerFrame.this.data
                                                .split(",")[i].trim()
                                        + CoreModdingInstallerFrame.this.data
                                                .split(",")[i + 1].trim()
                                        + CoreModdingInstallerFrame.this.data
                                                .split(",")[i + 2]
                                                .substring(
                                                        CoreModdingInstallerFrame.this.data
                                                                .split(",")[2]
                                                                .length() - 5)
                                                .trim());
                    }
                    
                }
                CoreModdingInstallerFrame.this.status = "      Complete!      ";
                CoreModdingInstallerFrame.this.statusLabel
                        .setText(CoreModdingInstallerFrame.this.status);
            }
        });
        this.status = "Waiting for input...";
        this.statusLabel.setText(this.status);
    }
}
