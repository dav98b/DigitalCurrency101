package network.thezone.digitalcurrency.keys;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public final class ECKeyPairWrapper {

    private final KeyPair genericKeyPair;

    public ECKeyPairWrapper(KeyPair genericKeyPair) {
        this.genericKeyPair = genericKeyPair;
    }

    public ECPrivateKey getPrivateKey() {
        return (ECPrivateKey) genericKeyPair.getPrivate();
    }

    public ECPublicKey getPublicKey() {
        return (ECPublicKey) genericKeyPair.getPublic();
    }

}
