public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      // TODO: Fill in this function
      int k = N - 1;
      while (k >= 0) {
         for (int i = 0; i < k; i++) {
            System.out.print(" ");
         }
         for (int i = k; i <= N - 1; i++) {
            System.out.print("*");
         }
         System.out.println();
         k -= 1;
      }
   }

      public static void main(String[] args) {
      starTriangle(7);
   }
}