/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.io.*;   // Needed for keyboard inputs

/* In this program, I demonstrated the concepts of:
   - Using methods of the Java "Math" class (Math.pow())
   - Arrays (which are returned by functions that need to return more than one variable)
   - Recursion (used by the evaluateExpression function, which repeatedly simplifies the mathematical expression and calls itself to solve the simplified expression)
   - Using methods of the Java "String" class (.substring(), .length(), .charAt, .indexOf(), .replaceAll(), .toLowerCase())
*/

/**
 *
 * @author Yiru Liu
 */
public class Calculator {
    
    public static String replaceInString(String original, int start, int end, String toBeReplacedIn) {  // Function to replace a part of a string between two indexes with another string
        return original.substring(0, start) + toBeReplacedIn + original.substring(end, original.length());
    }
    
    public static int[] whichOpFirst(char op1, char op2, String expression) {   // A function that, given two operations that are to be done from left to right and an expression, determines which one should be done first (if either is present), and its index.
        int exprLength = expression.length();   // The length of the expression
        char currentChar; // The current character the loop is traversing through
        
        int whichOp = -1; // The operation that is to be done first
        int opIndex = -1; // The index of the operation
        
        for(int i = 0; i < exprLength; i++) {   // Traverse through the expression:
            currentChar = expression.charAt(i); // Get the current character
            if(currentChar == op1) {    // If it is the first operation:
                // The first operation is to be done first:
                whichOp = 1;
                opIndex = i;
                break;  // No need to continue traversing through the expression
            } else if(currentChar == op2) {     // If it is the second operation:
                // The second operation is to be done first:
                whichOp = 2;
                opIndex = i;
                break;  // No need to continue traversing through the expression
            }
        }
        
        // Put variables to return into an array and return it:
        int[] returnArray = {whichOp, opIndex};
        return returnArray;
    }
    
    public static double[] getNumbersAround(String expression, int index) { // A function that, given an expression and the index of an operator, returns the two numbers around that operator, as well as their indexes, in a double array.
        int exprLength = expression.length();   // The length of the expression
        char currentChar; // The current character the loops is traversing through
        int beforeNumIndex = 0;    // The index of the start of the number before the operator (default to the first index in the array)
        int afterNumIndex = exprLength;     // The index of the end of the number after the operator (default to the last index in the array)
        
        // Find beforeNumIndex:
        for(int i = index-1; i >= 0; i--) {   // Traverse from right before the index of the operator in the direction to the start of the expression to find the number before the operator:
            currentChar = expression.charAt(i); // Get the current character
            if(!((currentChar <= '9' && currentChar >= '0') || currentChar == '.' || currentChar == '-')) {  // If the current character is not a number or a decimal point or a negative sign:
                beforeNumIndex = i+1;   // Then the number before the operator begins right after the current character.
                break; // There is no need to continue traversing through the expression, so break.
            }
        }
        
        // Find afterNumIndex:
        for(int i = index+1; i < exprLength; i++) {   // Traverse from right after the index of the operator in the direction to the end of the expression to find the number after the operator:
            currentChar = expression.charAt(i); // Get the current character
            if(!((currentChar <= '9' && currentChar >= '0') || currentChar == '.' || currentChar == '-')) {  // If the current character is not a number or a decimal point or a negative sign:
                afterNumIndex = i;   // Then the number after the operator ends right at the current character.
                break; // There is no need to continue traversing through the expression, so break.
            }
        }
        
        String beforeNumString = expression.substring(beforeNumIndex, index); // The number before the operator as a string
        double beforeNumDouble = new Double(beforeNumString);   // The number before the operator as a double
        String afterNumString = expression.substring(index+1, afterNumIndex); // The number after the operator as a string
        double afterNumDouble = new Double(afterNumString); // The number after the operator as a double
        double[] numsArray = {beforeNumDouble, afterNumDouble, beforeNumIndex, afterNumIndex};  // Put all the information we need to return in an array.
        return numsArray;   // Return the array.
    }
    
