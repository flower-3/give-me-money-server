package com.givememoney.jwt;

import com.givememoney.service.AuthService;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;


@Component
@Getter
@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private AuthService authService;
    private String secret;
    private final long tokenValidityInMilliseconds;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public TokenProvider(
            @Value("${oauth.jwt.secret}") String secret,
            @Value("${oauth.jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    /* Todo DI 의존관계 해결 생성자 주입으로 변경 */
    @Autowired
    public void setUserService(AuthService authService) {
        this.authService = authService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }


    /*
     * createToken 메소드는 Authentication 객체에 포함되어 있는 권한 정보들을 담은 토큰을 생성
     * jwt.token-validity-in-seconds 값을 이용해 토큰의 만료 시간을 지정합니다.
     */
    public String createToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidityInMilliseconds)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secret)  // 사용할 암호화 알고리즘과
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = authService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    /*
     * 토큰 검증 수행
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parse(token);
            return true;
        } catch (MalformedJwtException | SignatureException e) {
            //log.error("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            //log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            //log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = Arrays.stream(request.getCookies()).filter(cookie -> "Bearer".equals(cookie.getName())).findFirst().get().getValue();
        return bearerToken;
    }
}
