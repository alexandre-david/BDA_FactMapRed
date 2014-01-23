import java.util.ArrayList;
import java.util.Random;


public class Block_Permutation {
	
	int d;
	ArrayList<int[][]> result;
	
	public static void main(String[] args) {
		int d = 6;
		
		Block_Permutation permutation = new Block_Permutation(d);
		permutation.printPermut();
		
	}
	
	public Block_Permutation(int d){
		this.d = d;
		this.result=permut();
	}
	
	private ArrayList<int[][]> permut(){
		
		this.result = new ArrayList<int[][]>();
		int[][] matrixI = new int[d][d];
		int[][] matrixJ = new int[d][d];
		
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		int r = rand.nextInt(d);
		//System.out.println(r);
		
		for(int i=0 ; i<d; i++){
			for(int j=0; j<d; j++){
				matrixI[i][j] = i;
				matrixJ[i][j] = (j + r +i)%d;
			}
		}
		result.add(matrixI);
		result.add(matrixJ);
		
		return result;
		
	}
	
	private void printPermut(){
		for(int i = 0; i<d; i++){
			for(int j=0; j<d; j++){
				System.out.print("(" + result.get(0)[i][j] + "," + result.get(1)[i][j] + ") ");
			}
			System.out.println();
		}	
	}
	
}
