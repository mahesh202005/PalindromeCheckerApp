package App.src;

import java.util.*;

/**
 * MAIN CLASS - PalindromeCheckerApp
 * 
 * Use Case 12: Strategy Pattern for Palindrome Algorithms
 * 
 * Description:
 * This class demonstrates how different palindrome validation algorithms 
 * can be selected dynamically at runtime using the Strategy Design Pattern.
 * 
 * @author mahesh202005
 * @version 12.0
 */
public class PalindromeCheckerApp {
    
    /**
     * INTERFACE - PalindromeStrategy
     * Defines contract for all palindrome checking algorithms
     */
    public interface PalindromeStrategy {
        boolean check(String input);
    }
    
    /**
     * StackStrategy - Implements palindrome validation using Stack
     */
    static class StackStrategy implements PalindromeStrategy {
        
        @Override
        public boolean check(String input) {
            if (input == null || input.isEmpty()) {
                return true;
            }
            
            java.util.Stack<Character> stack = new java.util.Stack<>();
            
            // Push all characters to stack
            for (char c : input.toCharArray()) {
                stack.push(c);
            }
            
            // Compare with original string
            for (char c : input.toCharArray()) {
                if (c != stack.pop()) {
                    return false;
                }
            }
            
            return true;
        }
    }
    
    /**
     * DequeStrategy - Implements palindrome validation using Deque
     */
    static class DequeStrategy implements PalindromeStrategy {
        
        @Override
        public boolean check(String input) {
            if (input == null || input.isEmpty()) {
                return true;
            }
            
            java.util.Deque<Character> deque = new java.util.ArrayDeque<>();
            
            // Add all characters to deque
            for (char c : input.toCharArray()) {
                deque.add(c);
            }
            
            // Compare from both ends
            while (deque.size() > 1) {
                if (deque.removeFirst() != deque.removeLast()) {
                    return false;
                }
            }
            
            return true;
        }
    }
    
    /**
     * Context class that uses a PalindromeStrategy
     */
    static class PalindromeChecker {
        private PalindromeStrategy strategy;
        
        public PalindromeChecker() {
            this.strategy = new StackStrategy(); // Default strategy
        }
        
        public PalindromeChecker(PalindromeStrategy strategy) {
            this.strategy = strategy;
        }
        
        public void setStrategy(PalindromeStrategy strategy) {
            this.strategy = strategy;
        }
        
        public boolean checkPalindrome(String input) {
            return strategy.check(input);
        }
        
        public String getStrategyName() {
            return strategy.getClass().getSimpleName();
        }
    }
    
    /**
     * Main method - Entry point
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Palindrome Checker App - UC12");
        System.out.println("Strategy Pattern for Palindrome Algorithms");
        System.out.println("========================================\n");
        
        PalindromeChecker checker = new PalindromeChecker();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("\nEnter a string to check (or 'exit' to quit): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            
            // Choose strategy
            System.out.println("Select strategy:");
            System.out.println("1. Stack Strategy");
            System.out.println("2. Deque Strategy");
            System.out.print("Enter choice (1 or 2): ");
            
            String choice = scanner.nextLine().trim();
            
            if (choice.equals("1")) {
                checker.setStrategy(new StackStrategy());
            } else if (choice.equals("2")) {
                checker.setStrategy(new DequeStrategy());
            } else {
                System.out.println("Invalid choice. Using default strategy.");
            }
            
            boolean result = checker.checkPalindrome(input);
            System.out.println("Input: \"" + input + "\"");
            System.out.println("Strategy: " + checker.getStrategyName());
            System.out.println("Is Palindrome?: " + result);
        }
        
        scanner.close();
        System.out.println("Program terminated.");
    }
}