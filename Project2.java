import java.lang.Math;
import java.util.Scanner;

public class CurveFitting {
  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);
    System.out.println("Input length of vector: ");
    final int N = input.nextInt();

    double[] x = new double[N];
    double[] y = new double[N];
    double[] z = new double[N];

    System.out.println("Input each x value separated by a space: ");

    for (int i=0; i<x.length; i++) {
      x[i] = input.nextDouble();
    }

    System.out.println("Input each y value separated by a space: ");

    for (int i=0; i<y.length; i++) {
      y[i] = input.nextDouble();
    }

    z = linearfit(x,y,z,N);

    System.out.print("The z vector is: ");

    for (int i=0; i<z.length; i++) {
      if (i >= 1) {
        System.out.print(", " + z[i]);
      } else {System.out.print("[" + z[i]);}
    }
    System.out.println("]");

    double v = coeffDetermination(z, y, N);

    System.out.println("The coefficient of determination is: " + v);
  }

  public static double sumX(double[] x, boolean order) {

    double xSum = 0;

    for (int i=0; i < x.length; i++) {
      if (order) {
        xSum += x[i];
      } else {xSum += Math.pow(x[i],2);}
    }
    return xSum;
  }

  //Above method calulates the sum of the x values in conjunction with its order.

  public static double sumY(double[] y) {

    double ySum = 0;

    for (int i=0; i<y.length; i++) {
      ySum += y[i];
    }
    return ySum;
  }

  //Above method calculates the sum of the y values.

  public static double sumXY(double[] x, double[] y) {

    double productSum = 0;

    for (int i=0;i<x.length;i++) {
      productSum += x[i]*y[i];
    }
    return productSum;
  }

  //Above method calculates the sum of the element-wise product of the vectors x[] and y[].

  public static double[] linearfit(double[] x, double[] y, double[] z, int N) {

    boolean order = true;
    double summationX = sumX(x,order);
    double a_top = 0;
    double a_bottom = 0;

    if (order) {
      a_top += N*sumXY(x, y) - sumX(x,order)*sumY(y);
          order = false;
    }

    if (!order) {
      a_bottom += N*sumX(x,order) - Math.pow(summationX,2);
    }

    double a_1 = (a_top/a_bottom);
    double a_0 = (sumY(y)/(double)N) - (a_1)*(summationX/(double)N);

    for (int i=0; i<z.length; i++) {
      z[i] = a_0 + a_1*x[i];
    }
    return z;
  }

  //Above method calculates the values of a_1 and a_0.

  public static double coeffDetermination(double[] z, double[] y, int N) {

    double error = 0;

    for(int i=0; i<z.length; i++) {
      error += Math.pow((z[i]-y[i]),2);
    }

    double s_t = 0;

    for(int i=0; i<y.length; i++) {
      s_t += Math.pow((y[i]- sumY(y)/(double)N), 2);
    }

    double v = ((s_t - error)/s_t);

    return v;
  }
  //Above method calculates the coefficient of determination.
}
