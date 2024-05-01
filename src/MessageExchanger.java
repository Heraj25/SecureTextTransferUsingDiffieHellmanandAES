import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import ecryptiondecryption.EDcrypt;

public class MessageExchanger
{
	//prime number
	private static int p;
	//generator
	private static int alpha;
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		
		System.out.println("Please enter the the prime number (p) for the key exchange algorithm: ");
		Scanner scanner = new Scanner(System.in);
		p = scanner.nextInt();
		System.out.println("Please enter the the generator (alpha) for the key exchange algorithm: ");
		alpha = scanner.nextInt();
		scanner.nextLine(); 
		
		
		System.out.println("----------------------------------------");
		System.out.println("Diffie-Hellman Key Exchange initiated...");
		System.out.println("P = " + p + " Alpha = " + alpha);
		
		
		DHKeyExchanger dhAlice = new DHKeyExchanger(p, alpha);
		System.out.println("\nAlice's Private Key: " + dhAlice.getPrivateKey() + "\nAlice's Public Key: " + dhAlice.getPublicKey());
		
		DHKeyExchanger dhBob = new DHKeyExchanger(p, alpha);
		System.out.println("\nBob's Private Key: " + dhBob.getPrivateKey() + "\nBob's Public Key: " + dhBob.getPublicKey());
		
		
		long aliceKey = dhAlice.getSecretKey(dhBob.getPublicKey());
		long bobKey = dhBob.getSecretKey(dhAlice.getPublicKey());
		
		if (aliceKey != bobKey)
			System.err.println("Secret keys are not the same. Probably your p and alpha are big enough to confuse the Math.pow() function used in DHKeyExchanger class");
		
		System.out.println("\nSecret Key: " + aliceKey); 
		System.out.println("----------------------------------------");

		scanner.close();
		EDcrypt run=new EDcrypt();
        run.setVisible(true);
		

	}
}
