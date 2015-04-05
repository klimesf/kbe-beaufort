package cz.filipklimes;

/**
 * Encrypts and decrypts given text using a cipher algorithm.
 *
 * @author klimesf
 */
public interface CipherSolver {

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
