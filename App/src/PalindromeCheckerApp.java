package App.src;

import java.util.*;

/**
 * MAIN CLASS - PalindromeCheckerApp
 * 
 * Combined Use Cases:
 * UC12: Strategy Pattern for Palindrome Algorithms
 * UC13: Performance Comparison of Palindrome Algorithms
 * 
 * @author mahesh202005
 * @version 13.0
 */
public class PalindromeCheckerApp {
    
    // ==================== UC12: Strategy Pattern Interface ====================
    public interface PalindromeStrategy {
        boolean check(String input);
        String getStrategyName();
    }
    
    // ==================== UC12: Strategy Implementations ====================
    
    /**
     * StackStrategy - Implements palindrome validation using Stack
     */
    static class StackStrategy implements PalindromeStrategy {
        
        @Override
        public boolean check(String input) {
            if (input == null || input.isEmpty()) {
                return true;
            }
            
            Stack<Character> stack = new Stack<>();
            
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
        
        @Override
        public String getStrategyName() {
            return "Stack Strategy (UC12)";
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
            
            Deque<Character> deque = new ArrayDeque<>();
            
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
        
        @Override
        public String getStrategyName() {
            return "Deque Strategy (UC12)";
        }
    }
    
    // ==================== UC13: Additional Strategy Implementations ====================
    
    /**
     * TwoPointerStrategy - Most efficient O(n) approach
     */
    static class TwoPointerStrategy implements PalindromeStrategy {
        
        @Override
        public boolean check(String input) {
            if (input == null || input.isEmpty()) {
                return true;
            }
            
            int left = 0;
            int right = input.length() - 1;
            
            while (left < right) {
                if (input.charAt(left) != input.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
        
        @Override
        public String getStrategyName() {
            return "Two-Pointer Strategy (UC13)";
        }
    }
    
    /**
     * StringBuilderStrategy - Uses built-in reverse
     */
    static class StringBuilderStrategy implements PalindromeStrategy {
        
        @Override
        public boolean check(String input) {
            if (input == null || input.isEmpty()) {
                return true;
            }
            
            String reversed = new StringBuilder(input).reverse().toString();
            return input.equals(reversed);
        }
        
        @Override
        public String getStrategyName() {
            return "StringBuilder Strategy (UC13)";
        }
    }
    
    /**
     * RecursiveStrategy - Uses recursion
     */
    static class RecursiveStrategy implements PalindromeStrategy {
        
        @Override
        public boolean check(String input) {
            if (input == null || input.isEmpty()) {
                return true;
            }
            return checkRecursive(input, 0, input.length() - 1);
        }
        
        private boolean checkRecursive(String input, int left, int right) {
            if (left >= right) {
                return true;
            }
            if (input.charAt(left) != input.charAt(right)) {
                return false;
            }
            return checkRecursive(input, left + 1, right - 1);
        }
        
        @Override
        public String getStrategyName() {
            return "Recursive Strategy (UC13)";
        }
    }
    
    // ==================== UC12: Context Class ====================
    
    /**
     * Context class that uses a PalindromeStrategy
     */
    static class PalindromeChecker {
        private PalindromeStrategy strategy;
        
        public PalindromeChecker() {
            this.strategy = new TwoPointerStrategy(); // Default to efficient strategy
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
            return strategy.getStrategyName();
        }
    }
    
    // ==================== UC13: Performance Benchmarking ====================
    
    /**
     * Class to store performance test results
     */
    static class PerformanceResult {
        String strategyName;
        boolean result;
        long executionTimeNs;
        String input;
        
        PerformanceResult(String strategyName, boolean result, long executionTimeNs, String input) {
            this.strategyName = strategyName;
            this.result = result;
            this.executionTimeNs = executionTimeNs;
            this.input = input;
        }
        
        @Override
        public String toString() {
            return String.format("%-35s | Input: %-15s | Palindrome: %-5b | Time: %d ns",
                strategyName, "\"" + input + "\"", result, executionTimeNs);
        }
    }
    
    /**
     * Benchmark runner class
     */
    static class PalindromeBenchmark {
        private List<PalindromeStrategy> strategies;
        
        public PalindromeBenchmark() {
            strategies = Arrays.asList(
                new TwoPointerStrategy(),    // UC13
                new StringBuilderStrategy(), // UC13
                new StackStrategy(),         // UC12
                new DequeStrategy(),          // UC12
                new RecursiveStrategy()       // UC13
            );
        }
        
        /**
         * Runs performance test for a single input
         */
        public List<PerformanceResult> runBenchmark(String input) {
            List<PerformanceResult> results = new ArrayList<>();
            
            // Warm-up phase
            for (PalindromeStrategy strategy : strategies) {
                strategy.check(input);
            }
            
            // Actual benchmarking
            for (PalindromeStrategy strategy : strategies) {
                long startTime = System.nanoTime();
                boolean result = strategy.check(input);
                long endTime = System.nanoTime();
                
                results.add(new PerformanceResult(
                    strategy.getStrategyName(),
                    result,
                    endTime - startTime,
                    input
                ));
            }
            
            return results;
        }
    }
    
    // ==================== MAIN APPLICATION ====================
    
    /**
     * Main method - Entry point for combined UC12 and UC13
     */
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("PALINDROME CHECKER APP");
        System.out.println("UC12: Strategy Pattern + UC13: Performance Comparison");
        System.out.println("==========================================\n");
        
        PalindromeChecker checker = new PalindromeChecker();
        PalindromeBenchmark benchmark = new PalindromeBenchmark();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. UC12: Check palindrome with specific strategy");
            System.out.println("2. UC13: Compare performance of all strategies");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            
            String choice = scanner.nextLine().trim();
            
            if (choice.equals("3")) {
                break;
            }
            
            System.out.print("Enter string to check: ");
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Please enter a valid string.");
                continue;
            }
            
            switch (choice) {
                case "1":
                    // UC12: Strategy selection
                    System.out.println("\nSelect strategy:");
                    System.out.println("1. Stack Strategy");
                    System.out.println("2. Deque Strategy");
                    System.out.println("3. Two-Pointer Strategy");
                    System.out.println("4. StringBuilder Strategy");
                    System.out.println("5. Recursive Strategy");
                    System.out.print("Enter choice: ");
                    
                    String stratChoice = scanner.nextLine().trim();
                    
                    switch (stratChoice) {
                        case "1": checker.setStrategy(new StackStrategy()); break;
                        case "2": checker.setStrategy(new DequeStrategy()); break;
                        case "3": checker.setStrategy(new TwoPointerStrategy()); break;
                        case "4": checker.setStrategy(new StringBuilderStrategy()); break;
                        case "5": checker.setStrategy(new RecursiveStrategy()); break;
                        default: 
                            System.out.println("Invalid choice, using default");
                            checker.setStrategy(new TwoPointerStrategy());
                    }
                    
                    long startTime = System.nanoTime();
                    boolean result = checker.checkPalindrome(input);
                    long endTime = System.nanoTime();
                    
                    System.out.println("\n--- UC12 Result ---");
                    System.out.println("Input: \"" + input + "\"");
                    System.out.println("Strategy: " + checker.getStrategyName());
                    System.out.println("Is Palindrome? : " + result);
                    System.out.println("Execution Time : " + (endTime - startTime) + " ns");
                    break;
                    
                case "2":
                    // UC13: Performance comparison
                    System.out.println("\n--- UC13 Performance Comparison ---");
                    List<PerformanceResult> results = benchmark.runBenchmark(input);
                    
                    // Sort by execution time
                    results.sort(Comparator.comparingLong(r -> r.executionTimeNs));
                    
                    for (PerformanceResult pr : results) {
                        System.out.println(pr);
                    }
                    
                    // Show fastest
                    PerformanceResult fastest = results.get(0);
                    System.out.println("\n✓ Fastest: " + fastest.strategyName + 
                                     " (" + fastest.executionTimeNs + " ns)");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
        System.out.println("\nProgram terminated.");
    }
}