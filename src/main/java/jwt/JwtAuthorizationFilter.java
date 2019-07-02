package jwt;

import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import web.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;

/**
 * @Auther: 01378178
 * @Date: 2019/7/2 15:50
 * @Description:
 */
public class JwtAuthorizationFilter extends AuthorizationFilter {


    private String loginAction;
    private String logoutAction;
    private boolean loginWithCaptcha;
    private boolean passwordEncrypt;
    private String successUrl;

    private JwtManager jwtManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestURI = super.getPathWithinApplication(servletRequest);

        Jws<Claims> jws;

        try {
            jws = jwtManager.verify(WebUtils.getCookie(req, WebUtils.AUTH_COOKIE));

        }catch(Exception e){
            String returnUrl = req.getServletPath();
            if(StringUtils.isNotBlank(req.getQueryString())){
                returnUrl += "?" + req.getQueryString();
            }
            return redirectToLogin(req, resp, returnUrl, "302", "请登录");
        }

        String username = jws.getBody().getSubject();
        String validateRs = null;
        if(!userValidate(username, validateRs)){
            return response(req, resp, "401", validateRs, super.getUnauthorizedUrl());
        }

        // TODO here id should be user id
        if(urlPermissionValidate(requestURI, -1L)){
            return response(req, resp, "401", "未授权", super.getUnauthorizedUrl());
        }

        String renewJwt = (String) jws.getBody().get("renew_jtw");
        if(renewJwt != null){
            WebUtils.addCookie(
                    resp, WebUtils.AUTH_COOKIE, renewJwt, "/", jwtManager.getJwtExpSecs()
            );
        }

        WebContextHolder.currentUser();
        return true;
    }

    boolean urlPermissionValidate(String reqURI, Long id){
        return true;
    }

    boolean userValidate(String username, String rsCode){
        return true; // to implement
    }


    public boolean redirectToLogin(HttpServletRequest req, HttpServletResponse resp,
                                   String returnUtl, String resultCode, String msg) throws IOException {

        boolean isRootPath = StringUtils.isBlank(returnUtl) || "/".equals(returnUtl);
        String redirectUrl = isRootPath ? super.getLoginUrl() : buildUrlPaht(super.getLoginUrl(), "UTF-8",
                ImmutableMap.of("return_url", returnUtl));

        return response(req, resp, redirectUrl, resultCode, msg);
    }

    public boolean response(HttpServletRequest req, HttpServletResponse resp,
                            String rc, String msg, String redirectUtl) throws IOException {
        if(WebUtils.isAjax(req)){
            WebUtils.respJson(resp, new Result<>(rc, msg, redirectUtl));
        }else{
            String url = WebUtils.getContextPath(req)+ redirectUtl;
            if(redirectUtl.equals(successUrl) || !rc.equals("200")){
                url = buildUrlPaht(url, "UTF-8", ImmutableMap.of("msg", msg));
            }
            resp.sendRedirect(resp.encodeRedirectURL(url));
        }
        return false;
    }

    class Result<T> {
        private String code;
        private String msg;
        public T data;

        public Result(String code, String msg, T data){
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public static String buildUrlPaht(String url, String encoding, Map<String, ?> params) throws UnsupportedEncodingException {
        if(params == null || params.isEmpty()){
            return url;
        }
        return url + (url.indexOf("?") == -1 ? '?' : '&') + buildParams(params, encoding);
    }

    public static String buildParams(Map<String, ?> params, String encoding) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        String[] values;
        Object value;

        for(Map.Entry<String, ?> entry : params.entrySet()){
            value = entry.getValue();
            if(value != null && value.getClass().isArray()){
                values = new String[Array.getLength(value)];
                for(int length = values.length, i=0; i<length; i++){
                    values[i] = Objects.toString(Array.get(value, i), "");
                }
            }else{
                values = new String[]{Objects.toString(entry.getValue(), "")};
            }

            for(String val : values){
                builder.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(val, encoding))
                        .append("&");
            }
        }

        if(builder.length() >0){
            builder.setLength(builder.length() - 1);
        }

        return builder.toString();

    }
}
