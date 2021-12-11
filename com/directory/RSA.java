package javaapplication;
 

import java.math.BigInteger;
import java.util.Random;
import java.io.*;
import java.math.BigInteger;


public class RSA {
	public static BigInteger p;
	public static BigInteger q;
	private BigInteger N;
	private BigInteger phi;
	private BigInteger e;
	private BigInteger d;
	private int bitlength = 62;
	private int blocksize = 256;
        

	private Random r;

	public RSA() {
                long x=3053880317135553533L;
                long y=3916702577991933803L;
                long z=1678030687;
                 p = BigInteger.valueOf(x);
	 	 q = BigInteger.valueOf(y);
	 	 e=	BigInteger.valueOf(z);
		N = p.multiply(q);
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
	
			e.add(BigInteger.ONE);		//e=e+1
		}

		d = e.modInverse(phi);
                
                
                
                
                
	
	

}
	public RSA(BigInteger e, BigInteger d, BigInteger N) {

		this.e = e;
		this.d = d;
		this.N = N;

	}

	public static void main(String[] args) throws IOException {

		RSA rsa = new RSA();
		DataInputStream in = new DataInputStream(System.in);
		String teststring;
		System.out.println("Enter the plain text:");
		teststring = in.readLine(); 
		System.out.println("Encrypting String: " + teststring);
		System.out.println("String in Bytes: " + bytesToString(teststring.getBytes()));

		// encrypt

		byte[] encrypted = rsa.encryptRSA(teststring.getBytes());
		System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));
		//  decrypt
		byte[] decrypted = rsa.decryptRSA(encrypted);
		System.out.println("Decrypted String in Bytes: " + bytesToString(decrypted));
		System.out.println("Decrypted String: " + new String(decrypted));

	}

public static String bytesToString(byte[] encrypted) {

		String test = "";
		for (byte b : encrypted) {
			test += Byte.toString(b);
		}
                String s = new String(encrypted);   
		return s;
	}

	// Encrypt message

	public byte[] encryptRSA(byte[] message) {
            // DNA 
            String toEncrypt = bytesToString(message);
            System.out.print(toEncrypt);
            
            
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
                //int myInt = map.get(temporary).intValue();
                //char val = (char)myInt;
                //fullEncrypt = fullEncrypt + val;


            }
            return encrypted.getBytes();
            // DNA 
		
		//return (new BigInteger(message)).modPow(e, N).toByteArray();
	}

	// Decrypt message

	public byte[] decryptRSA(byte[] message) {
            
            // DNA 
            String encrypted = bytesToString(message);
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

            return ans.getBytes();
            
            
            // DNA
		//return (new BigInteger(message)).modPow(d, N).toByteArray();

	}
}