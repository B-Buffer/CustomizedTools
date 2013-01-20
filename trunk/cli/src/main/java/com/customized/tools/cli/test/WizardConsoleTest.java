package com.customized.tools.cli.test;

import com.customized.tools.cli.WizardConsole;

/**
 * Demo for use WizardConsole to update POJO entity attributes
 * 
 * @author kylin
 *
 */
public class WizardConsoleTest {

	public static void main(String[] args) {

		Entity entity = new Entity("100", "Kylin Soong", "Beijing");
		TestWizard testWizard = new TestWizard(entity);
		WizardConsole console = new WizardConsole();
		
		console.println("Previous entity:\n" + entity);
		
		if(console.readFromCli("WizardConsoleTest")) {
			TestWizard finalWizard = (TestWizard) console.popWizard(testWizard);
			console.pauseln("Final entity:\n" + finalWizard.getEntity());
		}
		
		console.prompt("DONE");
	}

}
