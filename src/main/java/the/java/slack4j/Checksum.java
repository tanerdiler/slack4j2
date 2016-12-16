package the.java.slack4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by taner on 13.12.2016.
 */
public class Checksum
{
    public static final String of(String text)
    {
        String checksum = text;

        if (text == null)
        {
            return null;
        }

        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte byteData[] = md.digest();

            StringBuilder hexString = convertByteToHex(byteData);

            checksum = hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            //TODO think about what we gonna do
        }
        return checksum;
    }

    private static StringBuilder convertByteToHex(byte[] byteData)
    {
        // convert the byte to hex format method 2
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteData.length; i++)
        {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1)
            {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString;
    }
}