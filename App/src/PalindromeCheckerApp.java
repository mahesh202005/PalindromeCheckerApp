import java.util.*;

/**
 * MAIN CLASS - UseCase13PalindromeCheckerApp
 *
 * Use Case 13: Performance Comparison of Palindrome Algorithms
 *
 * Description:
 * This class measures and compares the execution performance of
 * different palindrome validation algorithms.
 *
 * At this stage, the application:
 * - Implements multiple palindrome checking strategies
 * - Captures execution start and end time using System.nanoTime()
 * - Calculates total execution duration
 * - Displays benchmarking results for comparison
 *
 * This use case focuses purely on performance measurement
 * and algorithm comparison.
 * The goal is to introduce benchmarking concepts.
 *
 * @author Developer
 * @version 13.0
 */
public class PalindromeCheckerApp {

    /**
     * Interface defining the contract for all palindrome checking algorithms
     */
    interface PalindromeStrategy {
        boolean check(String input);
        String getStrategyName();
    }

    /**
     * Strategy 1: Two-Pointer Technique
     * Most efficient - O(n/2) comparisons
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
            return "Two-Pointer Technique";
        }
    }

    /**
     * Strategy 2: StringBuilder Reverse
     * Uses built-in reverse method
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
            return "StringBuilder Reverse";
        }
    }

    /**
     * Strategy 3: Stack-based Approach
     * Uses Stack data structure (LIFO)
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
            return "Stack-based Approach";
        }
    }

    /**
     * Strategy 4: Deque-based Approach
     * Uses Deque data structure (double-ended queue)
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
            return "Deque-based Approach";
        }
    }

    /**
     * Strategy 5: Recursive Approach
     * Uses recursion to check palindrome
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
            return "Recursive Approach";
        }
    }

    /**
     * Strategy 6: Character Array Comparison
     * Converts string to char array and compares
     */
    static class CharArrayStrategy implements PalindromeStrategy {

        @Override
        public boolean check(String input) {
            if (input == null || input.isEmpty()) {
                return true;
            }

            char[] chars = input.toCharArray();
            int length = chars.length;

            for (int i = 0; i < length / 2; i++) {
                if (chars[i] != chars[length - 1 - i]) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String getStrategyName() {
            return "Character Array Comparison";
        }
    }

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
            return String.format("%-30s | Input: %-20s | Is Palindrome? : %-5b | Execution Time : %d ns",
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
                    new TwoPointerStrategy(),
                    new StringBuilderStrategy(),
                    new StackStrategy(),
                    new DequeStrategy(),
                    new RecursiveStrategy(),
                    new CharArrayStrategy()
            );
        }

        /**
         * Runs performance test for a single input string
         * @param input The string to test
         * @return List of performance results for all strategies
         */
        public List<PerformanceResult> runBenchmark(String input) {
            List<PerformanceResult> results = new ArrayList<>();

            System.out.println("\n" + "=".repeat(100));
            System.out.println("Benchmarking for input: \"" + input + "\"");
            System.out.println("=".repeat(100));

            // Warm-up phase to stabilize JVM
            for (PalindromeStrategy strategy : strategies) {
                strategy.check(input);
            }

            // Actual benchmarking
            for (PalindromeStrategy strategy : strategies) {
                // Start time measurement
                long startTime = System.nanoTime();

                // Execute the algorithm
                boolean result = strategy.check(input);

                // End time measurement
                long endTime = System.nanoTime();
                long executionTime = endTime - startTime;

                // Store result
                results.add(new PerformanceResult(
                        strategy.getStrategyName(),
                        result,
                        executionTime,
                        input
                ));
            }

            return results;
        }

        /**
         * Runs performance test for multiple input strings
         * @param inputs Array of strings to test
         */
        public void runComprehensiveBenchmark(String[] inputs) {
            Map<String, List<PerformanceResult>> allResults = new LinkedHashMap<>();

            for (String input : inputs) {
                allResults.put(input, runBenchmark(input));
            }

            // Display summary statistics
            displaySummaryStatistics(allResults);
        }

