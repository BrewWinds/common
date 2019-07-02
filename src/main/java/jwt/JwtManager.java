package jwt;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 01378178
 * @Date: 2019/7/2 14:35
 * @Description:
 */
public class JwtManager {

    private int jwtExpSecs;
    private int jwtRefreshSecs;
    public ConcurrentHashMap<byte[], byte[]> jtiStore = new ConcurrentHashMap<>();
    private static final byte[] JTI_KEY_PREFIX = "jti:".getBytes();

    public String create(String subject){

        byte[] jti = uuid();
        byte[] secret = new SecureRandom(uuid()).generateSeed(24);

        String jwt = Jwts.builder()
                .setHeaderParam("jti", Base64.getUrlEncoder().withoutPadding().encodeToString(jti))
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpSecs))
                .claim("rfh", System.currentTimeMillis()+ jwtRefreshSecs)
                .compact();

        jtiStore.putIfAbsent(jti, secret);
        return jwt;
    }


    private byte[] extractJti(String jwt){
        int index;
        if(jwt==null || (index = jwt.indexOf(JwtParser.SEPARATOR_CHAR)) == -1){
            throw new RuntimeException("");//TODO
        }

        byte[] headerBytes = Base64.getUrlDecoder().decode(jwt.substring(0, index));
        Map<String, Object> header = (Map<String, Object>) JSON.parse(headerBytes);
        byte[] jti = Base64.getUrlDecoder().decode((String) header.get("jit"));
        if(jti == null || jti.length == 0){
            throw new RuntimeException("");
        }
        return jti;
    }


    public Jws<Claims> verify(String jwt) throws InvalidJwtException {
        byte[] jti = extractJti(jwt);
        byte[] secret = jtiStore.get(jti);
        if(secret==null || secret.length==0){
            throw new RuntimeException("Jti not found");
        }

        try {

            Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);

            long refresh = (long) jws.getBody().get("rfh");
            if (refresh < System.currentTimeMillis()) {

                // store jws
                String newlyJwt = create(jws.getBody().getSubject());
                jws.getBody().put("renew-jwt", newlyJwt);

                jtiStore.remove(jti);
            }

            return jws;

        }catch(SignatureException e){
            throw new InvalidJwtException(e.getMessage());
        }

    }

    public int getJwtExpSecs() {
        return jwtExpSecs;
    }

    public void setJwtExpSecs(int jwtExpSecs) {
        this.jwtExpSecs = jwtExpSecs;
    }

    public int getJwtRefreshSecs() {
        return jwtRefreshSecs;
    }

    public void setJwtRefreshSecs(int jwtRefreshSecs) {
        this.jwtRefreshSecs = jwtRefreshSecs;
    }

    public byte[] withPrefix(byte[] source){
        return Bytes.concat(JTI_KEY_PREFIX, source);
    }

    public static byte[] uuid() {

        UUID uuid = UUID.randomUUID();

        long most  = uuid.getMostSignificantBits(),
                least = uuid.getLeastSignificantBits();

        return new byte[] {
                (byte) (most  >>> 56), (byte) (most  >>> 48),
                (byte) (most  >>> 40), (byte) (most  >>> 32),
                (byte) (most  >>> 24), (byte) (most  >>> 16),
                (byte) (most  >>>  8), (byte) (most        ),

                (byte) (least >>> 56), (byte) (least >>> 48),
                (byte) (least >>> 40), (byte) (least >>> 32),
                (byte) (least >>> 24), (byte) (least >>> 16),
                (byte) (least >>>  8), (byte) (least       )
        };
    }

}


