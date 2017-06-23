import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WriteToFileTest {
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // print output to file
        //PrintWriter output = new PrintWriter("hashValues");
        // number of hashes here
        int limit = (int)(Math.pow(2,18));
        for (int i=0; i<limit; i++) {
            // to print to file:
            //output.println(sha256(Integer.toString(i)));
            // otherwise just
             sha256(Integer.toString(i));
        }
        //output.close();
    }
    
    static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
}