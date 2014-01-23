import java.util.ArrayList;
import java.util.Random;


public class SGD {
	public int rank;
	public int steps;
	public double alpha;
	public double beta;
	private UserItem ui;

	public static void main(String[] args) {
		
		// Matrix to be factorized: 
		
		// Necessary info:
		int nbLine = 943;
		int nbCol = 1682;
		String fileName = "u.data";
		
		// Object UserItem
		UserItem ui = new UserItem(fileName,nbLine,nbCol);
		
		// Parameters of the algorithm:
		int steps=5000;
		double alpha=0.0002;
		double beta=0.02;
		int rank = 10;
		
		// Create a SGD object
		
		// Constructor:
		SGD sgd = new SGD(steps, alpha, beta, rank, ui);
		
		// Use of the object:
		ArrayList<double[][]> result = new ArrayList<double[][]>();
		result = sgd.sgd_algo();
		
		// Show results:
		double [][] P = result.get(0);
		double [][] Q = result.get(1);
		double [][] PQ =  matrixProduct(P,Q);
		
		/*System.out.print("P=");
		showMatrix(P);
		System.out.print("Q=");
		showMatrix(Q);*/
		System.out.println("PQ=");
		showMatrix(PQ);
		System.out.println("userItem=");
		showMatrix(ui.data);
		
		
	}

	public SGD(int s, double a, double b, int r, UserItem userItem){

		this.rank = r;
		this.steps = s;
		this.alpha = a;
		this.beta = b;
		this.ui = userItem;

		//int [][] userItem = readInput();
		
		
		
	}

	private ArrayList<double[][]> sgd_algo(){

		// Initialize P,Q randomly
		double [][] P = RandomArray(ui.data.length, rank);
		double [][] Q = RandomArray(rank, ui.data[0].length);
		double eij=0;
		double error = 0;

		for (int step=0; step<steps; step++){
			System.out.println("step : " + step);
			
			for (int i=0; i<ui.data.length; i++){
				for (int j=0; j<ui.data[i].length; j++){

					// Compute error
					if (ui.data[i][j]>0){ // if not unknown
						eij=ui.data[i][j] - dotProduct(P,Q,i,j);


						// Update matrices P and Q
						for (int k=0; k<rank; k++){
							P[i][k] += alpha * (2*eij*Q[k][j] - beta*P[i][k]);
							Q[k][j] += alpha * (2*eij*P[i][k] - beta*Q[k][j]);
						}

					} // end if
					
					

				} // end i
			} // end j
			
			// Compute error
			for (int i=0; i<ui.data.length; i++){
				for (int j=0; j<ui.data[i].length; j++){
					if (ui.data[i][j]>0){
						error += Math.pow(ui.data[i][j] - dotProduct(P,Q,i,j), 2);
						for (int k=0; k<rank; k++){
							error += beta/2 * (Math.pow(P[i][k], 2) + Math.pow(Q[k][j],2));
						} // k 
					}// if
				} // j
			} // i
			
			if (error < 0.001){
				break;
			}
		}
		
		ArrayList<double[][]> result = new ArrayList<double[][]>();
		result.add(P);
		result.add(Q);
		return result;
	}
	

	private static double[][] RandomArray(int n, int m) {
		double[][] randomMatrix = new double [n][m];

		Random rand = new Random(); 
		rand.setSeed(System.currentTimeMillis()); 
		for (int i = 0; i < n; i++) {     
			for (int j = 0; j < m; j++) {
				double r = rand.nextDouble(); 
				randomMatrix[i][j] = Math.abs(r);
			}

		}

		return randomMatrix;
	}

	private static double dotProduct(double[][] pMatrix, double[][] qMatrix, int i, int j){
		double result=0;

		double[] pi= pMatrix[i];

		double [] qj = new double[qMatrix.length];
		for (int k=0; k<qMatrix.length; k++){
			qj[k]=qMatrix[k][j];
		}

		for (int k=0; k<pMatrix[0].length; k++){
			result += pi[k]*qj[k];
		}

		return result;
	}
	
	/**
	 * Compute P.Q (matrix product)
	 * @param P
	 * @param Q
	 * @return
	 */
	private static double[][] matrixProduct(double[][] P, double[][] Q){
		
		int n1 = P.length ;
		int n2 = P[0].length;
		int m1 = Q.length;
		int m2 = Q[0].length;
		
		double[][] result= new double[n1][m2];
		double temp=0;
		

		if (n2!=m1) {
			System.out.println("Multiplication impossible (vérifier les dimensions)");
		}
		
		for (int i=0; i<n1; i++){
			for (int j=0; j<m2; j++){
				for (int k=0; k<m1; k++){
				temp += P[i][k]*Q[k][j];
				}
				result[i][j]=temp;
				temp=0;
			}
		}
		
		return result;
	}

	/**
	 * Method that will allow to print the matrix M.
	 * @param M : matrix to be shown.
	 */
	private static void showMatrix(double[][] M) {
		int n=M.length;
		int m=M[0].length;
		
		for (int i=0; i<n; i++){
			for (int j=0; j<m; j++){
				System.out.print((int)(100*M[i][j])/100.+" , ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static void showMatrix(double[][] M, int nb_lines, int nb_columns) {
		int n=nb_lines;
		int m=nb_columns;
		
		for (int i=0; i<n; i++){
			for (int j=0; j<m; j++){
				System.out.print((int)(100*M[i][j])/100.+" , ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
