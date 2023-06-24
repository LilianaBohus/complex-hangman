import java.util.*;

public class Hangman {
    private String secretWord;
    private char[] guessedLetters;
    private int attemptsLeft;
    private Set<Character> incorrectGuesses;
    private Set<String> wordCategories;
    private Map<String, List<String>> wordDictionary;
    private String selectedCategory;

    public Hangman() {
        wordCategories = new HashSet<>();
        wordDictionary = new HashMap<>();
        incorrectGuesses = new HashSet<>();
        attemptsLeft = 6;
        initializeWordCategories();
        initializeWordDictionary();
    }

    private void initializeWordCategories() {
        wordCategories.add("Animals");
        wordCategories.add("Countries");
        wordCategories.add("Fruits");
    }

    private void initializeWordDictionary() {
        List<String> animalWords = Arrays.asList("lion", "elephant", "giraffe", "tiger", "zebra");
        wordDictionary.put("Animals", animalWords);

        List<String> countryWords = Arrays.asList("france", "germany", "canada", "brazil", "australia");
        wordDictionary.put("Countries", countryWords);

        List<String> fruitWords = Arrays.asList("apple", "banana", "orange", "kiwi", "mango");
        wordDictionary.put("Fruits", fruitWords);
    }

    public void play() {
        selectCategory();
        selectWord();
        while (true) {
            displayGameState();

            char guessedLetter = getPlayerGuess();
            boolean isCorrectGuess = updateGameState(guessedLetter);

            if (isCorrectGuess) {
                if (isWordGuessed()) {
                    System.out.println("Congratulations! You guessed the word " + secretWord.toUpperCase()
                            + " correctly.");
                    break;
                }
            } else {
                incorrectGuesses.add(guessedLetter);
                attemptsLeft--;
                if (attemptsLeft == 0) {
                    System.out.println("Game over! You ran out of attempts.");
                    System.out.println("The word was: " + secretWord);
                    break;
                }
            }
        }
    }

    private void selectCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a category:");
        int i = 1;
        for (String category : wordCategories) {
            System.out.println(i + ". " + category);
            i++;
        }
        int categoryIndex = scanner.nextInt();
        selectedCategory = (String) wordCategories.toArray()[categoryIndex - 1];
    }

    private void selectWord() {
        List<String> words = wordDictionary.get(selectedCategory);
        int randomIndex = new Random().nextInt(words.size());
        secretWord = words.get(randomIndex).toLowerCase();
        guessedLetters = new char[secretWord.length()];
        Arrays.fill(guessedLetters, '_');
    }

    private void displayGameState() {
        System.out.println("\nSecret Word: " + getMaskedWord());
        System.out.println("Attempts Left: " + attemptsLeft);
        System.out.print("Incorrect Guesses: ");
        for (char letter : incorrectGuesses) {
            System.out.print(letter + " ");
        }
        System.out.println();
    }

    private char getPlayerGuess() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a letter: ");
        String input = scanner.nextLine().toLowerCase();
        return input.charAt(0);
    }

    private boolean updateGameState(char guessedLetter) {
        boolean isCorrectGuess = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guessedLetter) {
                guessedLetters[i] = guessedLetter;
                isCorrectGuess = true;
            }
        }
        return isCorrectGuess;
    }

    private boolean isWordGuessed() {
        for (char letter : guessedLetters) {
            if (letter == '_') {
                return false;
            }
        }
        return true;
    }

    private String getMaskedWord() {
        StringBuilder maskedWord = new StringBuilder();
        for (char letter : guessedLetters) {
            maskedWord.append(letter).append(' ');
        }
        return maskedWord.toString();
    }

    public static void main(String[] args) {
        Hangman hangman = new Hangman();
        hangman.play();
    }
}