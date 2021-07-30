package network.thezone.digitalcurrency;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Objects;

public class TransactionBuilder {

    private final MessageDigest hashFunction;

    /*tx properties*/
    private byte[] recipient;
    private byte[] txHash;
    private byte[] txSign;

    public TransactionBuilder(MessageDigest hashFunction) {
        this.hashFunction = hashFunction;
    }

    public TransactionBuilder toRecipient(String recipient) {
        this.recipient = recipient.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public TransactionBuilder firstTransaction() {
        hashFunction.update((byte) 0);
        hashFunction.update(recipient);
        this.txHash = hashFunction.digest();
        hashFunction.reset();
        return this;
    }

    public TransactionBuilder previousTransaction(Transaction previousTx) {
        hashFunction.update(previousTx.getHash());
        hashFunction.update(recipient);
        this.txHash = hashFunction.digest();
        hashFunction.reset()
        ;
        return this;
    }

    public Transaction sign(Signature signature) {
        Objects.requireNonNull(recipient);
        Objects.requireNonNull(txHash);
        try {
            makeSignature(signature);
        } catch (SignatureException ex) {
            throw new IllegalStateException();
        }
        return new Transaction(recipient, txHash, txSign);
    }

    private void makeSignature(Signature signature) throws SignatureException {
        signature.update(recipient);
        signature.update(txHash);
        this.txSign = signature.sign();
    }


}
