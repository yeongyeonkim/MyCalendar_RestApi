package minsu.restapi.persistence.service;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.*;
import minsu.restapi.persistence.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class JwtService {
	
	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.expmin}")
	private Long expireMin;
	
	/*
	로그인 성공 시 사용자 정보를 기반으로 JwTToken을 생성해서 반환.
	 */
	public String create(final User user) {
		//log.trace("time: {}", expireMin);
		final JwtBuilder builder = Jwts.builder();

		//claim의 header
		builder.setHeaderParam("typ", "JWT");
		//claim의 body.
		//
		builder.setSubject(user.getEmail())
				.claim("name", user.getName()) //이 부분으로 나중에 get("name")
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*expireMin))
				.claim("User", user)
				.claim("second","부가정보");
		//claims에 get하고싶은 key의 이름과 값을 넣어준다.
		// claims.getheader();
		//그리고 나중에 claims.getBody().get("key name") + ""으로 출력
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

		/*
		Header, payload, verify signature가 필요한데,
		header와 payload는 단순 인코딩 되어 누구라도 디코딩 가능하지만
		verfy signature는 secret key를 알지 못하면 복호화 할 수 없다.
		secret key가 salt가 되는 것.
		 */
		final String jwt = builder.compact();
		//log.debug("토큰발행 : {}", jwt);
//		System.out.println(getUserEmail(jwt)); // test1
//		System.out.println(getUserName(jwt)); // name
		return jwt;
	}

	/*
	전달받은 토큰이 제대로 생성되었는지 확인하고 문제가 있다면 Runtime 예외를 발생시킨다.
	 */
//	public void checkValid(final String jwt) throws Exception {
//		Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
//	}
//
	public boolean checkValid(final String jwt) throws Exception {
		try{
			Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
			return true;
		} catch(ExpiredJwtException exception){
			return false;
		} catch(JwtException exception){
			return false;
		}
	}

	/*
	jwt 토큰을 분석해서 필요한 정보를 반환한다.
	 */
	public Map<String, Object> get(final String jwt) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		} catch(final Exception e) {
			throw new RuntimeException();
		}
		
		//log.trace("claims: {}", claims);
		System.out.println("claims : " + claims);
//		System.out.println("getSignature : " + claims.getSignature());
		System.out.println("getbody : " + claims.getBody());
		return claims.getBody();
	}
	/*
	토큰 만료 확인
	 */
	public Boolean getExpToken(String jwt){
		Jws<Claims> claims = null;
		try{
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
			Date exp = claims.getBody().getExpiration();
			Date now = new Date();
			if(exp.after(now)) return true;
			return false;
		} catch (Exception e){
			return false;
		}
	}
	public String getUserEmail(String jwt) throws RuntimeException{
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
			return claims.getBody().getSubject();
		} catch(Exception e){
			return null;
		}
	}
	public String getUserName(String jwt) throws RuntimeException{
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
			return claims.getBody().get("name") + "";
		} catch (Exception e){
			return null;
		}
	}
}
