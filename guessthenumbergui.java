import javax.swing.JOptionPane;
import java.util.Random;

public class GuessTheNumberGUI {

    public static void main(String[] args) {
        final int MIN = 1;
        final int MAX = 100;
        final int MAX_ATTEMPTS = 7;
        final int ROUNDS = 3;

        Random rand = new Random();
        int totalScore = 0;

        JOptionPane.showMessageDialog(null, "Welcome to Guess the Number!\nRange: " + MIN + " to " + MAX +
                "\nAttempts per round: " + MAX_ATTEMPTS + "\nTotal rounds: " + ROUNDS);

        for (int round = 1; round <= ROUNDS; round++) {
            int target = rand.nextInt(MAX - MIN + 1) + MIN;
            int attemptsLeft = MAX_ATTEMPTS;
            boolean roundWon = false;

            while (attemptsLeft > 0) {
                String input = JOptionPane.showInputDialog("Round " + round + "\nAttempts left: " + attemptsLeft +
                        "\nEnter your guess (" + MIN + "-" + MAX + "):");

                if (input == null) { // User clicked Cancel
                    JOptionPane.showMessageDialog(null, "Game cancelled!");
                    System.exit(0);
                }

                int guess;
                try {
                    guess = Integer.parseInt(input.trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Enter a number.");
                    continue; // do not consume attempt
                }

                if (guess < MIN || guess > MAX) {
                    JOptionPane.showMessageDialog(null, "Out of range! Guess between " + MIN + " and " + MAX);
                    continue; // do not consume attempt
                }

                attemptsLeft--; // valid guess consumes attempt

                if (guess == target) {
                    int attemptsUsed = MAX_ATTEMPTS - attemptsLeft;
                    int roundScore = calculateScore(attemptsUsed, MAX_ATTEMPTS);
                    totalScore += roundScore;
                    JOptionPane.showMessageDialog(null, "✔ Correct! You guessed in " + attemptsUsed + " attempts." +
                            "\nRound " + round + " score: " + roundScore);
                    roundWon = true;
                    break;
                } else if (guess < target) {
                    JOptionPane.showMessageDialog(null, "Too low!");
                } else {
                    JOptionPane.showMessageDialog(null, "Too high!");
                }
            }

            if (!roundWon) {
                JOptionPane.showMessageDialog(null, "✘ Round over. The number was: " + target);
            }
        }

        JOptionPane.showMessageDialog(null, "=== Game Over ===\nTotal score: " + totalScore +
                "\nThanks for playing!");
    }

    private static int calculateScore(int attemptsUsed, int maxAttempts) {
        int maxPoints = 100;
        int minPoints = 20;
        if (attemptsUsed <= 1) return maxPoints;
        if (attemptsUsed >= maxAttempts) return minPoints;

        double step = (maxPoints - minPoints) * 1.0 / (maxAttempts - 1);
        int score = (int) Math.round(maxPoints - (attemptsUsed - 1) * step);
        return Math.max(minPoints, Math.min(maxPoints, score));
    }
}
