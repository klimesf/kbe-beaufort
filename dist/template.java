package bezpecnost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Cipher solver for A7B34KBE class at FEE CUT.
 */
class Bezpecnost {

    private StringTokenizer st = new StringTokenizer("");
    private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    private String type;
    private CipherSolver cs = new CipherSolverImpl();

    /**
     * Main method of the program.
     * Runs the main loop.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Bezpecnost inst = new Bezpecnost();

        while (inst.run()) {
        }

        // Close the input stream.
        try {
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
        System.out.println(output);
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
        System.out.print(cs.decrypt(cs.removeInvalidCharacters(key), cs.removeInvalidCharacters(sb.toString())) + "\n");
    }
}

/**
 * Encrypts and decrypts given text using a cipher algorithm.
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
 * Cipher solver implementation.
 */
class CipherSolverImpl implements CipherSolver {

    // TODO

}