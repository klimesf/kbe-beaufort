package cz.filipklimes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Beaufort cipher solver for A7B34KBE class at FEE CUT.
 */
class Bezpecnost {

    private StringTokenizer st = new StringTokenizer("");
    private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    private String type;
    private CipherSolver cs = new BeaufortCipherSolver();

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

        StringBuilder outputStringBuilder = new StringBuilder();
        int i = 0;
        for (char c : output.toCharArray()) {
            outputStringBuilder.append(c);
            if(++i % 5 == 0 && i < (output.length() - 1)) {
                outputStringBuilder.append(' ');
            }
        }
        outputStringBuilder.append('\n');
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
        System.out.print(cs.decrypt(cs.removeInvalidCharacters(key), cs.removeInvalidCharacters(sb.toString())) + "\n");
    }
}
