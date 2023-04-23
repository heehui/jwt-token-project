package co.kr.tokenworld.jwtProject.service;


public interface SecurityService {
    String createToken(String subject, long ttlMillis);
    String getSubject(String token);
}
