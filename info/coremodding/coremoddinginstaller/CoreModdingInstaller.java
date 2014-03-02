package info.coremodding.coremoddinginstaller;

import info.coremodding.coremoddinginstaller.frame.CoreModdingInstallerFrame;

/**
 * The main class
 */
public class CoreModdingInstaller {

    /**
     * The main entry point
     *
     * @param args The command line arguments for the program
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name",
                "CORE Modding Installer");
        System.setProperty(
                "com.apple.mrj.application.apple.menu.about.version", "v1.0");
        new CoreModdingInstallerFrame();
    }
}