    public static boolean checkExpressionValid(String expression) { // A function to check if the expression the user entered is invalid but wouldn't cause evaluateExpression to throw an error (false if this is the case, true if not).
        int exprLength = expression.length();   // The length of the expression
        char currentChar; // The current character the loops is traversing through
        
        for(int i = 0; i < exprLength; i++) {   // Traverse through the expression:
            currentChar = expression.charAt(i); // Get the current character
            if(currentChar == '(') {    // If the current character is an open parenthesis:
                if(i > 0) { // If this parenthesis is not the first character in the expression, meaning that there would be a character before this parenthesis:
                    char beforeParen = expression.charAt(i-1); // Get the character before the parenthesis
                    if((beforeParen <= '9' && beforeParen >= '0') || beforeParen == '.') { // If the character before the parenthesis is a number or a decimal point:
                        return false;   // Then the expression is invalid, so return false.
                    }
                }
            } else if(currentChar == ')') { // If the current character is a closing parenthesis:
                if(i < exprLength-1) {    // If this parenthesis is not the last character in the expression, meaning that there would be a character after this parenthesis:
                    char afterParen = expression.charAt(i+1); // Get the character after the parenthesis
                    if((afterParen <= '9' && afterParen >= '0') || afterParen == '.') { // If the character after the parenthesis is a number or a decimal point:
                        return false;   // Then the expression is invalid, so return false.
                    }
                }
            } else if(currentChar == 'p') { // If the current character is a 'p' (as in "pi"):
                if(i > 0) { // If this 'p' is not the first character in the expression, meaning that there would be a character before this "pi":
                    char beforePi = expression.charAt(i-1); // Get the character before the "pi"
                    if((beforePi <= '9' && beforePi >= '0') || beforePi == '.') { // If the character before the "pi" is a number or a decimal point:
                        return false;   // Then the expression is invalid, so return false.
                    }
                }
                if(i < exprLength-2) { // If this 'p' is not the last 2 characters in the expression, meaning that there would be a character after this "pi":
                    char afterPi = expression.charAt(i+2);  // Get the character after the "pi", which would be 2 characters after the 'p' in "pi"
                    if((afterPi <= '9' && afterPi >= '0') || afterPi == '.') { // If the character before the "pi" is a number or a decimal point:
                        return false;   // Then the expression is invalid, so return false.
                    }
                }
            } else if(currentChar == 'e') { // If the current character is e:
                if(i > 0) { // If this e is not the first character in the expression, meaning that there would be a character before this e:
                    char beforeE = expression.charAt(i-1); // Get the character before the e
                    if((beforeE <= '9' && beforeE >= '0') || beforeE == '.') { // If the character before the e is a number or a decimal point:
                        return false;   // Then the expression is invalid, so return false.
                    }
                }
                if(i < exprLength-1) { // If this e is not the last character in the expression, meaning that there would be a character after this e:
                    char afterE = expression.charAt(i+1);  // Get the character after the e
                    if((afterE <= '9' && afterE >= '0') || afterE == '.') { // If the character before the e is a number or a decimal point:
                        return false;   // Then the expression is invalid, so return false.
                    }
                }
            }
        }
        
        return true; // If nothing invalid was encountered when traversing through the expression, then the expression would either be valid or would cause evaluateExpression to throw an error (in which case we will know that it is invalid at that point), so return true.
    }
    
