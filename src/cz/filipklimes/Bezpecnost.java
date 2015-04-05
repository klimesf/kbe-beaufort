package cz.filipklimes;

import java.io.BufferedReader;
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

    public static void main(String[] args) throws Exception {
        Bezpecnost inst = new Bezpecnost();
        while (inst.run()) {
        }
    }

    boolean run() throws Exception {
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

    private String nextToken() throws Exception {
        while (!st.hasMoreTokens()) st = new StringTokenizer(stdin.readLine());
        return st.nextToken();
    }

    private void handleEncrypt() throws Exception {
        String key = nextToken();
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            sb.append(nextToken());
        }
        System.out.println(cs.encrypt(key, sb.toString()));
    }

    private void handleDecrypt() throws Exception {
        String key = nextToken();
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            sb.append(nextToken());
        }
        System.out.println(cs.decrypt(key, sb.toString()));
    }

}

