import java.util.Scanner;

//Formatted output version

public class Adder {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    int[] in = new int[4];
    int[] out = new int[2];
    boolean out1;

    System.out.println("Enter the numbers in your array: ");
    for (int i=0; i<in.length; i++) {
      in[i] = input.nextInt();
    }

    System.out.print("[");
    int counter = 0;
    for (int j=0; j<in.length; j++, counter++) {
      if (counter < in.length-1) {
        System.out.print(in[j] + " ");
      }
      if (counter==in.length-1) {
        System.out.print(in[j]);
        break;
      }
    }
    System.out.println("]");

    out = Calculator(in);

    System.out.print("The two numbers that add up to 8 are: ");
    System.out.print("[");
    int counter1 = 0;
    for (int k=0; k<out.length; k++, counter1++) {
      if (counter1 < out.length-1) {
        System.out.print(out[k] + " ");
      }
      if (counter1==out.length-1) {
        System.out.print(out[k]);
        break;
      }
    }
    System.out.println("]");
  }
  public static int[] Calculator(int in[]) {
    int[] out = new int[2];
    boolean out1 = false;
    int sum = 0;
    for (int i=0; i<in.length; i++) {
      for (int z=1; z<in.length-1; z++) {
        sum += in[i] + in[z];
        if (sum != 8) {
          sum = 0;
        }
        if (sum == 8) {
          out[0] = in[i];
          out[1] = in[z];
          out1 = true;
          break;
        }
      }
    }
    return out;
  }
  public static boolean Boolean(int in[]) {
    boolean out1 = false;
    int sum = 0;
    for (int i=0; i<in.length; i++) {
      sum += in[i] + in[i+1];
      if (sum != 8) {
        sum = 0;
      }
      if (sum == 8) {
        out1 = true;
        break;
      }
    }
    return out1;
  }
}

//Simple version

// public class Adder {
//   public static void main(String[] args) {
//     Scanner input = new Scanner(System.in);
//     int[] in = new int[4];
//     boolean out = false;
//
//     System.out.println("Enter the numbers in your array: ");
//     for (int i=0; i<in.length; i++) {
//       in[i] = input.nextInt();
//     }
//     out = Boolean(in);
//     System.out.println("Are there two values that sum to 8?: " + out);
//   }
//   public static boolean Boolean(int in[]) {
//     boolean out = false;
//     int sum = 0;
//     for (int i=0; i<in.length; i++) {
//       for (int z=1; z<in.length-1; z++) {
//         sum += in[i] + in[z];
//         if (sum != 8) {
//           sum = 0;
//           out = false;
//         }
//         if (sum == 8) {
//           out = true;
//           break;
//         }
//       }
//     }
//     return out;
//   }
// }

// Even more simple

public class Adder {
  public static void main(String[] args) {
    int[] in = {4,0,1,4};
    boolean out = false;
    out = Boolean(in);

    System.out.println("Are there two values that sum to 8?: " + out);
  }
  public static boolean Boolean(int in[]) {
    boolean out = false;
    int sum = 0;
    for (int i=0; i<in.length-1; i++) {
      for (int z=i+1; z<in.length; z++) {
        sum += (in[i] + in[z]);
        if (sum < 8 ^ sum > 8) {
          sum = 0;
        }
        else {
          out = true;
          break;
        }
      }
    }
    return out;
  }
}
