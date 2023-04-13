import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// A node in the trie
class TrieNode {
    Map<Character, TrieNode> children; // The children of the node
    String value; // The expansion of the acronym, if this node represents the end of an acronym

    public TrieNode() {
        children = new HashMap<>();
        value = null;
    }
}

// The trie data structure
class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a key-value pair (an acronym and its expansion) into the trie
    public void insert(String key, String value) {
        TrieNode current = root;
        // Traverse the trie based on the characters of the key
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                // If the current node doesn't have a child for the current character, create one
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        // At the end of the traversal, mark the current node as the end of the key and store the value
        current.value = value;
    }

    // Searches the trie for a given key and returns its value (i.e., the expansion of the acronym)
    public String search(String key) {
        TrieNode current = root;
        // Traverse the trie based on the characters of the key
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                // If we encounter a null node while traversing, the key is not in the trie
                return null;
            }
            current = node;
        }
        // If we've reached the end of the key, return its value
        return current.value;
    }
}

public class AlgorithmB {
    public static Trie trie = new Trie();
    public static ArrayList<String> tweets = new ArrayList<String>();
    public static ArrayList<String> newTweets = new ArrayList<String>();
    public static void main(String[] args) {
        System.out.println("loading acronym database...");
        loadAcronyms("C:\\Users\\miles\\Desktop\\Assignments\\COSC 320\\cosc320\\src\\main\\resources\\acronyms.csv");
        System.out.println("acronym database loaded");
        System.out.println("loading tweets database..."); 
        loadTweets("C:\\Users\\miles\\Desktop\\Assignments\\COSC 320\\cosc320\\src\\main\\resources\\tweets.csv");
        System.out.println("tweets database loaded");
        StringBuilder sb = new StringBuilder();

        long startTime = System.currentTimeMillis();
        for (int j = 0; j < tweets.size(); j++) {
            String[] words = tweets.get(j).split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String word = words[i].toLowerCase();
                String expansion = trie.search(word);
                if (expansion != null) sb.append(expansion);
                else sb.append(word);
            }
            tweets.set(j, sb.toString());
            sb.setLength(0);
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " milliseconds");
    }

    public static void loadAcronyms(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                String[] acronym = {parts[0].trim(), parts[1].trim()};
                if (parts.length == 2) trie.insert(acronym[0], acronym[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void loadTweets(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) tweets.add(parts[5].trim());
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}