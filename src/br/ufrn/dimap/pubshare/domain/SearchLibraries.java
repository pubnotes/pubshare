/**
 *    This file is part of PubShare.
 *
 *    PubShare is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    PubShare is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with PubShare.  If not, see <http://www.gnu.org/licenses/>.
 */

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

	public static final int SEARCH_AUTHOR = 4;
	public static final int SEARCH_TITLE = 5;
	
	
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
