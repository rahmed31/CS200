import java.lang.Math;
import java.util.*;

public class Derivates {
  public static void main(String[] args) {

    // Date dt1 = new Date();
    // System.out.println(dt1.getSeconds());

    Scanner input = new Scanner(System.in);
    System.out.println("Enter the type of the signal:");
    int type = input.nextInt();
    System.out.println("Enter the order of differentiation:");
    int order = input.nextInt();

    final int N = 801;
    final int length = 801;
    final double T = 0.0025;

    double[] x = new double[N];
    double[] y = new double[N];
    double[] z = new double[N];

    // System.out.print("The time indices are: ");
    // for (int i=0; i<N; i++) {
    //   System.out.print(i +", ");
    // }

    x = siggenerator(x, type, T, length);

    System.out.print("The x valuea is: ");

    for (int i=0; i<x.length; i++) {
      if (i >= 1) {
        System.out.print(", " + x[i]);
      } else {System.out.print("[" + x[i]);}
    }
    System.out.println("]");

    y = derivative(x, y, order, T);

    System.out.print("The y values are: ");

    for (int i=0; i<x.length; i++) {
      if (i >= 1) {
        System.out.print(", " + y[i]);
      } else {System.out.print("[" + y[i]);}
    }
    System.out.println("]");

    z = integral(x, z, T);

    System.out.print("The z values are: ");

    for (int i=0; i<x.length; i++) {
      if (i >= 1) {
        System.out.print(", " + z[i]);
      } else {System.out.print("[" + z[i]);}
    }
    System.out.println("]");

    // Date dt2 = new Date();
    // System.out.println(dt2.getSeconds());
  }

  public static double[] siggenerator(double[] x, int type, double T, int length) {

    if (type == 1) {
      for (int i=0; i < x.length; i++) {
        x[i] = Math.sin((2*Math.PI)*i*T);
      }
    }

    else if (type == 2) {
      for (int i=0; i < x.length; i++) {
        x[i] = -Math.signum(Math.sin((2*Math.PI)*i*T));
      }
    }

    else if (type == 3) {
      boolean slope = true;
      double shift = 0d;

      for (int i=0; i < x.length; i++) {
        if (slope) {
          x[i] = (1d/200)*(i) - shift;
          if (x[i] == 1) {
            shift += 2d;
            slope = false;
          }
        }
        else if (!slope) {
          x[i] = -(1d/200)*(i) + shift;
          if (x[i] == 0) {
            slope = true;
          }
        }
      }

    }

    else {
      for (int i=0; i < x.length; i++) {
        x[i] = (Math.pow(i,2))/(Math.pow(length,2));
      }
    }
    return x;
  }

  public static double[] derivative(double[] x, double[] y, int order, double T) {

    if (order == 1) {
      for (int i=1; i<x.length; i++) {
        y[i-1] = (x[i] - x[i-1])/T;
      }
    }
    else {
      for (int i=2; i<x.length; i++) {
        y[i-2] = (3*x[i] - 4*x[i-1] + x[i-2])/2d*T;
      }
    }
    return y;
  }

  public static double[] integral(double[] x, double[] z, double T) {

    for(int i=0; i<1; i++) {
      z[i] = T*((x[1]+x[0])/2d);
    }
    for(int i=1; i<x.length-1; i++) {
      z[i] = z[i-1] + T*((x[i+1]+x[i])/2d);
    }
    return z;
  }
}
