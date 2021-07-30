package network.thezone.digitalcurrency;

import org.junit.jupiter.api.Test;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.util.Locale;

public class TransactionTest {

    private KeyPairGenerator keyPairGen;
    private MessageDigest hashFunction;
    private Signature ecdsa;

    private void initAlgorithms() {
        try {
            this.keyPairGen = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyPairGen.initialize(ecSpec);
            this.hashFunction = MessageDigest.getInstance("SHA-256");
            this.ecdsa = Signature.getInstance("SHA256withECDSA");
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void initializeSignature(Signature sign, PrivateKey privateKey) {
        try {
            sign.initSign(privateKey);
        } catch (InvalidKeyException ex) {
            throw new RuntimeException((ex.getMessage()));
        }
    }

    public void makeTx() {
        initAlgorithms();
        KeyPair senderKeys = keyPairGen.generateKeyPair();
        KeyPair recipientKeys = keyPairGen.generateKeyPair();
        initializeSignature(ecdsa, senderKeys.getPrivate());

        Transaction first = new TransactionBuilder(hashFunction)
                .toRecipient(recipientKeys.getPublic().toString())
                .firstTransaction()
                .sign(ecdsa);

        System.out.println();
        ////System.out.println(first);
    }

    @Test
    public void fromOpaqueToKey() throws Exception {
        ECPrivateKey privKey = generatePrivKey();
        generatePubKey(privKey);
    }

    private ECPrivateKey generatePrivKey() throws Exception{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyGen.initialize(ecSpec);

        KeyPair kp = keyGen.generateKeyPair();
        PublicKey pub = kp.getPublic();
        PrivateKey pvt = kp.getPrivate();

        ECPrivateKey epvt = (ECPrivateKey)pvt;
        System.out.println(epvt.getS().toString(16).toUpperCase(Locale.ROOT));
        return epvt;
    }

    private void generatePubKey(ECPrivateKey privKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        AlgorithmParameterGenerator paramsGen = AlgorithmParameterGenerator.getInstance("secp256k1");
        ECPrivateKeySpec privKeySpec = new ECPrivateKeySpec(privKey.getS(), paramsGen.generateParameters().getParameterSpec(ECParameterSpec.class));

        System.out.println(keyFactory.generatePublic(privKeySpec));
    }
}
