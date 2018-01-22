import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
 
public class SHAExample {
     
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException 
    {
	BufferedReader obj=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("enter data to be encrypted");
        String passwordToHash=obj.readLine();
        String salt = "SHA!RSSP";
        String securePassword = get_SHA_256_SecurePassword(passwordToHash, salt);
        System.out.println("SHA-256:" + securePassword);   
    }
 
   
    private static String get_SHA_256_SecurePassword(String passwordToHash, String salt)
    {
        //Use MessageDigest md = MessageDigest.getInstance("SHA-256");
		String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
    //Add salt
    
}