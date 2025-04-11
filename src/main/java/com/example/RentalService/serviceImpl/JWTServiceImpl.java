package com.example.RentalService.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.RentalService.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService{

	@Value("${jwt.secret}")
    private String secretkey;

    public JWTServiceImpl() {

    }


      @Override
	  public String generateToken(String userId, String username, String role) throws IllegalArgumentException{
    	
    	if(userId==null || username==null || role==null) {
    		 throw new IllegalArgumentException("Please provide all the details.");
    	}
    	 
    	Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);  // ✅ Add user_id to claims
        claims.put("role", role);       // ✅ Keep role as a claim

        return Jwts.builder()
                .claims(claims)
                .subject(username)  // Keep only username as subject
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 30))) // 30 hours
                .signWith(getKey())
                .compact();
    }
      
      
      @Override
	  public String generateToken(String useremail) throws IllegalArgumentException{
    	
    	if(useremail==null) {
    		 throw new IllegalArgumentException("Please provide all the details.");
    	}
    	 
    	Map<String, Object> claims = new HashMap<>();
        claims.put("email", useremail);  // ✅ Add user_id to claims

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 10)))
                .signWith(getKey())
                .compact();
    }
    
  /**
   * Retrieves the HMAC secret key used for signing or verifying JWT tokens.
   * 
   * This method decodes a base64-encoded secret key (provided as a string) and returns 
   * a SecretKey instance that can be used in HMAC algorithms for cryptographic operations.
   * 
   * @return A SecretKey object that can be used for HMAC-based cryptographic operations.
   */
   private SecretKey getKey() {
      byte[] keyBytes = Decoders.BASE64.decode(secretkey);
      return Keys.hmacShaKeyFor(keyBytes);
   }

    

    @Override
	public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Extract specified claim from token.
     * @param token token from which claim is to be extracted
     * @param claimResolver function that extracts desired claim from claims object
     * @param <T> the type of the claim to be extracted.
     * @return the extracted claim of type T. 
     * */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     *  extract all claims from token
     *  @param token token from which claims are to be extracted.
     *  @return all the extracted claims.
     * */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

   
    @Override
	public boolean validateToken(String token, UserDetails userDetails) throws IllegalArgumentException{
    	if(token==null || userDetails==null) {
   		 throw new IllegalArgumentException("Please provide all the details.");
    	}
    	
    	final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
