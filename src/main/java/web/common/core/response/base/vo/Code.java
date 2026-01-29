package web.common.core.response.base.vo;

import web.common.core.response.base.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Code {

    // 충돌 방지를 위한 Code format
    //XXX0000 - 실제 떨궈주는 코드 HttpStatus(200 - 200번대, 400 - 400번대)
    //000XX00 - 분류
    //00000XX - id
    // USER_NICKNAME_DUPLICATED(13010, HttpStatus.BAD_REQUEST, "User nickname duplicated"),

    OK(2000000, HttpStatus.OK, "Ok"),
    OK_ASYNC(2020000, HttpStatus.ACCEPTED, "Ok"),

    //00 default
    BAD_REQUEST(4000000, HttpStatus.BAD_REQUEST, "Err"),
    NOT_FOUND(4030000, HttpStatus.NOT_FOUND, "Err"),
    INTERNAL_SERVER_ERROR(5000000, HttpStatus.INTERNAL_SERVER_ERROR, "Err"),

    //01 Server Config
    DATA_ACCESS_ERROR(5000101, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),
    SERVER_DOWN(5000102, HttpStatus.INTERNAL_SERVER_ERROR, "Server is Down"),

    //02 Auth
    UNAUTHORIZED(2000200, HttpStatus.OK, "User unauthorized"),
    TOKEN_SIGNATURE(2000201, HttpStatus.OK, "SignatureException"),
    TOKEN_MALFORMED(2000202, HttpStatus.OK, "MalformedException"),
    TOKEN_EXPIRED(2000203, HttpStatus.OK, "ExpiredException"),
    TOKEN_UNSUPPORTED(2000204, HttpStatus.OK, "UnsupportedException"),
    TOKEN_ILLEGAL_ARGUMENT(2000205, HttpStatus.OK, "IllegalArgumentException"),
    USED_AUTHORIZATION_CODE(2000206, HttpStatus.OK, "Used authorization code"),

    //03 Use
    VALIDATION_ERROR(2000300, HttpStatus.OK, "Validation error"),
    NOT_MATCH_PASSWORD(2000301, HttpStatus.OK, "Not match password"),
    NO_SEARCH_USER(2000302, HttpStatus.OK, "No search user"),
    NOT_ENOUGH_POINT(2000303, HttpStatus.OK, "Not Enough point"),
    NO_SEARCH_ORDER(2000304, HttpStatus.OK, "No search order"),

    //99 test
    TEST_1(4519900, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "UNAVAILABLE_FOR_LEGAL_REASONS");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
//        return Optional.ofNullable(message)
//                .orElse(this.getMessage());
        return Optional.ofNullable(message)
                .filter(v->!v.isEmpty())
                .orElse(this.getMessage());
    }

    public static Code valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return Code.BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return Code.INTERNAL_SERVER_ERROR;
                    } else {
                        return Code.OK;
                    }
                });
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}