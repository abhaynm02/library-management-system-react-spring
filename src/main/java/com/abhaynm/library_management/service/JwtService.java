package com.abhaynm.library_management.service;




import com.abhaynm.library_management.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private  final  String SECRET_KEY ="c532053aa9f71b95a110771797b42a3422ebc1d9feb58fecf08d453ffc644e10";

    public  String extractUsername(String token){
        return  extractClaim(token,Claims::getSubject);
    }
    public boolean isValid(String token, UserDetails user){
        String username=extractUsername(token);
        return ( username.equals(user.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private  Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);
    }
     private Claims extractAllClaims(String token){
         return Jwts
                 .parser()
                 .verifyWith(getSigningKey())
                 .build()
                 .parseSignedClaims(token)
                 .getPayload();
     }

    public String generateToken(UserEntity user){
        String token= Jwts
                .builder()
                .subject(user.getUsername())
                 .claim("role", user.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(getSigningKey())
                .compact();
        return token;
    }
    private SecretKey getSigningKey(){
        byte [] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(keyBytes);
    }
}
