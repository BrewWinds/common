package reactor;

import java.util.*;

/**
 * @Auther: 01378178
 * @Date: 2019/7/1 11:01
 * @Description:
 */
public enum SecurityProtocol {
    PLAINTEXT(0, "PLAINTEXT"),
    SSL(1, "SSL"),
    SASL_PLAINTEXT(2, "SASL_PLAINTEXT"),
    SASL_SSL(3, "SASL_SSL");

    public final short id;
    public final String name;

    SecurityProtocol(int id, String name){
        this.id = (short) id;
        this.name = name;
    }

    private static final Map<Short, SecurityProtocol> CODE_TO_SECURITY_PROTOCOL;
    private static final List<String> NAMES;

    static {
        SecurityProtocol[] protocols = SecurityProtocol.values();
        List<String> names = new ArrayList<>(protocols.length);

        Map<Short, SecurityProtocol> codeToSecurityProtocol = new HashMap<>();
        for(SecurityProtocol proto : protocols){
            codeToSecurityProtocol.put(proto.id, proto);
            names.add(proto.name);
        }

        CODE_TO_SECURITY_PROTOCOL = Collections.unmodifiableMap(codeToSecurityProtocol);
        NAMES = Collections.unmodifiableList(names);
    }

    public static List<String> names(){
        return NAMES;
    }

    public static SecurityProtocol forId(short id){
        return CODE_TO_SECURITY_PROTOCOL.get(id);
    }

    public static SecurityProtocol forName(String name){
        return SecurityProtocol.valueOf(name.toUpperCase(Locale.ROOT));
    }
}
