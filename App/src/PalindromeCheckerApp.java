import java.util.*;

/**
 * MAIN CLASS - UseCase12PalindromeCheckerApp
 *
 * Use Case 12: Strategy Pattern for Palindrome Algorithms
 *
 * Description:
 * This class demonstrates how different palindrome validation algorithms can be
 * selected dynamically at runtime using the Strategy Design Pattern.
 *
 * At this stage, the application:
 * - Defines a common PalindromeStrategy interface
 * - Implements concrete Stack and Deque based strategies
 * - Injects the strategy at runtime
 * - Executes the selected algorithm
 *
 * The focus is purely on algorithmic interchangeability.
 * The goal is to teach extensible algorithm design.
 *
 * @author Developer
 * @version 12.0
 */
public class PalindromeCheckerApp {

    /**
     * INTERFACE - PalindromeStrategy
     *
     * This interface defines a contract for all palindrome checking algorithms.
     * Any new algorithm must implement this interface and provide its own validation logic.
     */
    public interface PalindromeStrategy {
        boolean check(String input);
    }

    /**
     * StackStrategy - Implements palindrome validation using Stack data structure.
     *
     * Algorithm:
     * 1. Push all characters of the string onto a stack
     * 2. Pop characters from stack and compare with original string characters
     * 3. If all characters match, it's a palindrome
     */
    static class StackStrategy implements PalindromeStrategy {

        /**
         * Implement palindrome validation using Stack.
         *
         * @param input String to validate
         * @return true if palindrome, false otherwise
         */
        @Override
        public boolean check(String input) {
            // Handle null or empty input
            if (input == null || input.isEmpty()) {
                return true; // Empty string is considered palindrome
            }

            // Create a stack to store characters
            java.util.Stack<Character> stack = new java.util.Stack<>();

            // Push each character of the input string onto the stack
            for (char c : input.toCharArray()) {
                stack.push(c);
            }

            // Compare characters by popping from the stack
            // The stack follows LIFO, so popping gives characters in reverse order
            for (char c : input.toCharArray()) {
                if (c != stack.pop()) {
                    return false; // Mismatch found
                }
            }

            return true; // All characters matched
        }
    }

    /**
     * DequeStrategy - Implements palindrome validation using Deque data structure.
     *
     * Algorithm:
     * 1. Add all characters to a deque
     * 2. Remove and compare characters from both ends simultaneously
     * 3. If all pairs match, it's a palindrome
     */
    static class DequeStrategy implements PalindromeStrategy {

        /**
         * Implement palindrome validation using Deque.
         *
         * @param input String to validate
         * @return true if palindrome, false otherwise
         */
        @Override
        public boolean check(String input) {
            // Handle null or empty input
            if (input == null || input.isEmpty()) {
                return true; // Empty string is considered palindrome
            }

            // Create a deque to store characters
            java.util.Deque<Character> deque = new java.util.ArrayDeque<>();

            // Add each character to the deque
            for (char c : input.toCharArray()) {
                deque.add(c);
            }

            // Compare characters from both ends
            while (deque.size() > 1) {
                char first = deque.removeFirst();
                char last = deque.removeLast();

                if (first != last) {
                    return false; // Mismatch found
                }
            }

            return true; // All character pairs matched
        }
    }

    /**
     * Context class that uses a PalindromeStrategy
     */
    static class PalindromeChecker {
        private PalindromeStrategy strategy;

        /**
         * Constructor with default strategy
         */
        public PalindromeChecker() {
            this.strategy = new StackStrategy(); // Default strategy
        }

        /**
         * Constructor with specific strategy
         * @param strategy The palindrome checking strategy to use
         */
        public PalindromeChecker(PalindromeStrategy strategy) {
            this.strategy = strategy;
        }

        /**
         * Set a new strategy at runtime
         * @param strategy The new strategy to use
         */
        public void setStrategy(PalindromeStrategy strategy) {
            this.strategy = strategy;
        }

        /**
         * Check if a string is a palindrome using the current strategy
         * @param input String to validate
         * @return true if palindrome, false otherwise
         */
        public boolean checkPalindrome(String input) {
            return strategy.check(input);
        }

        /**
         * Get the name of the current strategy
         * @return Strategy class name
         */
        public String getStrategyName() {
            return strategy.getClass().getSimpleName();
        }
    }

    /**
     * Main method - Entry point of the application
     * Demonstrates dynamic strategy selection at runtime
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Palindrome Checker App - UC12");
        System.out.println("Strategy Pattern Demonstration");
        System.out.println("========================================\n");

        // Create a palindrome checker with default Stack strategy
        PalindromeChecker checker = new PalindromeChecker();

        // Test strings
        String[] testStrings = {
                "level",
                "radar",
                "hello",
                "madam",
                "java",
                "racecar",
                "12321",
                "A man a plan a canal Panama" // Note: This will fail as we don't handle spaces/case
        };

        // Test with StackStrategy (default)
        System.out.println("Testing with StackStrategy:");
        System.out.println("----------------------------");
        testWithStrategy(checker, testStrings);

        // Switch to DequeStrategy
        System.out.println("\nTesting with DequeStrategy:");
        System.out.println("----------------------------");
        checker.setStrategy(new DequeStrategy());
        testWithStrategy(checker, testStrings);

        // Interactive mode
        System.out.println("\n========================================");
        System.out.println("Interactive Mode - Enter strings to check");
        System.out.println("Type 'exit' to quit");
        System.out.println("========================================\n");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("\nEnter a string to check: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                running = false;
                System.out.println("Exiting...");
                break;
            }

            // Let user choose strategy
            System.out.println("Choose strategy:");
            System.out.println("1. Stack Strategy");
            System.out.println("2. Deque Strategy");
            System.out.print("Enter choice (1 or 2): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    checker.setStrategy(new StackStrategy());
                    break;
                case "2":
                    checker.setStrategy(new DequeStrategy());
                    break;
                default:
                    System.out.println("Invalid choice. Using current strategy: " + checker.getStrategyName());
            }

            boolean result = checker.checkPalindrome(input);
            System.out.println("Input: \"" + input + "\"");
            System.out.println("Strategy: " + checker.getStrategyName());
            System.out.println("Is Palindrome?: " + result);
        }

        scanner.close();
    }

    /**
     * Helper method to test strings with current strategy
     * @param checker PalindromeChecker instance
     * @param testStrings Array of strings to test
     */
    private static void testWithStrategy(PalindromeChecker checker, String[] testStrings) {
        for (String test : testStrings) {
            boolean result = checker.checkPalindrome(test);
            System.out.printf("Input: %-30s Is Palindrome?: %b%n", "\"" + test + "\"", result);
        }
    }
}