        private void displaySummaryStatistics(Map<String, List<PerformanceResult>> allResults) {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("SUMMARY STATISTICS (Average Execution Times)");
            System.out.println("=".repeat(100));

            // Calculate averages for each strategy
            Map<String, List<Long>> strategyTimes = new HashMap<>();

            for (List<PerformanceResult> results : allResults.values()) {
                for (PerformanceResult result : results) {
                    strategyTimes.computeIfAbsent(result.strategyName, k -> new ArrayList<>())
                            .add(result.executionTimeNs);
                }
            }

            // Display averages
            System.out.printf("%-30s | %-20s%n", "Strategy", "Average Time (ns)");
            System.out.println("-".repeat(55));

            for (Map.Entry<String, List<Long>> entry : strategyTimes.entrySet()) {
                double average = entry.getValue().stream()
                        .mapToLong(Long::longValue)
                        .average()
                        .orElse(0);
                System.out.printf("%-30s | %,.0f ns%n", entry.getKey(), average);
            }
        }
    }

    /**
     * Application entry point for UC13.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("UC13: Palindrome Algorithm Performance Comparison");
        System.out.println("==========================================\n");

        PalindromeBenchmark benchmark = new PalindromeBenchmark();
        Scanner scanner = new Scanner(System.in);

        // Test with the specific case from the example
        System.out.println("Testing with example input: \"level\"");
        List<PerformanceResult> exampleResults = benchmark.runBenchmark("level");

        // Display results for the example
        System.out.println("\n" + "-".repeat(100));
        System.out.println("PERFORMANCE RESULTS");
        System.out.println("-".repeat(100));

        // Find and display the fastest strategy for this input
        PerformanceResult fastest = Collections.min(exampleResults,
                Comparator.comparingLong(r -> r.executionTimeNs));

        for (PerformanceResult result : exampleResults) {
            System.out.println(result);
        }

        System.out.println("\n" + "=".repeat(100));
        System.out.printf("Fastest Strategy: %s (%d ns)%n",
                fastest.strategyName, fastest.executionTimeNs);
        System.out.println("=".repeat(100));

        // Interactive mode for custom testing
        System.out.println("\n" + "=".repeat(100));
        System.out.println("INTERACTIVE BENCHMARKING MODE");
        System.out.println("Enter strings to benchmark (type 'exit' to quit)");
        System.out.println("=".repeat(100));

        while (true) {
            System.out.print("\nEnter a string to benchmark: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (input.isEmpty()) {
                System.out.println("Please enter a non-empty string.");
                continue;
            }

            // Run benchmark for the input
            List<PerformanceResult> results = benchmark.runBenchmark(input);

            // Display results sorted by execution time
            results.sort(Comparator.comparingLong(r -> r.executionTimeNs));

            System.out.println("\n" + "-".repeat(100));
            System.out.println("RESULTS (Sorted by Performance)");
            System.out.println("-".repeat(100));

            for (PerformanceResult result : results) {
                System.out.println(result);
            }

            // Show performance ratios
            System.out.println("\n" + "-".repeat(100));
            System.out.println("PERFORMANCE COMPARISON");
            System.out.println("-".repeat(100));

            long baseline = results.get(0).executionTimeNs; // Fastest time
            for (int i = 1; i < results.size(); i++) {
                PerformanceResult result = results.get(i);
                double ratio = (double) result.executionTimeNs / baseline;
                System.out.printf("%s is %.2fx slower than %s%n",
                        result.strategyName,
                        ratio,
                        results.get(0).strategyName);
            }
        }

        // Run comprehensive benchmark with multiple test cases
        System.out.println("\n" + "=".repeat(100));
        System.out.println("COMPREHENSIVE BENCHMARK WITH MULTIPLE TEST CASES");
        System.out.println("=".repeat(100));

        String[] testInputs = {
                "a",                    // Single character
                "ab",                   // Two characters, not palindrome
                "aa",                   // Two characters, palindrome
                "level",                // 5 characters, palindrome
                "hello",                // 5 characters, not palindrome
                "racecar",              // 7 characters, palindrome
                "abcdefghijklmnopqrstuvwxyz", // Long non-palindrome
                "amanaplanacanalpanama" // Long palindrome (without spaces)
        };

        benchmark.runComprehensiveBenchmark(testInputs);

        scanner.close();
        System.out.println("\nBenchmarking completed. Program terminated.");
    }
}