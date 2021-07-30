package network.thezone.digitalcurrency;

import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.util.Arrays;
import java.util.Formatter;

public class Transaction {

    private byte[] recipientPublicKey;
    private byte[] hash; //txHash encodes previous tx hash + recipient public key
    private byte[] senderSignature;

    public Transaction(byte[] recipientPublicKey, byte[] txHash, byte[] senderSignature) {
        this.recipientPublicKey = recipientPublicKey;
        this.hash = txHash;
        this.senderSignature = senderSignature;
    }

    public byte[] getHash() {
        return hash;
    }

    public String toString() {
        String txOutput = "{\n\thash: %s,\n\trecipient: %s,\n\tsignature: %s\n}";
        return new Formatter().format(txOutput, new String(hash, StandardCharsets.UTF_8),
                new String(recipientPublicKey), new String(senderSignature)).toString();
    }
}
