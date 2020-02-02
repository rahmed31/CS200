import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ApproxStringMatchingUsingLD {
   public static int distance(String a, String b) {
     a = a.toLowerCase();
     b = b.toLowerCase();
     int[] costs = new int[b.length() + 1];

     for (int j = 0; j < costs.length; j++) {costs[j] = j;}

     for (int i = 1; i <= a.length(); i++) {
       costs[0] = i;
       int nw = i - 1;
       for (int j = 1; j <= b.length(); j++) {
         int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
         nw = costs[j];
         costs[j] = cj;
       }
     }
     return costs[b.length()];
   }

   public static void main(String[] args) {
     Scanner input = new Scanner(System.in);
     String text = "aagtc ccgta ttcgaa";

     System.out.println("System genetated string is: \n'" + text + "'");
     System.out.println("Enter the keyword to search: ");
     String keyword = input.nextLine();
     String[] data = text.split(" ");

     List<Integer> dist = new ArrayList<Integer>();

     for (int i = 0; i < data.length; i++) {
       dist.add(distance(data[i], keyword));
     }
     System.out.println("The distances between the word and each word of the string is: " + dist);
     Collections.sort(dist);

     System.out.print("Autocorrection: ");
     for (int i = 0; i < data.length; i++) {
       if (distance(data[i], keyword) == dist.get(0)) {
         System.out.print(data[i] + " ");
       }
     }
     System.out.println("");
   }
}

// String text = "In computer science, approximate string matching, "
//    + "often colloquially referred to as fuzzy string searching is the technique of finding strings that match a pattern approximately rather than exactly. "
//    + "The problem of approximate string matching is typically divided into two sub-problems: finding approximate substring matches inside a given string and finding "
//    + "dictionary strings that match the pattern approximately.";
