package Game;


/*
 * Random matrix/array operations that are needed for the game.
 * Transpose, reverse, rotation, print, etc.
 */
public final class GameUtilities {
	private static int[][] transposeMatrix(int[][] oldMatrix) {
		int[][] newMatrix = new int[oldMatrix[0].length][oldMatrix.length];

		for (int i = 0; i < oldMatrix.length; i++)
			for (int j = 0; j < oldMatrix[0].length; j++)
				newMatrix[j][i] = oldMatrix[i][j];

		return newMatrix;
	}

	private static int[] reverseArray(int[] array) {
		for (int i = 0; i < array.length / 2; i++) {
			int temp = array[i];
			array[i] = array[array.length - i - 1];
			array[array.length - i - 1] = temp;
		}

		return array;
	}

	private static int[][] reverseEachRow(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = reverseArray(matrix[i]);
		}

		return matrix;
	}

	private static int[][] reverseEachColumn(int[][] matrix) {
		for (int i = 0; i < matrix.length / 2; i++) {
			int[] temp = matrix[i];
			matrix[i] = matrix[matrix.length - i - 1];
			matrix[matrix.length - i - 1] = temp;
		}

		return matrix;
	}

	public static int[][] rotateMatrixClockwise(int[][] matrix) {
		matrix = transposeMatrix(matrix);
		matrix = reverseEachRow(matrix);

		return matrix;
	}

	public static int[][] rotateMatrixCounterclockwise(int[][] matrix) {
		matrix = transposeMatrix(matrix);
		matrix = reverseEachColumn(matrix);

		return matrix;
	}

	public static void printArray(int[] array) {
		for (int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");

		System.out.println();
	}

	public static void printMatrix(int[][] matrix) {
		System.out.println("---- MATRIX: ----");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++)
				System.out.print(matrix[i][j] + " ");
		}
	}

	public static boolean countValues(int[] array, int elem, int value) {
		int count = 0;
		boolean isvalid = false;

		for (int i = 0; i < array.length - 1; i++)
			if (array[i] == array[i + 1]) {
				if (array[i] == elem) {
					count++;

					if (count == value - 1)
						isvalid = true;
				}
			} else
				count = 0;

		
		count = value - 1;

		if (isvalid) {
			if (array[0] != array[1] && count >= 0) {
				if ((array[0] == 0 && array[1] == elem) || (array[1] == 0 && array[1] == elem))
					isvalid = true;
				else if ((array[0] == 1 && array[0] == 2) || (array[0] == 2 && array[0] == 1))
					isvalid = false;
			} else
				count--;

			if (array[1] != array[2] && count >= 0) {
				if ((array[1] == 0 && array[2] == elem) || (array[1] == 0 && array[2] == elem))
					isvalid = true;
				else if ((array[1] == 1 && array[2] == 2) || (array[1] == 2 && array[2] == 1))
					isvalid = false;
			} else
				count--;

			if (array[2] != array[3] && count >= 0) {
				if ((array[2] == 0 && array[3] == elem) || (array[2] == 0 && array[3] == elem))
					isvalid = true;
				else if ((array[2] == 1 && array[3] == 2) || (array[2] == 2 && array[3] == 1))
					isvalid = false;
			} else
				count--;

			if (array[3] != array[4] && count >= 0) {
				if ((array[3] == 0 && array[4] == elem) || (array[3] == 0 && array[4] == elem))
					isvalid = true;
				else if ((array[3] == 1 && array[4] == 2) || (array[3] == 2 && array[4] == 1))
					isvalid = false;
			} else
				count--;

			if (array[4] != array[5] && count >= 0) {
				if ((array[4] == 0 && array[5] == elem) || (array[4] == 0 && array[5] == elem))
					isvalid = true;
				else if ((array[4] == 1 && array[5] == 2) || (array[4] == 2 && array[5] == 1))
					isvalid = false;
			} else
				count--;
		}

		return isvalid;
	}
	
	public static boolean isFull(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[0].length; j++)
				if (matrix[i][j] == 0)
					return false;

		return true;
	}

	public static boolean areEquals(int[][] m1, int[][] m2) {
		for (int i = 0; i < m1.length; i++)
			for (int j = 0; j < m1[0].length; j++)
				if (m1[i][j] != m2[i][j]) {
					return false;
				}

		return true;
	}
}