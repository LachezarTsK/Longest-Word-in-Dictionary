import java.util.Arrays;

/*
The solution implements a Trie Data Structure but without separate methods for 
adding words and searching for words.

If the words are added to the dictionary in an ascending order of their length, 
the search can be done at the same time while adding words to the dictionary. 
This approach also gives the possibility to stop adding words and return 
whatever results we have thus far, if the conditions of the problem statement 
are no longer fulfilled.

Of course, the real benefits of the Trie Data Structure is a fast search, which 
more than compensates the overheads connected with building the dictionary into 
a Trie. However, the present problem allows this twist in the Trie implementation.
 */
public class Solution {

    /*
    'longestWord': the longest word that can be built one character at a time by 
    other words in the dictionary. When there are more than one word of the longest 
    length, the word with the smallest lexicographical order is selected.
     */
    String longestWord;
    TrieNode root;

    public String longestWord(String[] words) {

        root = new TrieNode();
        longestWord = "";
        search_longestWordThatCanBeBuiltOneCharacterAtaTimeByOtherWords(words);

        return longestWord;
    }

    public void search_longestWordThatCanBeBuiltOneCharacterAtaTimeByOtherWords(String[] words) {

        Arrays.sort(words, (a, b) -> (a.length() - b.length()));
        int length = words.length;
        int previousLength = 0;

        for (int i = 0; i < length; i++) {

            int n = words[i].length();
            /*
            Since the words are sorted in an ascending order of their length, the search continues 
            only while the condition 'the word can be built one character at a time by other words' 
            holds true, i.e. only while the next word is either of equal length to the previous one, 
            or longer than it by exactly one character.            
             */
            if (previousLength + 1 >= n) {
                searchWhileAddingWordsToDictionary(words[i]);
                previousLength = n;
            } else {
                return;
            }
        }
    }

    public void searchWhileAddingWordsToDictionary(String word) {

        /*
        'charsEndOfWords': implemented to check the condition 
        'the word can be built one character at a time by other words'.        
         */
        int charsEndOfWords = word.length();
        TrieNode current = root;
        int length = word.length();

        for (int i = 0; i < length; i++) {

            char ch = word.charAt(i);
            if (current.alphabet[ch - 'a'] == null) {
                current.alphabet[ch - 'a'] = new TrieNode();
                charsEndOfWords--;
            } else if (!current.alphabet[ch - 'a'].isWord) {
                charsEndOfWords--;
            }

            current = current.alphabet[ch - 'a'];
        }
        current.isWord = true;

        if (charsEndOfWords + 1 == length) {
            if (longestWord.length() < length || word.compareTo(longestWord) < 0) {
                longestWord = word;
            }
        }
    }

}

class TrieNode {

    TrieNode[] alphabet;
    boolean isWord;

    public TrieNode() {
        alphabet = new TrieNode[26];
    }
}
