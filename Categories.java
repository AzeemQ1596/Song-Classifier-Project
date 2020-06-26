//categories class
public class Categories {

	int catID;
	int[] catAspects = new int[6]; //creates an array for category aspect values
	
	Categories(int ID) { //constructor to set category ID
		
		setCatID(ID);
	}
	public void setCatID(int ID) { //sets category ID
		catID = ID;
	}

	public void setCatAspects(String[] token) {// sets category aspects
		
		for(int i=0; i<6; i++) 
			catAspects[i] = Integer.parseInt(token[i+1]);// i+1 because the first token is the category ID
	}
	public int getCatID() {
		return catID;
	}
	
	public int getCatAspects(int index) { 
		return catAspects[index];
	}	
}//end of categories class
