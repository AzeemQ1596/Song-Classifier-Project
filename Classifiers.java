//Assignment 2. Syed Azeem Ahmed Quadri, 20052050

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Classifiers {
	
	// The errorcheckFuntion checks if the song is a bad song and writes it into a error.txt file and sets the song status to false
	public static void errorCheck(String[] token, String line, Song song, PrintWriter file) {
		
		if(token.length < 7) { //if the length is less than 7, means it doesn't have enough aspect values
			file.println(line +" Not enough aspect values");
			song.setSongStatus(false);
		}
		
		else if(token.length == 7) { //if the length is equal to 7, the possible errors are
									// that it may have decimal aspect value or an aspect value greater than 10
			try {
				for(int i=1; i<token.length; i++) {
					if(Integer.parseInt(token[i])>10) { //if the aspect isn't an integer, it will be "caught"
						file.println(line +" Aspect value isn't in the range of 0-10"); //writes to error.txt
						song.setSongStatus(false);
						}	
					}		
				}
			catch(Exception E1) {
				file.println(line +" One of the aspect value isnt't an integer"); //writes to error.txt
				song.setSongStatus(false);
			}
		}
		else if(token.length > 7) { //if the length is more than 7, possible errors are it has more than 6 aspect values or that there 
									//is a comma in the song ID (since the the tokens are split by comma)
			try {
				Double.parseDouble(token[1]); //if there is an error in converting to double, the 2nd token isn't an aspect value and there is a comma in the ID,
				file.println(line +" Too many aspect values");//writes to error.txt
				song.setSongStatus(false);
			}	
			catch(NumberFormatException E2) {
				file.println(line +" Comma in ID");//writes to error.txt
				song.setSongStatus(false);
				
			}
		
		}
		
	}//end of errorCheck()
	
	public static void main(String[] fabs) { //main function
		
	Scanner songFile = null; //This reads the song File
	PrintWriter errorFile = null; //writes to the error.txt file
	PrintWriter songCatFile = null; //writes to the song_category file

	try { //try-catch for songFile
		songFile = new Scanner(new FileInputStream("songs.txt"));
		errorFile = new PrintWriter(new FileOutputStream("error.txt"));
		songCatFile = new PrintWriter(new FileOutputStream("song_category.txt"));
	}
	catch(FileNotFoundException e) {
		
		System.out.println("Error" +e.getMessage());
		System.exit(0);
	}//end of try catch

	String songLine, catLine; //stores line;
	String[] songToken, catToken; //stores the tokens
	
	Song song; //song object 
	Categories cat; //category object
	
	while(songFile.hasNextLine()) { //Here we start reading the song.txt file
		
		songLine = songFile.nextLine();
		songToken = songLine.split(","); //songLine is split wherever there is a ","
		
		song = new Song(songToken[0]); //constructor is called
		errorCheck(songToken, songLine, song, errorFile); //This is where the songs are checked to see if they are bad
		
		if(song.getSongStatus()) { //checks if the song status is true
			
			Scanner catFile = null; //this reads the categories.txt file
			
			try {//try-catch of categories.txt
				catFile = new Scanner(new FileInputStream("categories.txt"));
			}
			catch(FileNotFoundException e) {
				
				System.out.println("Error" +e.getMessage());
				System.exit(0);
			}//end of try catch
			
			song.setSongAspects(songToken);	//the songAspects are assigned
			while(catFile.hasNextLine()) { //here we start reading the categories.txt file
					
					catLine = catFile.nextLine();
					catToken = catLine.split(","); // songLine is split wherever there is a ","
					
					cat = new Categories(Integer.parseInt(catToken[0])); //Categories constructor is called
					cat.setCatAspects(catToken); //categories aspects are set
					
					double sum=0;
					for(int i=0; i<6; i++) 
						sum = sum + Math.pow(song.getSongAspects(i) - cat.getCatAspects(i), 2); // the sum of square of differences is calculated
				
					
					if(sum < song.getMinSum()) { //here we check for minimum sum and assign it to the song
						
						song.setSongCat(cat.getCatID());
						song.setMinSum(sum);	
					}					
			}//end of inner while
			
			catFile.close(); //categories.txt file is closed
			songCatFile.println(song.getSongID() + ":" + (int)song.getSongCat() + ": Distance: " + song.getMinSum()); //here we write the songID to the song_category.txt 
																												      //along with the category that it's closest to
		}																										  //and the distance
	} //end of outer while
	
	System.out.println("Song_Category file created");
	songCatFile.close(); // songFile is closed
	
	PrintWriter catStatsFile = null; // this writes to categories_stats.txt file
	Scanner catFile2 = null; // this reads the categories.txt file
	
	try {//try-catch for this above two
		catFile2 = new Scanner(new FileInputStream("categories.txt"));
		catStatsFile = new PrintWriter(new FileOutputStream("categories_stats.txt"));
	}//end of try catch
	
	catch(FileNotFoundException e) {
		
		System.out.println("Error" +e.getMessage());
		System.exit(0);
	}	
	
	while(catFile2.hasNextLine()) {// here we start reading the categories.txt file
		
		catLine = catFile2.nextLine();
		catToken = catLine.split(",");
		
		int counter = 0; //a counter to count the number of songs which have chose this category as closest
		
		catStatsFile.println(catToken[0] + ": ");
		Scanner songCatFile2 = null; //this reads the song_category.txt file
		
		try { //try-catch for song_category.txt
			songCatFile2 = new Scanner(new FileInputStream("song_category.txt"));
		}
		catch(FileNotFoundException e) {
			
			System.out.println("Error" +e.getMessage());
			System.exit(0);
		}//end of try catch
		
		double min = 999.0; //to find the song that is closest to a category. all the sums have to be smaller than 999
		String closestSong=null;
		
		while(songCatFile2.hasNextLine()) { //here we start reading the song_category.txt file
			
			songLine = songCatFile2.nextLine();
			songToken = songLine.split(":"); //its splits wherever there is ":"
			
			if(Integer.parseInt(catToken[0])==Integer.parseInt(songToken[1])) { //checks if the songs category is equal to the category
				
				counter++;	
				if(min > Double.parseDouble(songToken[3])) { //finds the closest song to that category
					min = Double.parseDouble(songToken[3]);
					closestSong = songToken[0];
				}
			}
		}//end of inner while
		
		songCatFile2.close();
		catStatsFile.println("Number of songs:" + counter); //writes the number of songs to categories_stats.txt
		catStatsFile.println(closestSong); //writes the closest song to categories_stats.txt
		catStatsFile.println("\n");
		
	}//end of outer while
	System.out.println("Categories_stats file created");
	
	//here all files are closed
	songFile.close();
	songCatFile.close();
	catStatsFile.close();
	errorFile.close();
	System.out.println("Program complete");
	
	}// end of main
	
}//end of classifiers

				 
				
			