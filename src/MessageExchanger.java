import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import ecryptiondecryption.EDcrypt;

public class MessageExchanger
{
    
    private static int p;
    
    private static int alpha;

    public static void main(String[] args) throws UnsupportedEncodingException
    {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Please enter the prime number (p) for the key exchange algorithm: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid prime number.");
                scanner.next(); 
            }
            p = scanner.nextInt();

           
            if (!isPrime(p)) {
                System.out.println("The entered number is not a prime number. Please enter a prime number.");
            }
        } while (!isPrime(p));

// Input Krdo (alpha) for the key exchange algorithm alpha primitive root hai 
        do {
            System.out.println("Please enter the generator (alpha) for the key exchange algorithm: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); 
            }
            alpha = scanner.nextInt();

// Check if alpha is a primitive root modulo p
            if (!isPrimitiveRoot(alpha, p)) {
                System.out.println("The entered alpha is not a primitive root modulo " + p + ". Please enter a primitive root.");
            }
        } while (!isPrimitiveRoot(alpha, p));

        scanner.nextLine(); 

        System.out.println("----------------------------------------");
        System.out.println("Diffie-Hellman Key Exchange initiated...");
        System.out.println("P = " + p + ", Alpha = " + alpha);

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

//Number is prime
   
	private static boolean isPrime(int n) {
		if (n <= 1)
			return false;
	
//divisibility from 2 up to the square root of n
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0)
				return false;
		}
	
		return true;
	}
	
// Primitive root modulo p
    
private static boolean isPrimitiveRoot(int alpha, int p) {
    if (gcd(alpha, p) != 1)
        return false;
//Euler's Totient Function (phi)
    int phi = p - 1;
// Alpha raised to the power of each number from 1 to phi generates distinct values
    for (int i = 1; i <= phi; i++) {
        int result = power(alpha, i, p);
        for (int j = i + 1; j <= phi; j++) {
            if (result == power(alpha, j, p))
                return false; 
	// Alpha generates a repeated value, it's not a primitive root
        }
    }
    
    return true;
}


// Power in modular
    private static int power(int x, int y, int p) {
        int res = 1;
        x = x % p;

        while (y > 0) {
            if ((y & 1) == 1)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }
//Greatest Common Divisor (GCD)
    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
}