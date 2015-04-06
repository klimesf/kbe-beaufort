package bezpecnost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Beaufort cipher solver for A7B34KBE class at FEE CUT.
 */
class Bezpecnost {

    private StringTokenizer st = new StringTokenizer("");
    private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    private String type;
    private CipherSolver cs = new BeaufortCipherSolver();
    private Socket socket;
    private PrintWriter printWriter;

    /**
     * Main method of the program.
     * Runs the main loop.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Bezpecnost inst = new Bezpecnost();
//        try {
//            inst.socket = new Socket(InetAddress.getByName("94.112.68.30"), 3999);
//            inst.printWriter = new PrintWriter(inst.socket.getOutputStream(), true);
//        } catch (IOException e) {
//        }

        while (inst.run()) {
        }



        // Close the input stream.
        try {
//            inst.socket.close();
            inst.stdin.close();
        } catch (IOException e) {
        }
    }

    /**
     * Main loop method of the program.
     *
     * @return True if you can continue to iterate, false if not.
     */
    boolean run() {
        type = nextToken();
        if (type.equals("end")) return false;
        if (type.equals("e")) {
            handleEncrypt();
        }
        if (type.equals("d")) {
            handleDecrypt();
        }

        return true;
    }

    /**
     * Reads next string token from the input.
     *
     * @return Reads the next string token from the input.
     */
    private String nextToken() {
        while (!st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(stdin.readLine());
            } catch (IOException e) {
            }
        }
        return st.nextToken();
    }


    /**
     * Handles the "e" command.
     */
    private void handleEncrypt() {
        String key = nextToken();
        StringBuilder inputStringBuilder = new StringBuilder();
        while (st.hasMoreTokens()) {
            inputStringBuilder.append(nextToken());
        }

        String output = cs.encrypt(cs.removeInvalidCharacters(key), cs.removeInvalidCharacters(inputStringBuilder.toString()));

        StringBuilder outputStringBuilder = new StringBuilder();
        int i = 0;
        for (char c : output.toCharArray()) {
            outputStringBuilder.append(c);
            if(++i % 5 == 0 && i < (output.length() - 1)) {
                outputStringBuilder.append(' ');
            }
        }
        outputStringBuilder.append('\n');
//        this.printWriter.write("e " + key + " " + inputStringBuilder.toString() + "\n");
//        this.printWriter.write("ours: " + outputStringBuilder.toString());
//        this.printWriter.flush();
        System.out.print(outputStringBuilder.toString());
    }

    /**
     * Handles the "d" command.
     */
    private void handleDecrypt() {
        String key = nextToken();
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            sb.append(nextToken());
        }
//        this.printWriter.write("d " + key + " " + sb.toString() + "\n");
//        this.printWriter.write("ours: " + cs.decrypt(cs.removeInvalidCharacters(key), cs.removeInvalidCharacters(sb.toString())));
//        this.printWriter.flush();
        System.out.print(cs.decrypt(cs.removeInvalidCharacters(key), cs.removeInvalidCharacters(sb.toString())) + "\n");
    }
}

/**
 * Encrypts and decrypts given text using a cipher algorithm.
 *
 * @author klimesf
 */
interface CipherSolver {

    /**
     * Removes invalid characters from given string.
     *
     * @param s String to be truncated.
     * @return String without invalid characters.
     */
    String removeInvalidCharacters(String s);

    /**
     * Encrypts given text with given key.
     *
     * @param key  Key to be encrypted by.
     * @param text Open text to be encrypted.
     * @return Encrypted text.
     */
    String encrypt(String key, String text);

    /**
     * Decrypts given text by given key.
     *
     * @param key  Key to be decrypted by.
     * @param text Encrypted text to be decrypted.
     * @return Decrypted text.
     */
    String decrypt(String key, String text);
}

/**
 * Encrypts and decrypts given text using Beaufort algorithm.
 *
 * @author klimesf
 * @see "http://www.thonky.com/kryptos/beaufort-cipher/"
 */
class BeaufortCipherSolver implements CipherSolver {

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
