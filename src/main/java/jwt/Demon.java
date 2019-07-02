package jwt;

import com.google.common.primitives.Bytes;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;

import java.security.Key;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Auther: 01378178
 * @Date: 2019/7/2 11:09
 * @Description:
 */
public class Demon {

    public static void main(String[] args) {
        Demon d = new Demon();
        d.demo1();

//        d.demo4();

        String key = d.getKeyId();
        System.out.println(key.getBytes());

    }


    /**
     * Signed JWTS
     */
    public void demo1(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws = Jwts.builder().setSubject("joe").signWith(key).compact();
        System.out.println(jws);

        Jwts.parser().setSigningKey(key).parseClaimsJws(jws)
                .getBody().getSubject().equals("joe");

        // 1  String encodedHeader = base64URLEncode(header.getBytes(UTF8));
        // 2. String encodedClaims = base64URLEncode(claims.getBytes(UTF8));
        // 3. String concatenated = encodedHeader + "." + encodedClaims
        // 4. Key key = getMySecretKey();
        // 5. byte[] signature = hmacSha256(concatenated, key)
        // 6. String jws = concatenated + "." + base64URLEncode(signature)
    }

    /**
     * creating a jws
     * set jwt header
     */
    public void demo2(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws = Jwts.builder().setHeaderParam("kid", "myKeyId").setSubject("Bob").signWith(key).compact();
    }

    /**
     * Set Claims > body
     */
    public void demo3(){
        // setIssuer, setSubject, setAudience, setExpiration, setNotBefore, SetIssuedAt, setId(jti)
        String jws = Jwts.builder()
                .setIssuer("cjun")
                .setSubject("bob")
                .setAudience("you")
                .setExpiration(new Date(System.currentTimeMillis()+(60*60*1000)))
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .claim("hello", "world")
                .compact();
    }


    Key getKey(){
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    String getKeyId(){
        UUID uuid = UUID.randomUUID();
        String str =  Long.toHexString(uuid.getMostSignificantBits())+Long.toHexString(uuid.getLeastSignificantBits());
        return str;
    }

    String getJws(){
        return Jwts.builder()
                .setIssuer("cjun")
                .setSubject("bob")
                .setAudience("you")
                .setExpiration(new Date(System.currentTimeMillis()+(60*60*1000)))
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .claim("hello", "world")
                .compact();
    }
    /**
     * reading a jws
     */
    public void demo4(){
        Jws<Claims> jws = Jwts.parser().parseClaimsJws(getJws());
        System.out.println(jws.toString());
    }


    /**
     * Multiple Key using KeyResolver
     */
    public void demo5(){
        SigningKeyResolver signingKeyResolver = new MySigningKeyResolver();
        Jwts.parser().setSigningKeyResolver(signingKeyResolver).parseClaimsJws(getJws());
    }

    /**
     * kid, jti
     */
    public void demo6(){
        Key signingKey = getKey();
        String kid = getKeyId();


        String jws = Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, kid)
                .signWith(signingKey)
                .compact();
    }

    /**
     * require specific subject
     */
    public void demo7(){
        try {
            Jwts.parser().requireSubject("jsmith").setSigningKey(getKey()).parseClaimsJws(getJws());
            Jwts.parser().require("myfield", "myRequiredValue").setSigningKey(getKey())
                    .parseClaimsJws(getJws());
        }catch(MissingClaimException mce){

        }catch(IncorrectClaimException ice){

        }
    }

    /**
     * 允许时间差
     */
    public void demo8(){
        long seconds = 3 * 60;
        Jwts.parser().setAllowedClockSkewSeconds(seconds).parseClaimsJws(getJws());
    }

    class MySigningKeyResolver extends SigningKeyResolverAdapter{

        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims) {
            String keyId = header.getKeyId();
            Key key = lookupVerificationKey(keyId);
            return key;
        }

        public Key lookupVerificationKey(String keyId){
            return null;
        }
    }


}
