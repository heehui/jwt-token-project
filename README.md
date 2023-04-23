# jwt-token-project(springboot jwt를 이용한 토큰 발급하기)

### 1. 개발 환경
- OS : window 11
- 개발환경 : Java 1.8, SpringBoot
- Tool : IntelliJ, Postman

#

### 2. 프로젝트 구조
<p align="left">
  <img src="https://user-images.githubusercontent.com/78891624/233842219-82cd0714-f783-486f-9c6f-1eb5a21f7d0d.png" width="750" height="200"/>
</p>

#

### 3. 환경설정
#### 3.1 pom.xml
###### - jwt dependency 추가
###### : JWT 토큰 생성,파싱, 검증해주는 라이브러리를 추가한다.
```
<dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt</artifactId>
      <version>0.9.1</version>
</dependency>
```

#

### 4. 토큰 발급
##### 4.1 SecurityServiceImpl.java
###### - SecretKey 만들기
###### : Base64 인코딩를 이용하여 토큰 발급에 필요한 SecretKey를 생성한다.
```
private final String secretKey = Base64.getEncoder().encodeToString("Hello World".getBytes());
```


```
public String createToken(String subject, long ttlMillis) {
    if (ttlMillis <= 0) {
        throw new RuntimeException("토큰 만료기간은 0 이상이어야 합니다.");
    }

    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    JwtBuilder builder = Jwts.builder()
            .setSubject(subject)
            .signWith(signatureAlgorithm, signingKey);
    long nowMillis = System.currentTimeMillis();
    builder.setExpiration(new Date(nowMillis + ttlMillis));
    return builder.compact();
}
```

##### 4.2 HomeController.java
```
```

### 5. 발급받은 토큰 확인
##### 5.1 SecurityServiceImpl.java
```
public String getSubject(String token) {
    Claims claims = Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(token).getBody();
    return claims.getSubject();
}
```

##### 5.3 HomeController.java
```
```

#

### 6. Postman으로 토큰 발급/확인하기

#### 6.1 토큰 발급
##### - http://localhost:8080/security/generate/token?subject=happyToken

<p align="center">
  <img src="https://user-images.githubusercontent.com/78891624/233840667-0ff19555-3e3e-4258-bac5-bb0668aadefd.png" width="850" height="500"/>
</p>

#### 6.2 토큰 확인
##### - http://localhost:8080/security/get/subject?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXBweVRva2VuIiwiZXhwIjoxNjgyMjUyODg0fQ.UBOaMoUtn2b7zPQa4AQGj58oJWxSY1OX4l42cpPcxcc

<p align="center">
  <img src="https://user-images.githubusercontent.com/78891624/233840862-c70795ec-4bb3-4206-8822-64676935c15f.png" width="850" height="500"/>
</p>

#

### 7. JWT 홈페이지에서 발급한 토큰 확인하기
<p align="center">
  <img src="https://user-images.githubusercontent.com/78891624/233840881-79b5d988-f0f6-42d5-a0a8-26d9a5e1b6ec.png" width="850" height="500"/>
</p>
