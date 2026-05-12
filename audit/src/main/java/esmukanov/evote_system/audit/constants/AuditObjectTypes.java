package esmukanov.evote_system.audit.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuditObjectTypes {

    public static final String USER = "USER";
    public static final String ELECTION = "ELECTION";
    public static final String ELECTION_OPTION = "ELECTION_OPTION";
    public static final String VOTE = "VOTE";
    public static final String RESULT = "RESULT";
    public static final String BLOCKCHAIN_RECORD = "BLOCKCHAIN_RECORD";
}
