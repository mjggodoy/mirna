package mirna.integration.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import mirna.integration.beans.Organism;

public class StringTokenizerTest {
	
	public static void main(String[] args) {
		String[] organismTokens = StringUtils.splitPreserveAllTokens("Human;Mouse", ";");
		List<Organism> organismList = new ArrayList<Organism>();
		for (String organismlist : organismTokens) {
			Organism organism = new Organism();
			organism.setName(organismlist);
			organismList.add(organism);
		}
		
		int i = 1;
		for (Organism organism : organismList) {
			System.out.println(i + ":");
			System.out.println(organism);
		}
		
	}

}
