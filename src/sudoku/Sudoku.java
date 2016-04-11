package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CS2420 Assignment 4 - Sudoku.java
 * 
 * This program solves a Sudoku puzzle from the command line using backtracking
 * and recursion.
 * 
 * @author Bailey Jiang
 * @author Alexander Gottlieb
 *
 */
public class Sudoku {
	/**
	 * Count backtrack and save p as a 2D array representing the puzzle.
	 * original is the original puzzle that is represented by the periods.
	 */
	private static int backtrack;
	private static int[][] p;
	private static String[][] original;

	public static void main(String args[]) throws FileNotFoundException {
		if (!(args.length == 1)) {
			System.out.println("Must take in at least one parameter!");
			System.exit(0);
		}

		File f = new File(args[0]);
		Scanner sc = new Scanner(f);
		// Print to console
		System.out.println("Unsolved:");
		// Writes original puzzle to console.
		read(sc);
		writeToArray();
		System.out.println();
		System.out.println("Solved:");
		// Writes solved puzzle to console.
		solved();
		System.out.println("Backtrack steps: " + backtrack);

	}

	/**
	 * Read a scanner that represents a Sudoku puzzle and prints to console.
	 * 
	 * @param s
	 *            - Scanner that represents a Sudoku puzzle.
	 */
	public static void read(Scanner s) {
		p = new int[9][9];
		original = new String[9][9];
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				if (s.hasNext()) {
					String temp = s.next();
					original[i][j] = temp;
				}
				System.out.print(original[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Writes the puzzle to a 2D array with the periods represented by 0.
	 */
	public static void writeToArray() {
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				if (!original[i][j].equals(".")) {
					p[i][j] = Integer.parseInt(original[i][j]);
				} else {
					p[i][j] = 0;
				}
			}
		}
	}

	/**
	 * Writes the solved puzzle to the console.
	 */
	public static void solved() {
		// Call the solver method starting at index (0, 0).
		Solver(0, 0);
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				System.out.print(p[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Main solver for the Sudoku puzzle. Uses recursion and backtracking.
	 * 
	 * @param i
	 *            - X index of the 2D array
	 * @param j
	 *            - Y inex of the 2D array
	 * @return - Returns true or false depending on if it found a valid value.
	 */
	public static boolean Solver(int i, int j) {
		// Checks the index of i. Since i is constantly incremented, if it
		// reaches 9, reset i and increment j.
		if (i >= 9) {
			i = 0;
			j++;
		}
		// Base case. If this returns true, the entire puzzle is solved.
		if (j >= 9) {
			return true;
		}
		// Check if the index is empty
		if (p[i][j] == 0) {
			// Loop to write 1-9
			for (int z = 1; z < 10; z++) {
				// Now check rows and columns
				if (check(z, i, j)) {
					p[i][j] = z;
					// Do a recursive call if everything is valid (check
					// passes).
					if (Solver(i + 1, j)) {
						return true;
					} else {
						// If it fails, then backtrack. Reset the current index
						// to 0.
						p[i][j] = 0;
					}
					// Continue the loop if z is not compatible.
				} else {
					continue;
				}
			}
			// If the index is not empty, increment i and call the next index.
		} else {
			return Solver(i + 1, j);
		}
		// Increment backtrack. If everything fails, the algorithm should leak
		// to false here.
		backtrack++;
		return false;
	}

	/**
	 * Checks if a number z is compatible with Sudoku's rules. Cannot be
	 * duplicates in the current column, row, or within a 3x3 square that it
	 * resides in.
	 * 
	 * @param z
	 *            - Number to check.
	 * @param i
	 *            - X index of the 2D array.
	 * @param j
	 *            - Y index of the 2D array.
	 * @return - Returns true if everything passes, false otherwise.
	 */
	public static boolean check(int z, int i, int j) {
		// Check rows:
		for (int x = 0; x < 9; x++) {
			if (p[x][j] == z) {
				return false;
			}
		}
		for (int y = 0; y < 9; y++) {
			if (p[i][y] == z) {
				return false;
			}
		}
		// Now check squares:
		if (i < 3) {
			if (j < 3) {
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 3; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
			if (j < 6 && j > 2) {
				for (int x = 0; x < 3; x++) {
					for (int y = 3; y < 6; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
			if (j < 9 && j > 5) {
				for (int x = 0; x < 3; x++) {
					for (int y = 6; y < 9; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
		}
		if (i < 6 && i > 2) {
			if (j < 3) {
				for (int x = 3; x < 6; x++) {
					for (int y = 0; y < 3; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
			if (j < 6 && j > 2) {
				for (int x = 3; x < 6; x++) {
					for (int y = 3; y < 6; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
			if (j < 9 && j > 5) {
				for (int x = 3; x < 6; x++) {
					for (int y = 6; y < 9; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
		}
		if (i < 9 && i > 5) {
			if (j < 3) {
				for (int x = 6; x < 9; x++) {
					for (int y = 0; y < 3; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
			if (j < 6 && j > 2) {
				for (int x = 6; x < 9; x++) {
					for (int y = 3; y < 6; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
			if (j < 9 && j > 5) {
				for (int x = 6; x < 9; x++) {
					for (int y = 6; y < 9; y++) {
						if (p[x][y] == z) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}