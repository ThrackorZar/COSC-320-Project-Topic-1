import java.io.*;
import java.util.*;

public class main {
    
    //****
    //ATTENTION: Change directories of both Bufferedreaders to current location of acronyms.csv and input.csv, otherwise final output will be null as no data will be read!
    // Left current working directory in as proof of working program as of submission.
    //****
    
    public static void main(String[] args) {
        // Read in the first CSV file containing the strings
        ArrayList<String> strings = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\jaden\\Downloads\\COSC320Team15Milestone3\\input.csv"));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String str = parts[5].trim();
                    strings.add(str);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Read in the second CSV file containing the acronyms and their expanded versions
        ArrayList<String[]> acronymList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\jaden\\Downloads\\COSC320Team15Milestone3\\acronyms.csv"));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String[] acronym = { parts[0].trim(), parts[1].trim() };
                    acronymList.add(acronym);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Replace acronyms in each string
        long startTime = System.currentTimeMillis();
        int acronymReplacements = 0;
        for (String str : strings) {
            String[] words = str.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                for (String[] acronym : acronymList) {
                    if (words[i].equalsIgnoreCase(acronym[0])) {
                        words[i] = acronym[1];
                        System.out.println(acronym[0]+ " " + acronym[1]);
                        acronymReplacements++;
                        break;
                    }
                }
            }
            String result = String.join(" ", words);
            System.out.println(result);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
        System.out.println("Acronym replacements: " + acronymReplacements);
    }
}