import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PalindromeCheckerApp {
    public static void main(String[] args) {
        String input = "civic"; // Test input

        System.out.println("Input : " + input);
        boolean isPalindrome = checkPalindrome(input);
        System.out.println("Is Palindrome? : " + isPalindrome);
    }

    public static boolean checkPalindrome(String input) {
        Queue<Character> queue = new LinkedList<>();
        Stack<Character> stack = new Stack<>();

        // 1. Insert each character into both queue and stack
        for (char c : input.toCharArray()) {
            queue.add(c);  // Enqueue (FIFO)
            stack.push(c); // Push (LIFO)
        }

        // 2. Compare characters until the queue becomes empty
        while (!queue.isEmpty()) {
            // dequeue() vs pop()
            if (!queue.remove().equals(stack.pop())) {
                return false; // Mismatch found
            }
        }

        return true; // All matched
    }
}