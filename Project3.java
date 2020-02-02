import java.lang.Math;
import java.util.Scanner;
import java.util.Random;

public class CommunicationLink {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.println("Enter number of input samples:");
    final int N = input.nextInt();

    double[] randSig = new double[N];
    double[] quanSigIn = new double[N];
    double[] quanSigOut = new double[N];
    int[] bitsIn = new int[4*N];
    int[] bitsIn2 = new int[4*N];
    double[] modIn = new double[2*N];
    double[] noisySig = new double[2*N];
    int[] bitsOut = new int[4*N];

    randSig = randGenerator(randSig);

    System.out.print("The randSig values are: ");
    for (int i=0; i<randSig.length; i++) {
      if (i >= 1) {
        System.out.print(", " + randSig[i]);
      } else {System.out.print("[" + randSig[i]);}
    }
    System.out.println("]");

    quanSigIn = quantize(randSig, quanSigIn);

    System.out.print("The quantized values are: ");
    for (int i=0; i<quanSigIn.length; i++) {
      if (i >= 1) {
        System.out.print(", " + quanSigIn[i] + "q");
      } else {System.out.print("[" + quanSigIn[i] + "q");}
    }
    System.out.println("]");

    bitsIn = realToBin(quanSigIn, bitsIn);

    int k=0;
    for (int i=bitsIn.length-1; i>=0; i-=4, k+=4) {
      bitsIn2[k] = bitsIn[i-3];
      bitsIn2[k+1] = bitsIn[i-2];
      bitsIn2[k+2] = bitsIn[i-1];
      bitsIn2[k+3] = bitsIn[i];
    }

    System.out.print("The quantized values in binary (in order) are: ");
    System.out.print("[");
    for (int i=1; i<bitsIn2.length+1; i++) {
      System.out.print(bitsIn2[i-1]);
        if (i%4 == 0 && i< bitsIn2.length-1) {
          System.out.print(" ");
        }
        if (i%4 == 0 && i == bitsIn2.length) {
          System.out.println("]");
        }
    }

    // System.out.print("The quantized values in binary (in order) are: ");
    // System.out.print("[");
    // for (int i=bitsIn.length-1; i>=0; i-=4) {
    //   System.out.print(bitsIn[i-3]);
    //   System.out.print(bitsIn[i-2]);
    //   System.out.print(bitsIn[i-1]);
    //   System.out.print(bitsIn[i]);
    //   System.out.print(" ");
    // }
    // System.out.println("]");
    //This is for bitsIn

  //   System.out.print("The quantized values in binary (reversed) are: ");
  //
  //   System.out.print("[");
  //   for (int i=1; i<bitsIn.length+1; i++) {
  //     System.out.print(bitsIn[i-1]);
  //       if (i%4 == 0 && i< bitsIn.length-1) {
  //         System.out.print(" ");
  //       }
  //       if (i%4 == 0 && i == bitsIn.length) {
  //         System.out.println("]");
  //       }
  //   }
  //This is for bitsIn

    modIn = modulation(bitsIn2, modIn);

    System.out.print("The randSig values are: ");
    for (int i=0; i<modIn.length; i++) {
      if (i >= 1) {
        System.out.print(", " + modIn[i]);
      } else {System.out.print("[" + modIn[i]);}
    }
    System.out.println("]");

    noisySig = addNoise(modIn, noisySig);

    System.out.print("The noisySig values are: ");
    for (int i=0; i<noisySig.length; i++) {
      if (i >= 1) {
        System.out.print(", " + noisySig[i]);
      } else {System.out.print("[" + noisySig[i]);}
    }
    System.out.println("]");

    bitsOut = demodulation(noisySig, bitsOut);

    System.out.print("The binary outputs are: ");
    System.out.print("[");
    for (int i=1; i<bitsOut.length+1; i++) {
      System.out.print(bitsIn2[i-1]);
        if (i%2 == 0 && i< bitsOut.length-1) {
          System.out.print(" ");
        }
        if (i%2 == 0 && i == bitsOut.length) {
          System.out.println("]");
        }
    }

    quanSigOut = binToReal(bitsOut, quanSigOut);

    System.out.print("The quantized outputs are: ");
    for (int i=0; i<quanSigIn.length; i++) {
      if (i >= 1) {
        System.out.print(", " + quanSigIn[i] + "q");
      } else {System.out.print("[" + quanSigIn[i] + "q");}
    }
    System.out.println("]");

