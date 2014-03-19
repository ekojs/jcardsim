/*
 * Copyright 2011 Licel LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javacard.security;

import com.licel.jcardsim.crypto.KeyPairImpl;

/**
 *
 * This class is a container for a key pair (a public key and a
 * private key). It does not enforce any security, and, when initialized,
 * should be treated like a PrivateKey.
 * <p>In addition, this class features a key generation method.
 * @see PublicKey
 */
public final class KeyPair {

    /**
     * <code>KeyPair</code> object containing a RSA key pair.
     */
    public static final byte ALG_RSA = 1;
    /**
     * <code>KeyPair</code> object containing a RSA key pair with private key in
     * its Chinese Remainder Theorem form.
     */
    public static final byte ALG_RSA_CRT = 2;
    /**
     * <code>KeyPair</code> object containing a DSA key pair.
     */
    public static final byte ALG_DSA = 3;
    /**
     * <CODE>KeyPair</CODE> object containing an EC key pair for
     * EC operations over fields of characteristic 2 with polynomial
     * basis.
     */
    public static final byte ALG_EC_F2M = 4;
    /**
     * <CODE>KeyPair</CODE> object containing an EC key pair for
     * EC operations over large prime fields
     */
    public static final byte ALG_EC_FP = 5;

    private KeyPairImpl impl;

    /**
     * (Re)Initializes the key objects encapsulated in this <code>KeyPair</code> instance
     * with new key values. The initialized public and private key objects
     * encapsulated in this instance will then be suitable for use with the
     * <code>Signature</code>, <code>Cipher</code> and <code>KeyAgreement</code> objects.
     * An internal secure random number generator is used during new key pair generation.
     * <p>Notes:<ul>
     * <li><em>For the RSA algorithm, if the exponent value in the public key object is pre-initialized,
     * it will be retained. Otherwise, a default value of 65537 will be used.</em>
     * <li><em>For the DSA algorithm, if the p, q and g parameters of the public key object are pre-initialized,
     * they will be retained. Otherwise, default precomputed parameter sets will be used. The required
     * default precomputed values are listed in </em>Appendix B<em> of </em>Java Cryptography Architecture
     * API Specification & Reference<em> document.</em>
     * <li><em>For the EC case, if the Field, A, B, G and R parameters of the
     * key pair are pre-initialized, then they will be retained. Otherwise
     * default pre-specified values MAY be used (e.g. WAP predefined curves),
     * since computation of random generic EC keys is infeasible on the smart
     * card platform.</em>
     * <li><em>If the time taken to generate the key values is excessive, the implementation may automatically
     * request additional APDU processing time from the CAD.</em>
     * </ul>
     * @throws CryptoException with the following reason codes:<ul>
     * <li><code>CryptoException.ILLEGAL_VALUE</code> if the exponent
     * value parameter in RSA or the p, q, g parameter set in DSA or
     * the Field, A, B, G and R parameter set in EC is invalid.
     * </ul>
     * @see APDU
     * @see Signature
     * @see Cipher
     * @see RSAPublicKey
     * @see ECKey
     * @see DSAKey
     */
    public final void genKeyPair()
            throws CryptoException {
        impl.genKeyPair();
    }

    /**
     * Constructs a <code>KeyPair</code> instance for the specified algorithm and keylength;
     * the encapsulated keys are uninitialized.
     * To initialize the <code>KeyPair</code> instance use the <code>genKeyPair()</code> method.<p>
     * The encapsulated key objects are of the specified <code>keyLength</code> size and
     * implement the appropriate <code>Key</code> interface associated with the specified algorithm
     * (example - <code>RSAPublicKey</code> interface for the public key and <code>RSAPrivateKey</code>
     * interface for the private key within an <code>ALG_RSA</code> key pair).<p>
     * <p>Notes:<ul>
     * <li><em>The key objects encapsulated in the generated </em><code>KeyPair</code><em> object
     * need not support the </em><code>KeyEncryption</code><em> interface.</em>
     * </ul>
     * @param algorithm the type of algorithm whose key pair needs to be generated.
     * Valid codes listed in <code>ALG_..</code> constants above. See <A HREF="../../javacard/security/KeyPair.html#ALG_RSA"><CODE>ALG_RSA</CODE></A>
     * @param keyLength  the key size in bits. The valid key bit lengths are key type dependent.
     * See the <code>KeyBuilder</code> class.
     * @see KeyBuilder
     * @throws CryptoException with the following reason codes:<ul>
     * <li><code>CryptoException.NO_SUCH_ALGORITHM</code> if the requested algorithm
     * associated with the specified type, size of key is not supported.</ul>
     * @see KeyBuilder
     * @see Signature
     * @see javacardx.crypto.KeyEncryption
     * @see Cipher
     */
    public KeyPair(byte algorithm, short keyLength)
            throws CryptoException {
        impl = new KeyPairImpl(algorithm, keyLength);
    }

    /**
     * Constructs a new <code>KeyPair</code> object containing the specified
     * public key and private key.
     * <p>Note that this constructor only stores references to the public
     * and private key components in the generated <code>KeyPair</code> object. It
     * does not throw an exception if the key parameter objects are uninitialized.
     * @param publicKey the public key.
     * @param privateKey the private key.
     * @throws CryptoException with the following reason codes:<ul>
     * <li><code>CryptoException.ILLEGAL_VALUE</code> if the input parameter key
     * objects are inconsistent with each other - i.e mismatched algorithm, size etc.
     * <li><code>CryptoException.NO_SUCH_ALGORITHM</code> if the algorithm
     * associated with the specified type, size of key is not supported.
     * </ul>
     */
    public KeyPair(PublicKey publicKey, PrivateKey privateKey)
            throws CryptoException {
        impl = new KeyPairImpl(publicKey, privateKey);
    }

    /**
     * Returns a reference to the public key component of this <code>KeyPair</code> object.
     * @return a reference to the public key.
     */
    public PublicKey getPublic() {
        return impl.getPublic();
    }

    /**
     * Returns a reference to the private key component of this <code>KeyPair</code> object.
     * @return a reference to the private key.
     */
    public PrivateKey getPrivate() {
        return impl.getPrivate();
    }
}