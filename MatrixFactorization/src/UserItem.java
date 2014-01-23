import java.io.*;
//import Jama.*;


public class UserItem {
	
	public double[][] data;
	
	public static void main(String[] args) {
		
		int nbLine = 943;
		int nbCol = 1682;
		String fileName = "u.data";
		
		UserItem ui = new UserItem(fileName,nbLine,nbCol);
		ui.printUserItem();
		
	}
	

	public UserItem(String fileName, int nbLine, int nbCol){
		this.data = readDataFile(fileName, nbLine, nbCol);
	}
	
	public double[][] readDataFile(String fileName, int nbLine, int nbCol){
		
		double[][] userItem = new double[nbLine][nbCol];

		
		try {
			File file = new File(fileName);
			BufferedReader reader = null;

		
			reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    while ((text = reader.readLine()) != null) {
		    	String[] parts = text.split("\t");
		    	int part1 = Integer.parseInt(parts[0]); // user id
		    	int part2 = Integer.parseInt(parts[1]); // item id
		    	int part3 = Integer.parseInt(parts[2]); // rating
		    	// Users and items are numbered consecutively from 1
		    	userItem[part1-1][part2-1] = part3; //id-1 to fill in the whole matrix
		    }
		    
		    reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} 
		
		return userItem;
	}
	

	private void printUserItem() {
		for(int i = 0; i<10; i++){
			for(int j=0; j<20; j++){
				System.out.print(((int)this.data[i][j]) + ",");
			}
			System.out.println();
		}
				
	}
	
}