    double ber = bitErrorRate(bitsOut, bitsIn2, N);
    System.out.println("The bit error rate is: " + ber);

    double error = meanPower(quanSigIn, quanSigOut, N);
    System.out.println("The mean power is: " + error);
  }

  public static double[] randGenerator(double[] randSig) {
    Random r = new Random();
    for (int i=0; i<randSig.length; i++) {
       randSig[i] = (r.nextInt(99)+1)/100.0;
    }
    return randSig;
  }

  public static double[] quantize(double[] randSig, double[] quanSigIn) {
    double q = 0.0625;

    for (int i=0; i<randSig.length; i++) {
      if ((randSig[i]/q)%1 == 0) {
        quanSigIn[i] = (randSig[i]/q)+0.50;
      }
      else {
        double temp = randSig[i]/q;
        int whole = (int)temp;
        quanSigIn[i] = (double)whole+0.50;
      }
    }
    return quanSigIn;
  }

  public static int[] realToBin(double[] quanSigIn, int[] bitsIn) {
    double q = 0.0625;
    int j = bitsIn.length-1;
    int counter = 0;

    for (int i=0; i<quanSigIn.length; i++) {
      int temp = (int)((q*quanSigIn[i]-(q/2))/q);
      // System.out.println(temp);
      while (temp>=0) {
        bitsIn[j--] = temp%2;
        temp = temp/2;
        counter++;
        if (counter%4 == 0) {break;}
      }
      if (counter%4 != 0) {
        while(counter%4 != 0) {
          bitsIn[j--] = 0;
          counter++;
        }
      }
    }
    return bitsIn;
  }

  public static double[] modulation(int[] bitsIn, double[] modIn) {
    int k=0;
    for(int i=0; i<bitsIn.length; i+=2, k++) {
      if (bitsIn[i] == 0 && bitsIn[i+1] == 0) {modIn[k] = -3;}
      else if (bitsIn[i] == 0 && bitsIn[i+1] == 1) {modIn[k] = -1;}
      else if (bitsIn[i] == 1 && bitsIn[i+1] == 1) {modIn[k] = 1;}
      else if (bitsIn[i] == 1 && bitsIn[i+1] == 0) {modIn[k] = 3;}
    }
    return modIn;
  }

  public static double[] addNoise(double[] modIn, double noisySig[]) {
    Random r = new Random();
    for (int i=0; i<modIn.length; i++) {
      noisySig[i] = modIn[i] + (r.nextInt(90)-40)/100.0;
    }
    return noisySig;
  }

  public static int[] demodulation(double[] noisySig, int[] bitsOut) {
    int k=0;

    for (int i=0; i<noisySig.length; i++, k+=2) {
      if (noisySig[i] <= -2) {
        bitsOut[k] = 0;
        bitsOut[k+1] = 0;
      }
      else if (noisySig[i] > -2 && noisySig[i] <= 0) {
        bitsOut[k] = 0;
        bitsOut[k+1] = 1;
      }
      else if (noisySig[i] > 0 && noisySig[i] <= 2) {
        bitsOut[k] = 1;
        bitsOut[k+1] = 1;
      }
      else if (noisySig[i] > 2) {
        bitsOut[k] = 1;
        bitsOut[k+1] = 0;
      }
    }
    return bitsOut;
  }

  public static double[] binToReal(int[] bitsOut, double[] quanSigOut) {
    int k=0;
    double q = 0.0625;
    for (int i=0; i<bitsOut.length; i+=4, k++) {
      quanSigOut[k] = (bitsOut[i]*8 + bitsOut[i+1]*4 + bitsOut[i+2]*2 + bitsOut[i+3]*1)*q + (q/2);
    }
    return quanSigOut;
  }

  public static double bitErrorRate(int[] bitsOut, int[] bitsIn2, int N) {
    double sum = 0;
    double b = 4;
    double n = (double)N;
    for (int i=0; i<bitsOut.length; i++) {
      sum += Math.abs(bitsOut[i]-bitsIn2[i]);
    }
    double ber = (1/(b*n))*sum;
    return ber;
  }

  public static double meanPower(double[] quanSigIn, double[] quanSigOut, int N) {
    double sum = 0;
    double n = (double)N;
    for (int i=0; i<quanSigIn.length; i++) {
      sum += Math.pow((quanSigOut[i]-quanSigIn[i]),2);
    }
    double error = (1/n)*sum;
    return error;
  }
}
