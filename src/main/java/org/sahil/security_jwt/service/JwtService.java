package org.sahil.security_jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService {
    private final static String SIGNIN_KEY="ef85b4b8147d30e9628bb923218a9de55b2bdc88e5ff2fbfb38e3ff5a158e6bbfebbfc39d68507ad79457a8ab745717eecb4befe9e19ade0332dc5308e05058b7757a2ae6c2ec88f06a2f4a2f8659cd9699fd4969b97563f2676709b2339b21b9a2d4ef0a8e9f484a91d0a29a86e74c07c9b084f4e5666010fb21810a949e8a9db08d3449ddd586df1cfe7a5094707e9d36c3d9b6076fc3e159604688c97f60c19e69c3778d2e891449aefbe4a5d90625bdbc6ffe31ac3f6a527a82c48d9ffbfb6bed8bf8445c79187446dd0694fa0df0183ce4f6c6d006c54b96ae157bfb54357fe01cf805d4fd34292dd871ed21cbe06a23d2163433ea73727b995d89ff44d";


    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);

    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
      return    Jwts
                .builder()
                .setClaims(extraClaims)//Claims are pieces of information about the user or the token itself.
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails ){
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T>claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts
               .parserBuilder()
               .setSigningKey(getSignInKey())
               .build()
               .parseClaimsJws(token)
               .getBody();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNIN_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
