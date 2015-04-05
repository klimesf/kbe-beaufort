package cz.filipklimes;

/**
 * Encrypts and decrypts given text using Beaufort algorithm.
 *
 * @author klimesf
 */
public class BeaufortCipherSolver implements CipherSolver {

    private char[][] tableau;
    private char[] cols = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private char[] rows = "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray();

    public BeaufortCipherSolver() {
    }

    private char[][] getTableau() {
        if (this.tableau == null) {
            this.createTableau();
        }
        return this.tableau;
    }

    private void createTableau() {
        char[][] tableau = new char[26][26];
        tableau[0] = "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray();

        for (int i = 1; i < 26; i++) {
            for (int j = 1; j < 26; j++) {
                tableau[i][j - 1] = tableau[i - 1][j];
            }
            tableau[i][25] = tableau[i - 1][0];
        }

        this.tableau = tableau;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String removeInvalidCharacters(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (char c : s.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String encrypt(String key, String text) {
        text = this.removeInvalidCharacters(text);

        char[] textArray = text.toCharArray();
        char[] keyArray = key.toCharArray();
        StringBuilder sb = new StringBuilder(text.length());

        for (int i = 0; i < textArray.length;) {
            for (int j = 0; j < keyArray.length && i < textArray.length; j++) {
                int a = this.getPositionOfTextCharacter(textArray[i]);
                int b = this.getPositionOfKeyCharacter(keyArray[j]);

//                System.out.printf("%c - %c: [%d][%d] = %c\n", textArray[i], keyArray[j], a, b, this.getTableau()[a][b]);

                sb.append(this.getTableau()[a][b]);
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String decrypt(String key, String text) {
        return text;
    }

    private int getPositionOfKeyCharacter(char c) {
        for (int i = 0; i < 26; i++) {
            if(rows[i] == Character.toUpperCase(c)) {
                return i;
            }
        }
        return 0; // Fallback
    }

    private int getPositionOfTextCharacter(char c) {
        for (int i = 0; i < 26; i++) {
            if(cols[i] == Character.toLowerCase(c)) {
                return i;
            }
        }
        return 0; // Fallback
    }
}
