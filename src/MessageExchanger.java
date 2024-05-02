import java.io.UnsupportedEncodingException;

import java.util.Scanner;

import ecryptiondecryption.EDcrypt;

public class MessageExchanger
{
	//prime number
	private static int p;
	//generator
	private static int alpha;
	
	public static boolean isPrimitiveRoot(int p, int alpha) {
        boolean[] visited = new boolean[p];
        
        int current = alpha % p;
        visited[current] = true;
        for (int i = 2; i < p; i++) {
            current = (current * alpha) % p;
            visited[current] = true;
        }
        
        
        for (int i = 1; i < p; i++) {
            if (!visited[i]) {
                return false; 
            }
        }
        
        return true; 
    }

	public static boolean isPrime(int p, int alpha) {
        // Check if p is prime
        boolean isPPrime = isPrimeNumber(p);
        // Check if alpha is prime
        boolean isAlphaPrime = isPrimeNumber(alpha);

        return isPPrime && isAlphaPrime;
    }

    // Helper function to check if a number is prime
    private static boolean isPrimeNumber(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i = i + 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

	//good use cases p=7 and alpha=2 fails the test
	//p=7 and alpha=3 passes the test
	//p=23, 𝛼=5 passes the test
	//

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		
		System.out.println("Please enter the the prime number (p) for the key exchange algorithm: ");
		Scanner scanner = new Scanner(System.in);
		p = scanner.nextInt();
		System.out.println("Please enter the the generator (alpha) for the key exchange algorithm: ");
		alpha = scanner.nextInt();
		// scanner.nextLine(); 
		
		
		System.out.println("----------------------------------------");
		System.out.println("Diffie-Hellman Key Exchange initiated...");
		System.out.println("P = " + p + " Alpha = " + alpha);
		
		

		if(isPrimitiveRoot(p, alpha) && isPrime(p, alpha)) {
			DHKeyExchanger dhAlice = new DHKeyExchanger(p, alpha);
			System.out.println("\nAlice's Private Key: " + dhAlice.getPrivateKey() + "\nAlice's Public Key: " + dhAlice.getPublicKey());
			
			DHKeyExchanger dhBob = new DHKeyExchanger(p, alpha);
			System.out.println("\nBob's Private Key: " + dhBob.getPrivateKey() + "\nBob's Public Key: " + dhBob.getPublicKey());
			
			
			long aliceKey = dhAlice.getSecretKey(dhBob.getPublicKey());
			long bobKey = dhBob.getSecretKey(dhAlice.getPublicKey());
			if(aliceKey == bobKey) {
				System.out.println("\nSecret Key: " + aliceKey); 
				System.out.println("----------------------------------------");
			}
				
			scanner.close();
			EDcrypt run=new EDcrypt();
			run.setVisible(true);
		
		}
		else //(aliceKey != bobKey)
			System.err.println("Secret keys are not the same. Probably your p and alpha does not contain primitive root or they may not be prime numbers");

	}
}
