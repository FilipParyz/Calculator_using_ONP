import java.util.*;
public class App {
    public static double evaluate(String expression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else if (token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (token.equals("-")) {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(a - b);
            } else if (token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (token.equals("/")) {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(a / b);
            } else if (token.equals("(")) {
                //stack.push(Double.NaN);
            } else if (token.equals(")")) {
                List<Double> values = new ArrayList<>();
                while (!stack.isEmpty() && !stack.peek().isNaN()) {
                    values.add(stack.pop());
                }
                stack.pop();
                Collections.reverse(values);
                double result = values.get(0);
                for (int i = 1; i < values.size(); i++) {
                    result = evaluate(values.get(i) + " " + result + " " + token);
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    public static String toReversePolish(String formula) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);

            if (Character.isDigit(c)) {
                output.append(c);
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && stack.peek() != '(' && hasHigherPrecedence(stack.peek(), c)) {
                    output.append(stack.pop());
                }
                stack.push(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                stack.pop();
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }

        return output.toString();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean hasHigherPrecedence(char op1, char op2) {
        if (op1 == '*' || op1 == '/') {
            return true;
        } else if (op1 == '+' || op1 == '-') {
            return op2 == '+' || op2 == '-';
        } else {
            return false;
        }
    }

    public static String addSpaces(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            result.append(str.charAt(i)).append(" ");
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a formula: ");
        String formula = scanner.nextLine();

        // Convert to ONP
        String expression = toReversePolish(formula);

        // Add spaces
        expression = addSpaces(expression);
        System.out.println(expression);

        // Evaluate using ONP
        double result = evaluate(expression);
        System.out.println(result);

        scanner.close();
    }
}