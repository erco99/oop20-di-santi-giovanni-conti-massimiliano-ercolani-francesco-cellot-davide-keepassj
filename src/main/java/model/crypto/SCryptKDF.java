package model.crypto;

import com.google.common.primitives.Bytes;
import com.lambdaworks.crypto.SCryptUtil;

public class SCryptKDF implements KDF {

    @Override
    public final byte[] generateKey(final byte[] password, final byte[] seed, final int rounds) {
        byte[] composite = Bytes.concat(password, seed);
        String hash = SCryptUtil.scrypt(new String(composite), 10, 10, 10);
        return hash.getBytes();
    }

}
