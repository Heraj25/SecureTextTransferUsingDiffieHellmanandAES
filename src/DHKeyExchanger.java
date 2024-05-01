import java.util.concurrent.ThreadLocalRandom;

/**
 * Implements Diffie-Hellman Key Exchange algorithm.
 */
public class DHKeyExchanger
{
	private long p, alpha;
	private long publicKey, privateKey;
	
	public DHKeyExchanger(long p, long alpha)
	{
		this.alpha = alpha;
		this.p = p;
		
		privateKey = ThreadLocalRandom.current().nextLong(p);
		publicKey =  (long) (Math.pow(alpha, privateKey)) % p;
	}
	
	
	public long getSecretKey(long publicKey)
	{
		return (long) (Math.pow(publicKey, privateKey)) % p;
	}
	
	public long getPublicKey()
	{
		return publicKey;
	}

	public long getPrivateKey()
	{
		return privateKey;
	}

	public long getP()
	{
		return p;
	}

	public long getAlpha()
	{
		return alpha;
	}
}
