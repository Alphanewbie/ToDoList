# Key 잃어버렸을때

.jks 이 저장되어 있는 디렉토리에 가서 CLI창 오픈

```
keytool -export -rfc -alias "키 저장소 이름" -file "PEM 파일 생성 이름".pem -keystore "키 이름".jks
```

예를 들면 나의 경우에는 키 생성할때 키저장소 이름을 key0로 했다.

```
keytool -export -rfc -alias key0 -file upload_certificate.pem -keystore masterkey.jks
```

그리고 비밀 번호는 위와 아래 값 중에 위에 값