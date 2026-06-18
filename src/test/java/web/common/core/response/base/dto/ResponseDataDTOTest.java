package web.common.core.response.base.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseDataDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void responseDataDTO_jsonWithGenericData_deserializes() throws Exception {
        String json = """
                {
                  "success": true,
                  "code": 200,
                  "message": "OK",
                  "data": {
                    "userKey": "user-1",
                    "username": "harry"
                  }
                }
                """;

        ResponseDataDTO<TestUser> response = objectMapper.readValue(
                json,
                new TypeReference<ResponseDataDTO<TestUser>>() {
                }
        );

        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("OK");
        assertThat(response.getData()).isEqualTo(new TestUser("user-1", "harry"));
    }

    private record TestUser(String userKey, String username) {
    }
}
