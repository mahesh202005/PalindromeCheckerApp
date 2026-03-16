import java.util.LinkedList;

/**
 * =============================================================================
 * MAIN CLASS - UseCase8PalindromeCheckerApp
 * =============================================================================
 * * Use Case 8: Linked List Based Palindrome Checker
 * * Description:
 * This class checks whether a string is a palindrome
 * using a LinkedList.
 * * Characters are added to the list and then compared
 * by removing elements from both ends:
 * * - removeFirst()
 * - removeLast()
 * * This demonstrates how LinkedList supports
 * double-ended operations for symmetric validation.
 * * @author Developer
 * @version 8.0
 */
public class PalindromeCheckerApp {

    /**
     * Application entry point for UC8.
     * * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Define the input string
        String input = "level";
        System.out.println("Input : " + input);

        // Create a LinkedList to store characters
        LinkedList<Character> list = new LinkedList<>();

        // Add each character to the linked list
        for (char c : input.toCharArray()) {
            list.add(c);
        }

        // Flag to track palindrome state
        boolean isPalindrome = true;

        // Compare until only one or zero elements remain
        while (list.size() > 1) {
            // Remove from both ends and compare
            Character first = list.removeFirst();
            Character last = list.removeLast();

            if (!first.equals(last)) {
                isPalindrome = false;
                break;
            }
        }

        // Output the result
        System.out.println("Is Palindrome? : " + isPalindrome);
    }
}