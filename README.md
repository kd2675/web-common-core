# web-common-core

백엔드 서비스 전반에서 공통으로 사용하는 웹 라이브러리입니다. 응답 래퍼, 공통 예외, 코드값, HTTP/날짜/이미지/쿠키 등 범용 유틸리티를 제공합니다.

## 역할
- 공통 응답 DTO 제공
- 공통 예외/코드 체계 제공
- 요청/쿠키/UUID/이미지 등 유틸리티 제공
- 서비스별 비즈니스 로직을 넣지 않는 공유 모듈

## 주요 패키지
- `web.common.core.request`
- `web.common.core.response.base`
- `web.common.core.utils`

## 대표 파일
- `src/main/java/web/common/core/response/base/dto/ResponseDataDTO.java`
- `src/main/java/web/common/core/response/base/dto/ResponseErrorDTO.java`
- `src/main/java/web/common/core/response/base/vo/Code.java`
- `src/main/java/web/common/core/response/base/exception/GeneralException.java`

## 빌드
```bash
./gradlew :web-common-core:compileJava
./gradlew :web-common-core:test
```

## 참고
- 라이브러리 모듈이라 `bootRun` 대상이 아닙니다.
- 실제 사용처 영향은 `auth-common-core`, `auth-back-server`, `zeroq-back-service`, `muse-back-service`, `zeroq-back-sensor`, `zeroq-sensor-gateway`, `semo-back-service` 쪽에서 확인하는 편이 안전합니다.
