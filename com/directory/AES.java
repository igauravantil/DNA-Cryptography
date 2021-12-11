package javaapplication;
 
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.hslf.record.Document;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
 
 
public class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static String line;
    private static String cle;
    static ArrayList<String> txt=new ArrayList<>();
    static ArrayList<String> txtCrypte=new ArrayList<>();
    static ArrayList<String> txtDecrypte=new ArrayList<>();;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public static String decryptToText(String str){
        String result = "" ;
        while(str.length() < 8){
            str = '0' + str;
        }
        for(int i = 0 ; i < str.length() ; i += 8){
            String temp = "";
            for(int j =0 ;j < 8; j ++){
                temp += str.charAt(i + j);
            }
            int charCode = Integer.parseInt(temp, 2);
            String strTemp = new Character((char)charCode).toString();
            result += strTemp;


        }
        return result ;
    }
    static boolean isPrime(int n)
    {
        
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
  
        
        if (n % 2 == 0 || n % 3 == 0)
            return false;
  
        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
  
        return true;
    }
    public static String complement(String str){
        for(int i = 0 ; i < str.length() ; i++){
            if(isPrime(i)){
                if(str.charAt(i) == 'A'){
                    str = str.substring(0, i) + 'G' + str.substring(i+1);
                    
                }
                else if(str.charAt(i) == 'G'){
                    str = str.substring(0, i) + 'A' + str.substring(i+1);
                }
                else if(str.charAt(i) == 'C'){
                    str = str.substring(0, i) + 'T' + str.substring(i+1);
                }
                else if(str.charAt(i) == 'T'){
                    str = str.substring(0, i) + 'C' + str.substring(i+1);
                }

            }
        }
        return str;
    }
    public static String decryption(String encrypted){
        
            String ans = "";
            int f = encrypted.length();

            for(int  i =0 ; i< f; i = i + 4){
                int temp = 0;
                for(int j = 3 ;j>=0;j--){
                    if(encrypted.charAt(i+3-j)=='A'){
                        temp = temp + 0;
                    }
                    else if(encrypted.charAt(i+3-j)=='G'){
                        int po = 1;
                        for(int k = 0;k < j*2+1;k++){
                            po = po*2;
                        }
                        temp = temp + po;

                    }
                    else if(encrypted.charAt(i+3-j)=='C'){
                        int po = 1;
                        for(int k = 0;k < j*2;k++){
                            po = po*2;
                        }
                        temp = temp + po;
                    }
                    else if(encrypted.charAt(i+3-j)=='T'){
                        int po1 = 1;
                        for(int k = 0;k < j*2;k++){
                            po1 = po1*2;
                        }
                        int po2 = 1;

                        for(int k = 0;k < j*2+1;k++){
                            po2 = po2*2;
                        }
                        temp = temp + po1 + po2;
                    }
                }
                char uf = (char)temp;
                //System.out.print(temp + " ");
                ans = ans + uf;
            }

            return ans;
    }
    public static String encryption(String toEncrypt){
        
            String encrypted = "";
            String fullEncrypt = "";
            for(int i = 0 ; i  < toEncrypt.length();i++){
                char thisChar = toEncrypt.charAt(i);
                int asc = (int)thisChar;
                String temporary = "";
                for(int j =0 ; j < 4; j++){
                    int and1 = asc&1;
                    asc = asc>>1;

                    int and2 = asc&1;
                    asc = asc>>1;
                    if(and1 == 1){
                        if(and2==1){
                            temporary =  "T" + temporary;
                        }
                        else{
                            temporary =  "C"+ temporary;


                        }
                    }
                    else{
                        if(and2==1){
                            temporary =  "G"+ temporary;


                        }
                        else{
                            temporary =  "A"+ temporary;


                        }
                    }


                }
                encrypted = encrypted + temporary;
                /*int myInt = map.get(temporary).intValue();
                char val = (char)myInt;
                fullEncrypt = fullEncrypt + val;*/


            }
            return encrypted;
    }
    public static String xorString(String text, String key){
        String result = "";
        
        if(key.length() <= text.length()){
            
            String temp = key;
            int mul = text.length()/key.length();
            for(int a = 0 ; a < mul-1 ; a ++){
                key = key.concat(temp);
            }
            System.out.println(key.length());
            int i = key.length() - 1;
            int j = text.length() - 1;
            
            
            while(i >= 0){
                if(key.charAt(i) == text.charAt(j)){
                    result += '0';
                }
                else{
                    result += '1';
                }
                i--;
                j--;
            }
            while(j >= 0){
                result += text.charAt(j);
                j--;
            }
        }
        else{
            
            int i = key.length() - 1;
            int j = text.length() - 1;
            
            while(j >= 0){
                if(key.charAt(i) == text.charAt(j)){
                    result += '0';
                    
                }
                else{
                    result += '1';
                }
                i--;
                j--;
                
            }
            
        }
        result = reverse(result);
        //System.out.println(result);
        
        return result;
    }
    public static String strToBinary(String s)
    {
        int n = s.length();
        String result = "";
        for (int i = 0; i < n; i++)
        {
            // convert each char to
            // ASCII value
            int val = Integer.valueOf(s.charAt(i));
 
            // Convert ASCII value to binary
            String bin = "";
            while (val > 0)
            {
                if (val % 2 == 1)
                {
                    bin += '1';
                }
                else
                    bin += '0';
                val /= 2;
            }
            bin = reverse(bin);
            while(bin.length()%8 !=0){
                bin = '0' + bin;
            }
            result  = result.concat(bin);
            //System.out.print(bin + " ");
        }
        
        return result;
    }

    static String reverse(String input)
    {
        char[] a = input.toCharArray();
        int l, r = 0;
        r = a.length - 1;
 
        for (l = 0; l < r; l++, r--)
        {
            // Swap values of l and r
            char temp = a[l];
            a[l] = a[r];
            a[r] = temp;
        }
        return String.valueOf(a);
    }
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            // DNA 
            String toEncrypt = strToEncrypt;
            String key = strToBinary(secret);
            String binary = strToBinary(toEncrypt);
            String test = xorString(binary, key);
            String encrypted = encryption(test);
            encrypted = reverse(encrypted);
            encrypted = complement(encrypted);
            System.out.println("encrypted string: "+encrypted);
            return encrypted;
            // DNA 
            /*
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            */
        }
        catch (Exception e)
        {
            System.out.println("Error occured during encryption: " + e.toString());
        }
        return null;
    }
    
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            // DNA 
            strToDecrypt = complement(strToDecrypt);
            strToDecrypt = reverse(strToDecrypt);
            String decrypted = decryption(strToDecrypt);
            secret = strToBinary(secret);
            decrypted = xorString(decrypted, secret);
            System.out.println("Decrypted : " + decrypted  + " " + secret);
            decrypted = decryptToText(decrypted);
            
            
            return decrypted;
            
            // DNA 
            /*setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getMimeDecoder().decode(strToDecrypt)));
           */
        }
        catch (Exception e)
        {
            System.out.println("Error occured during decryption: " + e.toString());
        }
        return null;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
    }
        
    } 