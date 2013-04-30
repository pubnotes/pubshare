package br.ufrn.dimap.pubshare.domain;

import java.util.ArrayList;

/**
 * Libraries to search.
 * @author itamir
 *
 */
public class SearchLibraries {

	public static final int IEEEXPLORER = 1;
	public static final int ACM = 2;
	public static final int SPRINGER = 3;
	
	
	/**
	 * Method to list libraries.
	 * @return
	 */
	public static ArrayList<String> getLibraries() {
		ArrayList<String> fontes = new ArrayList<String>();
		fontes.add("SELECT");
		fontes.add("IEEExplorer");
		fontes.add("ACM");
		fontes.add("Springer");
		return fontes;
	}
	
}
