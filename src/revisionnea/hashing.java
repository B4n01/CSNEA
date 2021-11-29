
package revisionnea;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashing {
    
    public static String Hashpassword(String Password) {
        String OriginalPassword = Password;
        String hashedpassword = null;
        try {
            MessageDigest msgdgst = MessageDigest.getInstance("MD5");
            msgdgst.update(OriginalPassword.getBytes());
            //Get the hash's bytes 
            byte[] bytes = msgdgst.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder ToHex = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                ToHex.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            hashedpassword = ToHex.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return hashedpassword;
    }
    public static boolean CompareHashed(String databasePassword, String userEnteredPassword) {
        return Hashpassword(userEnteredPassword).equals(databasePassword);
    }
}