    public static double evaluateExpression(String expression) throws Exception {    // The function that evaluates the mathematical expression and returns the result of the expression (the first double)
        try {   // See if the expression is already just a number:
            return new Double(expression);
        } catch(NumberFormatException e) {  // If not, then evaluate it:
            int exprLength = expression.length();   // The length of the expression

            // Handle parentheses:
            int openParenIndex = expression.indexOf('(');   // Find the index of the first open parenthesis (if there is one):
            if(openParenIndex != -1) {  // If there exists an open parenthesis:
                // Find the index of the matching closing parenthesis:
                int numUnclosedParens = 0;  // When traversing the array, an open parenthesis adds one and a closing parenthesis subtracts one. This means that when this is equal to zero, then we would have found the matching closing parenthesis.
                char currentChar;   // The current character the loop is traversing through
                int closeParenIndex = -1;// To store the index of the matching closing parenthesis found
                for(int i = openParenIndex; i < exprLength; i++) {  // Traverse from the index of the open parenthesis to the end of the expression:
                    currentChar = expression.charAt(i); // Get the current character

                    if(currentChar == '(') {    // If it's an open parenthesis:
                        numUnclosedParens++;    // Increment the number of unclosed parentheses
                    } else if(currentChar == ')') { // If it's a closing parenthesis:
                        numUnclosedParens--;    // Decrement the number of unclosed parentheses
                    }

                    if(numUnclosedParens <= 0) { // If all parentheses have been closed:
                        closeParenIndex = i;    // Then the current character is the matching closing parenthesis.
                        break;  // Since the closing parenthesis has been found, there is no need to continue traversing the expression, so break.
                    }
                }
                String inParensExpression = expression.substring(openParenIndex+1, closeParenIndex);    // Get the expression that is contained inside the parentheses
                //System.out.println(inParensExpression);
                double inParensValue = evaluateExpression(inParensExpression);  // Use recursion to get the value of the expression that is contained inside the parentheses
                String inParensValueString = Double.toString(inParensValue);    // Convert it to a string so it can be put into the expression
                //System.out.println(inParensValueString);
                String simplifiedExpression = replaceInString(expression, openParenIndex, closeParenIndex+1, inParensValueString);  // Create a new, simplified expression where the expression inside the parentheses is replaced with its value
                //System.out.println(simplifiedExpression);
                return evaluateExpression(simplifiedExpression);    // Use recursion to get the value of the simplified expression
            }

            // Handle the operators following order of operations:

            // Handle exponentiation first:
            int expIndex = expression.indexOf('^');    // Find exponentiation in the equation
            if(expIndex != -1) {   // If there is exponentiation in the equation:
                double[] nums = getNumbersAround(expression, expIndex);    // Get the numbers around the exponentiation

                // Get the numbers from the array returned by the function:
                double beforeNum = nums[0];
                double afterNum = nums[1];
                int beforeNumIndex = (int)nums[2];
                int afterNumIndex = (int)nums[3];

                double exponentiationResult = Math.pow(beforeNum, afterNum);    // Calculate the result of the exponentiation

                if(beforeNumIndex == 0 && afterNumIndex == exprLength) {    // If this exponentiation was the only thing in the expression:
                    return exponentiationResult;
                } else {    // If there are more to be calculated in the expression:
                    String simplifiedExpression = replaceInString(expression, beforeNumIndex, afterNumIndex, Double.toString(exponentiationResult));  // Create a new, simplified expression where the exponentiation is replaced with its value
                    return evaluateExpression(simplifiedExpression);    // Use recursion to get the value of the simplified expression
                }
            }
            if(expIndex != -1) {   // If there is exponentiation in the equation:
                double[] nums = getNumbersAround(expression, expIndex);    // Get the numbers around the exponentiation

                // Get the numbers from the array returned by the function:
                double beforeNum = nums[0];
                double afterNum = nums[1];
                int beforeNumIndex = (int)nums[2];
                int afterNumIndex = (int)nums[3];

                double exponentiationResult = Math.pow(beforeNum, afterNum);    // Calculate the result of the exponentiation

                if(beforeNumIndex == 0 && afterNumIndex == exprLength) {    // If this exponentiation was the only thing in the expression:
                    return exponentiationResult;    // Just return the result
                } else {    // If there are more to be calculated in the expression:
                    String simplifiedExpression = replaceInString(expression, beforeNumIndex, afterNumIndex, Double.toString(exponentiationResult));  // Create a new, simplified expression where the exponentiation is replaced with its value
                    return evaluateExpression(simplifiedExpression);    // Use recursion to get the value of the simplified expression
                }
            }

            // Next, handle multiplication and division:
            // Since multiplication and division is done from left to right, we need to see which one to do first:
            int[] opFirstMD = whichOpFirst('*', '/', expression);
            int whichOpMD = opFirstMD[0];
            int opIndexMD = opFirstMD[1];
            if(whichOpMD != -1) { // If multiplication or division was found:
                double[] nums = getNumbersAround(expression, opIndexMD);    // Get the numbers around the operation

                // Get the numbers from the array returned by the function:
                double beforeNum = nums[0];
                double afterNum = nums[1];
                int beforeNumIndex = (int)nums[2];
                int afterNumIndex = (int)nums[3];

                double result = -0.0;   // The product or quotient of the multiplication or division
                if(whichOpMD == 1) {    // If multiplication is to be done:
                    result = beforeNum * afterNum;  // Calculate the result of the multiplication
                } else if(whichOpMD == 2) { // If division is to be done:
                    result = beforeNum / afterNum;  // Calculate the result of the division
                }

                if(beforeNumIndex == 0 && afterNumIndex == exprLength) {    // If this multiplication or division was the only thing in the expression:
                    return result;  // Just return the result
                } else {    // If there are more to be calculated in the expression:
                    String simplifiedExpression = replaceInString(expression, beforeNumIndex, afterNumIndex, Double.toString(result));  // Create a new, simplified expression where the multiplication or division is replaced with its value
                    return evaluateExpression(simplifiedExpression);    // Use recursion to get the value of the simplified expression
                }
            }

            // Next, handle addition and subtraction:
            // Since addition and subtraction is done from left to right, we need to see which one to do first:
            int[] opFirstAS = whichOpFirst('+', '~', expression);
            int whichOpAS = opFirstAS[0];
            int opIndexAS = opFirstAS[1];
            if(whichOpAS != -1) { // If addition or subtraction was found:
                double[] nums = getNumbersAround(expression, opIndexAS);    // Get the numbers around the operation

                // Get the numbers from the array returned by the function:
                double beforeNum = nums[0];
                double afterNum = nums[1];
                int beforeNumIndex = (int)nums[2];
                int afterNumIndex = (int)nums[3];

                double result = -0.0;   // The product or quotient of the addition or subtraction
                if(whichOpAS == 1) {    // If addition is to be done:
                    result = beforeNum + afterNum;  // Calculate the result of the addition
                } else if(whichOpAS == 2) { // If subtraction is to be done:
                    result = beforeNum - afterNum;  // Calculate the result of the subtraction
                }

                if(beforeNumIndex == 0 && afterNumIndex == exprLength) {    // If this addition or subtraction was the only thing in the expression:
                    return result;  // Just return the result
                } else {    // If there are more to be calculated in the expression:
                    String simplifiedExpression = replaceInString(expression, beforeNumIndex, afterNumIndex, Double.toString(result));  // Create a new, simplified expression where the addition or subtraction is replaced with its value
                    return evaluateExpression(simplifiedExpression);    // Use recursion to get the value of the simplified expression
                }
            }
        }
        
        // This function should have returned something by this point.
        // If it didn't, then the expression contained invalid syntax, so we need to throw an exception:
        throw new Exception("Invalid expression.");
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        // Set up keyboard input:
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader keyboard = new BufferedReader(in);
        
        // Print some information about the program:
        System.out.println("This program is a calculator that can evaluate mathematical expressions. ");
        System.out.println("Supported operations are addition +, subtraction -, multiplication *, division /, and exponentiation ^. You can use parentheses ().");
        System.out.println("Please use an underscore _ to represent a negative sign.");
        System.out.println("Note: Please write out all multiplication using *.");
        System.out.println("Mathematical constants you can use are pi and e.");
        
        while(true) {   // Loop until the user enters "exit":
            // Prompt and get the mathematical expression from the user:
            System.out.println("Please enter a mathematical expression, or type \"exit\" to exit: ");
            String expression = keyboard.readLine();
            
            if(expression.equals("exit")) { // If the user entered "exit":
                break;  // Break the while(true) loop and end the program.
            }
            
            // Process the user input to get it ready to be passed into evaluateExpression:
            expression = expression.replaceAll(" ", ""); // The user may have included spaces in the expression; remove them by replacing all spaces " " with nothing "".
            expression = expression.toLowerCase();  // The user may have entered the constants in uppercase (e.g. "PI", "E"); convert them to lowercase.
            if(checkExpressionValid(expression)) {  // If the expression is valid or would cause evaluateExpression to throw an error, then continue:
                expression = expression.replaceAll("pi", Double.toString(Math.PI)); // Replace where the user entered "pi" with the numerical value of pi (it needs to be converted into a string in order to be replaced into the expression.
                expression = expression.replaceAll("e", Double.toString(Math.E));   // Replace where the user entered "e" with the numerical value of e (it needs to be converted into a string in order to be replaced into the expression.
                expression = expression.replaceAll("-", "~");   // We had the user enter '-' to represent subtraction. In evaluateExpression, we use '~' to represent subtraction since '-' is used to represent negative numbers.
                expression = expression.replaceAll("\\^_", "\\^-"); // When we replace the user's negative sign '_' with multiplied by -1, this would not work correctly if it comes after an exponentiation (for example, 10^-2 -> 10^-1*2 is not correct), so just replace it with a negative sign '-'. We need to put two backslashes before '^' because '^' is a regular expression character, but we want to just treat it like a normal character.
                expression = expression.replaceAll("_", "-1*"); // We had the user enter '_' to represent a negative sign. We need to make this into being multiplied by -1 (so expressions like -8^2 will be evaluated correctly to -64.0 and not 64.0).

                try {   // Surround this part in a try because it may throw an exception if the expression was invalid.
                    double result = evaluateExpression(expression); // Use evaluateExpression to get the expression.
                    if(Double.isFinite(result)) {   // If the result was an actual number (not NaN or an infinity):
                        // Print out the result:
                        System.out.print("The result is: ");
                        System.out.println(result);
                    } else {    // If the result was not a finite number:
                        System.out.println("Sorry, you entered an invalid expression.");    // Tell the user that the expression entered was invalid.
                    }
                } catch(Exception e) {  // If an exception is thrown, then the user entered an invalid expression.
                    System.out.println("Sorry, you entered an invalid expression.");    // Tell the user that the expression entered was invalid.
                }
            } else {    // If the expression was invalid (but wouldn't cause evaluateExpression to throw an error):
                System.out.println("Sorry, you entered an invalid expression.");    // Tell the user that the expression entered was invalid.
            }
            System.out.println();   // Print a blank line to separate different expressions and their results.
        }
    }
    
}