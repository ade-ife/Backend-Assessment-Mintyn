package Niji.Backend.Assessment.Mintyn.Pojo.Response;


public enum EnumResponseCodes {

    SUCCESS("00"),
    DUPLICATE_TRANSACTION_REFERENCE("01"),
    UNABLE_TO_CREATE_REQUEST("02"),
    UNKNOWN_STATE("03"),
    TRANSACTION_QUERY_DATERANGE_EXPIRED("04"),
    ACCOUNT_NOT_FOUND("05"),
    AUTH_TOKEN_REQUIRED("06"),
    EXPIRED_TOKEN("07"),
    INVALID_TOKEN("08"),
    INVALID_USER_CREDENTIALS("09"),
    INACTIVE_CREDENTIALS("10"),
    ERROR_PROCESSING_REQUEST("11"),
    INVALID_CALLER_IP_ADDRESS("12"),
    WRONG_ACCOUNT_PASSED("13"),
    SYSTEM_EXCEPTION("14"),
    INTERNAL_BAD_REQUEST("15"),
    FAILED("96");
    private final String title;
    EnumResponseCodes(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.title;
    }
}

