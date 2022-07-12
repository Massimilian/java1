

public class Main {
    public static void main(String[] args) {
        int[][] matrix = new int[10][10];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = i*j;
                String n = Integer.toString(matrix[i][j]);
                if (n.length()==2) {
                    System.out.print(" " + matrix[i][j]);
                } else {
                    System.out.print(" " + matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}