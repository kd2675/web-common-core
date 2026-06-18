package web.common.core.response.base.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import web.common.core.response.base.vo.Code;

@Getter
public class ResponseDataDTO<T> extends ResponseDTO {
    private T data;

    @JsonCreator
    public ResponseDataDTO(
            @JsonProperty("success") Boolean success,
            @JsonProperty("code") Integer code,
            @JsonProperty("message") String message,
            @JsonProperty("data") T data
    ) {
        super(success, code, message);
        this.data = data;
    }

    private ResponseDataDTO(T data) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
    }

    private ResponseDataDTO(T data, String message) {
        super(true, Code.OK.getCode(), message);
        this.data = data;
    }

    public static <T> ResponseDataDTO<T> of(T data) {
        return new ResponseDataDTO<>(data);
    }

    public static <T> ResponseDataDTO<T> of(T data, String message) {
        return new ResponseDataDTO<>(data, message);
    }

    public static <T> ResponseDataDTO<T> empty() {
        return new ResponseDataDTO<>(null);
    }
}
