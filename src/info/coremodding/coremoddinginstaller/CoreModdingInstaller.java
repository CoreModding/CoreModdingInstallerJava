package info.coremodding.coremoddinginstaller;

import info.coremodding.coremoddinginstaller.frame.CoreModdingInstallerFrame;

public class CoreModdingInstaller {
	
	public static void main(String[] args) {
		//Mac application settings stuff
		//public static Application application;
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "CORE Modding Installer");
		System.setProperty("com.apple.mrj.application.apple.menu.about.version", "v1.0");
		//application = Application.getApplication();
		//Image image = Toolkit.getDefaultToolkit().getImage("res/icon.png");
		//application.setDockIconImage(image);
		
		new CoreModdingInstallerFrame();
	}
	
}
