import java.util.*;
public class App {

    // This method removes all spaces from a string
    public static String removeSpaces(String str) {
        return str.replaceAll("\\s+", "");
    }
    
    /**
     * Converts an infix formula to reverse Polish notation (RPN).
     * 
     * @param formula the infix formula to be converted
     * @return the formula in reverse Polish notation
     */
    public static String toReversePolish(String formula) {
        // Create a stack to store operators
        Stack<Character> stack = new Stack<>();
        // Create a string builder to store the output
        StringBuilder output = new StringBuilder();

        // Iterate over the characters in the formula
        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);

            if (Character.isDigit(c)) {
                // Append the digit to the output
                output.append(c);
            } else if (isOperator(c)) {
                // Pop operators from the stack and append them to the output until an operator with lower precedence is encountered
                while (!stack.isEmpty() && stack.peek() != '(' && hasHigherPrecedence(stack.peek(), c)) {
                    output.append(stack.pop());
                }
                // Push the current operator onto the stack
                stack.push(c);
            } else if (c == '(') {
                // Push an opening parenthesis onto the stack
                stack.push(c);
            } else if (c == ')') {
                // Pop operators from the stack and append them to the output until an opening parenthesis is encountered
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                // Pop the opening parenthesis from the stack
                stack.pop();
            }
            if (c != ' '){
                // Print the current state of the stack and the output
                System.out.println("\tStep: " + (i+1) + " Character: " + c);
                System.out.println("\tStack: " + stack);
                System.out.println("\tOutput: " + output + "\n");
            }
        }

        // Pop any remaining operators from the stack and append them to the output
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }

        return output.toString();
    }

    // This method checks if a character is a supported operator
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // This method checks if an operator has higher precedence than another operator
    private static boolean hasHigherPrecedence(char op1, char op2) {
        if (op1 == '*' || op1 == '/') {
            return true;
        } else if (op1 == '+' || op1 == '-') {
            return op2 == '+' || op2 == '-';
        } else {
            return false;
        }
    }

    // This method adds spaces between characters in a string
    public static String addSpaces(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            result.append(str.charAt(i)).append(" ");
        }
        return result.toString().trim();
    }

    /**
     * Evaluates a mathematical expression in Reverse Polish Notation (RPN) format.
     * 
     * @param expression the expression to be evaluated
     * @return the result of the evaluation
     */
    public static double evaluate(String expression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            // Check if the token is a number
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            }
            // Check if the token is an addition operator
            else if (token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            }
            // Check if the token is a subtraction operator
            else if (token.equals("-")) {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(a - b);
            }
            // Check if the token is a multiplication operator
            else if (token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            }
            // Check if the token is a division operator
            else if (token.equals("/")) {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(a / b);
            }
            // Check if the token is an opening parenthesis
            else if (token.equals("(")) {
                //stack.push(Double.NaN);
            }
            // Check if the token is a closing parenthesis
            else if (token.equals(")")) {
                List<Double> values = new ArrayList<>();
                // Pop values from the stack until an opening parenthesis is encountered
                while (!stack.isEmpty() && !stack.peek().isNaN()) {
                    values.add(stack.pop());
                }
            }

            // Print the current state of the stack
            System.out.println("\tToken: " + token);
            System.out.println("\tStack: " + stack + "\n");
        }
        return stack.pop();
    }

    // This is the main method of the application
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a formula: ");
        String formula = scanner.nextLine();

        String formulaWithoutSpaces = removeSpaces(formula);

        // Convert the formula to RPN
        System.out.println("\nConverting to RPN...");
        String rpnFormula = toReversePolish(formulaWithoutSpaces);
        System.out.println("RPN Formula: " + rpnFormula);

        // Add spaces between characters in the RPN formula
        String spacedFormula = addSpaces(rpnFormula);

        // Evaluate the RPN formula and print the result
        System.out.println("Evaluating...\n");
        double result = evaluate(spacedFormula);
        System.out.println("Result: " + result);

        scanner.close();
    }
}