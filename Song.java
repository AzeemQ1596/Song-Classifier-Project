//Song class
public class Song {

	String songID;
	int[] songAspects = new int[6]; //creates an array for song aspect values
	boolean status = true;
	int songCat;
	double minSum=999; //sum cannot be more than 999
	
	Song(String ID) {//contructor to make set Song ID
		
		setSongID(ID);
	}
	public void setSongID(String ID) {
		
		songID = ID;
	}
	public void setSongAspects(String[] token) { //to set aspect values
		
		for(int i=0; i<6; i++) 
			songAspects[i] = Integer.parseInt(token[i+1]); //i+1 because the first token is the song ID
	}
	public void setSongStatus(boolean stat) { //to set status
		
		status = stat;
	}
	public void setSongCat(int cat) { //to set song category
		
		songCat = cat;
	}
	public void setMinSum(double m) { // //to set the minimum sum
		
		minSum = m;
	}	
	public String getSongID() {
		
		return songID;
	}
	public int getSongAspects(int index) {
		
		return songAspects[index];
	}
	
	public boolean getSongStatus() {
		
		return status;
	}
	public int getSongCat() {
		
		return songCat;
	}
	public double getMinSum() { 
		
		return minSum;
	}
}//end of song class