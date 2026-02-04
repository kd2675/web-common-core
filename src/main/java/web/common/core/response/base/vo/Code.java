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

    OK(2000000, HttpStatus.OK, "Ok"),
    OK_ASYNC(2020000, HttpStatus.ACCEPTED, "Accepted"),

    //00 default
    BAD_REQUEST(4000000, HttpStatus.BAD_REQUEST, "Bad request"),
    FORBIDDEN(4030000, HttpStatus.FORBIDDEN, "Forbidden"),
    NOT_FOUND(4040000, HttpStatus.NOT_FOUND, "Not found"),
    CONFLICT(4090000, HttpStatus.CONFLICT, "Conflict"),
    INTERNAL_SERVER_ERROR(5000000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    //01 Server Config
    DATA_ACCESS_ERROR(5000101, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),
    SERVER_DOWN(5000102, HttpStatus.INTERNAL_SERVER_ERROR, "Server is down"),

    //02 Auth
    UNAUTHORIZED(4010200, HttpStatus.UNAUTHORIZED, "Unauthorized"),
    TOKEN_SIGNATURE(4010201, HttpStatus.UNAUTHORIZED, "Invalid token signature"),
    TOKEN_MALFORMED(4010202, HttpStatus.UNAUTHORIZED, "Malformed token"),
    TOKEN_EXPIRED(4010203, HttpStatus.UNAUTHORIZED, "Expired token"),
    TOKEN_UNSUPPORTED(4010204, HttpStatus.UNAUTHORIZED, "Unsupported token"),
    TOKEN_ILLEGAL_ARGUMENT(4010205, HttpStatus.UNAUTHORIZED, "Illegal token argument"),
    USED_AUTHORIZATION_CODE(4090206, HttpStatus.CONFLICT, "Used authorization code"),

    //03 Use
    VALIDATION_ERROR(4000300, HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_MATCH_PASSWORD(4010301, HttpStatus.UNAUTHORIZED, "Password does not match"),
    NO_SEARCH_USER(4040302, HttpStatus.NOT_FOUND, "User not found"),
    NOT_ENOUGH_POINT(4090303, HttpStatus.CONFLICT, "Not enough points"),
    NO_SEARCH_ORDER(4040304, HttpStatus.NOT_FOUND, "Order not found"),

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
