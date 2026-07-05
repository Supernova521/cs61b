public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      // TODO: Fill in this function
      int k = 4;
      while (k >= 0){
         for (int i = 0; i < k; i++){
            System.out.print(" ");
         }
         for (int i = k; i <= 4; i++){
            System.out.print("*");
         }
         System.out.println();
         k -= 1;
      }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}