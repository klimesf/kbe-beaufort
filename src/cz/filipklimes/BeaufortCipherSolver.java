package cz.filipklimes;

/**
 * Encrypts and decrypts given text using Beaufort algorithm.
 *
 * @author klimesf
 * @see "http://www.thonky.com/kryptos/beaufort-cipher/"
 */
public class BeaufortCipherSolver implements CipherSolver {

    private char[][] tableau;
    private char[] cols;
    private char[] rows;

    /**
     * Lazy load tableau getter,
     *
     * @return The cipher tableau.
     */
    private char[][] getTableau() {
        if (this.tableau == null) {
            this.createTableau();
        }
        return this.tableau;
    }

    /**
     * Lazy load cols getter.
     *
     * @return The column indexes of the tableau.
     */
    public char[] getCols() {
        if (this.cols == null) {
            this.cols = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        }
        return this.cols;
    }

    /**
     * Lazy load rows getter.
     *
     * @return The row indexes of the tableau.
     */
    public char[] getRows() {
        if (this.rows == null) {
            this.rows = "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray();
        }
        return this.rows;
    }

    /**
     * Creates the cipher tableau.
     */
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

        // Init local variables
        char[] textArray = text.toCharArray();
        char[] keyArray = key.toCharArray();
        StringBuilder cipherStringBuilder = new StringBuilder(text.length());

        int inputStringIterator = 0;
        while (inputStringIterator < textArray.length) { // Iterates over the whole string

            // Init the column iterator
            int columnIterator = 0;

            // Iterates over the password/columns
            while (columnIterator < keyArray.length && inputStringIterator < textArray.length) {

                // Get indexes
                int rowIndex = this.getIndexOfColCharacter(textArray[inputStringIterator]);
                int colIndex = -1;
                if(rowIndex > -1) {
                    colIndex = this.getIndexOfRowCharacter(keyArray[columnIterator]);
                }

                // Append to the ciphered text and iterate
                if (rowIndex > -1 && colIndex > -1) {
                    cipherStringBuilder.append(this.getTableau()[rowIndex][colIndex]);
                    inputStringIterator++;
                    columnIterator++;
                } else {
                    inputStringIterator++;
                }
            }
        }

        return cipherStringBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String decrypt(String key, String text) {

        char[] keyArray = key.toCharArray();
        char[] textArray = text.toCharArray();
        StringBuilder openStringBuilder = new StringBuilder(text.length());

        int inputStringIterator = 0;
        while (inputStringIterator < textArray.length) { // Iterates over the whole string

            // Init the column iterator
            int columnIterator = 0;

            // Iterates over the password/columns
            while (columnIterator < keyArray.length && inputStringIterator < textArray.length) {

                // Get indexes
                int rowIndex = this.getIndexOfRowCharacter(keyArray[columnIterator]);
                int colIndex = -1;
                if (rowIndex >= 0) {
                    colIndex = this.getIndexOfTableauCharacter(textArray[inputStringIterator], rowIndex);
                }

                // Append to the ciphered text and iterate
                if(rowIndex != -1 && colIndex != -1) {
                    openStringBuilder.append(Character.toUpperCase(this.getCols()[colIndex]));
                    inputStringIterator++;
                    columnIterator++;
                } else {
                    inputStringIterator++;
                }
            }
        }

        return openStringBuilder.toString();
    }

    /**
     * Returns row number of the given character.
     * Returns -1 if the character was not found.
     *
     * @param c The character.
     * @return The row number.
     */
    private int getIndexOfRowCharacter(char c) {
        for (int i = 0; i < 26; i++) {
            if (this.getRows()[i] == Character.toUpperCase(c)) {
                return i;
            }
        }
        return -1; // Fallback
    }

    /**
     * Returns column number of the given character.
     * Returns -1 if the character was not found.
     *
     * @param c The character.
     * @return The column number.
     */
    private int getIndexOfColCharacter(char c) {
        for (int i = 0; i < 26; i++) {
            if (this.getCols()[i] == Character.toLowerCase(c)) {
                return i;
            }
        }
        return -1; // Fallback
    }

    /**
     * Returns column number of given character in given row in the tableau.
     * Returns -1 if the character was not found.
     *
     * @param c   The character.
     * @param row The row number.
     * @return The column number.
     */
    private int getIndexOfTableauCharacter(char c, int row) {
        for (int i = 0; i < 26; i++) {
            if (this.getTableau()[row][i] == Character.toUpperCase(c)) {
                return i;
            }
        }
        return -1; // Fallback
    }
}
