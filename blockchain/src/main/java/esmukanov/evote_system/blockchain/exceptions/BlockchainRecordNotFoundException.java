package esmukanov.evote_system.blockchain.exceptions;

public class BlockchainRecordNotFoundException extends RuntimeException {

    public BlockchainRecordNotFoundException(String message) {
        super(message);
    }
}
