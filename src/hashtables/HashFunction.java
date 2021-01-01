package hashtables;

public class HashFunction {

    // simple arbitrary hash function
    public int hashFunction(String key) {
        char[] keyArr = key.toCharArray();
        int val = 0;
        for ( char letter : keyArr ) {
            val += letter;
        }
        return val;
    }
}
