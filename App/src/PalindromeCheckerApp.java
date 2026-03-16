public class PalindromeCheckerApp {

    public static void main(String[] args) {
        String input = "racecar"; // You can change this to test

        if (isPalindrome(input)) {
            System.out.println(input + " is a palindrome.");
        } else {
            System.out.println(input + " is not a palindrome.");
        }
    }

    public static boolean isPalindrome(String str) {
        // Step 1: Convert the string to a character array
        char[] charArray = str.toCharArray();

        int left = 0;
        int right = charArray.length - 1;

        // Step 2: Compare characters from both ends moving inward
        while (left < right) {
            if (charArray[left] != charArray[right]) {
                return false; // Characters don't match
            }
            left++;
            right--;
        }
        return true; // All characters matched
    }
}