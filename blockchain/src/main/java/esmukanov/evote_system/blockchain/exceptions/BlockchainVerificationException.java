package esmukanov.evote_system.blockchain.exceptions;

public class BlockchainVerificationException extends RuntimeException {

    public BlockchainVerificationException(String message) {
        super(message);
    }
}
