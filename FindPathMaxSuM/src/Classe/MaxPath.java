package Classe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MaxPath {
class Result{
		
		public Result(List<Integer> path, int total) {
			this.path = path;
			this.total = total;
		}
		private List<Integer> path = new ArrayList<Integer>();
		private int total = 0;
		public List<Integer> getPath() {
			return path;
		}
		public void setPath(List<Integer> path) {
			this.path = path;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		
	}
	
	public static void main(String[] args) {
		LinkedList<LinkedList<Integer>> pyramid = ReadFromFile("test2");
		if(pyramid != null) {
			int[][] matrix = ParseMatrix(pyramid);

			Result res = findPath(matrix);
			System.out.println("The biggest sum possible is : "+res.getTotal());
			System.out.println("Its path is : "+res.getPath());
		}
	}

	// this function is responsible for finding the path of the maximum sum in the matrix with the helf of two other functions that calculate and search for the maximum 
	private static Result findPath(int[][] matrix) {
		int s = matrix[0].length;
		Result[][] paths = new Result[s][s];
		paths[0][0] = new MaxPath().new Result(new ArrayList<Integer>(), matrix[0][0]);
		paths[0][0].getPath().add(matrix[0][0]);
		for(int i=0;i<s-1;i++) {
			for(int j=0;j<=i;j++) {
				if(paths[i][j]==null) {
					continue;
				}
				paths = CalculateNewPaths(matrix,paths,i,j,s);
			}
		}

		return findMaxPath(paths,s);
	}

	private static Result findMaxPath(Result[][] paths, int s) {
		for(int i=s-1;i>0;i--) {
			Result max = null;
			for(int j=0;j<=i;j++) {
				if(paths[i][j]!=null) {
					if(max==null) {
						max = paths[i][j];
					}else if(max.getTotal()<paths[i][j].getTotal()) {
						max = paths[i][j];
					}
				}
			}
			if(max!=null) {
				return max;
			}
		}
		// TODO Auto-generated method stub
		return paths[0][0];
	}

	private static Result[][] CalculateNewPaths(int[][] matrix, Result[][] paths, int i, int j, int s) {

			if(!isPrime(matrix[i+1][j])) {
				int total = paths[i][j].getTotal()+matrix[i+1][j];
				if(paths[i+1][j]==null) {
					paths[i+1][j] = new MaxPath().new Result(new ArrayList<Integer>(), 0);
				}
				if(total>paths[i+1][j].getTotal()) {
					paths[i+1][j].setTotal(total);
					paths[i+1][j].setPath(null);
					paths[i+1][j].setPath(new ArrayList<Integer>(paths[i][j].getPath()));
					paths[i+1][j].getPath().add(matrix[i+1][j]);
				}
			}
			

			if(!isPrime(matrix[i+1][j+1])) {
				if(paths[i+1][j+1]==null) {
					paths[i+1][j+1] = new MaxPath().new Result(new ArrayList<Integer>(), 0);
				}
				int total = paths[i][j].getTotal()+matrix[i+1][j+1];
				if(total>paths[i+1][j+1].getTotal()) {
					paths[i+1][j+1].setTotal(total);
					paths[i+1][j+1].setPath(null);
					paths[i+1][j+1].setPath(new ArrayList<Integer>(paths[i][j].getPath()));
					paths[i+1][j+1].getPath().add(matrix[i+1][j+1]);
				}
			}
		
		return paths;
	}

	private static boolean isPrime(int p) {

		if(p<=1) return false;
		if(p==2) return true;
		if(p%2==0)return false;
		for(int i=3;i<=Math.sqrt(p);i+=2) {
			if(p%i==0)return false;
		}
		
		return true;
	}

	//this function is responsible for creating a matrix from the linkedlist we created in the readfromfile function
	private static int[][] ParseMatrix(LinkedList<LinkedList<Integer>> pyramid) {
		int size = pyramid.getLast().size();
		int[][] matrix = new int[size][size];
		int i=0;
		for(LinkedList<Integer> l : pyramid) {
			int j=0;
			for(Integer number : l) {
				matrix[i][j] = number;
				j++;
			}
			i++;
		}
		
		return matrix;
	}
	// this function is responsible for reading from the file and creating a list that add the number from each line
	private static LinkedList<LinkedList<Integer>> ReadFromFile(String path) {

		LinkedList<LinkedList<Integer>> integerList = null;
		File f = new File(path);
		
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("File was not found");
		}
		
		if(sc!=null) {
			integerList = new LinkedList<LinkedList<Integer>>();
			while(sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				String arr[] = line.split(" ");
				LinkedList<Integer> tempList = new LinkedList<Integer>();
				for(String str : arr) {
					tempList.add(Integer.parseInt(str));
				}
				integerList.add(tempList);
			}
			sc.close();
		}
		
		return integerList;
	}


}
