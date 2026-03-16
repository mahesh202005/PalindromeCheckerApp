import java.util.ArrayDeque;
import java.util.Deque;

public class PalindromeCheckerApp {
    public static void main(String[] args) {
        String input = "refer"; // Test input

        System.out.println("Input : " + input);
        boolean isPalindrome = checkPalindrome(input);
        System.out.println("Is Palindrome? : " + isPalindrome);
    }

    public static boolean checkPalindrome(String input) {
        // Use ArrayDeque for better performance
        Deque<Character> deque = new ArrayDeque<>();

        // 1. Add each character to the deque
        for (char c : input.toCharArray()) {
            deque.addLast(c);
        }

        // 2. Compare characters from both ends
        // While there is more than 1 character left to compare
        while (deque.size() > 1) {
            Character first = deque.removeFirst();
            Character last = deque.removeLast();

            if (!first.equals(last)) {
                return false; // Mismatch found
            }
        }

        return true; // All matched or only one middle char left
    }
}