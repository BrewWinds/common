//package spring.mvc;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import page.PageHandler;
//import page.PageReqParams;
//
//import java.lang.reflect.Method;
//
///**
// */
//public class PageMethodArgumentResolver implements HandlerMethodArgumentResolver {
//
//    private static final int DEFAULT_SIZE = 10;
//    private static final int MAX_SIZE = 500;
//
//    @Override
//    public boolean supportsParameter(MethodParameter methodParameter) {
//        return PageReqParams.class == methodParameter.getParameterType();
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//
//        PageReqParams page = new PageReqParams();
//
//        nativeWebRequest.getParameterMap().entrySet().forEach(e->{
//            if(page.PAGE_PARAMS.contains(e.getKey())){
//                int val = Integer.parseInt(e.getValue()[0], 0);
//                if( (PageHandler.DEFAULT_LIMIT.equals(e.getKey()) || PageHandler.DEFAULT_PAGE_NUM.equals(e.getKey()))
//                        && val < 1){
//                    invokeMethod(page, "set"+ StringUtils.capitalize(e.getKey()), val);
//                }
//            }else if(PageReqParams.SORT_PARAM.equalsIgnoreCase(e.getKey())){
//                // a desc, b asc
//                String val = StringUtils.join(e.getValue(), ",").trim();
//                invokeMethod(page, "set"+ StringUtils.capitalize(e.getKey()), val);
//            }else{
//                String[] val = e.getValue();
//                page.put(e.getKey(), val.length==1 ? val[0].trim() : val);
//            }
//
//        });
//
//        if(page.getLimit() > MAX_SIZE){
//            page.setLimit(MAX_SIZE);
//        }
//        if(page.getOffset() < 0){
//            page.setOffset(0);
//        }
//        if(page.getPageSize() < 1){
//            page.setPageSize(DEFAULT_SIZE);
//        }
//        if(page.getPageSize() > MAX_SIZE){
//            page.setPageSize(MAX_SIZE);
//        }
//        if(page.getPageNum() < 1){
//            page.setPageNum(1);
//        }
//        return page;
//    }
//
//    public <T> void invokeMethod(T target, String methodName, Object val){
//        try {
//            Method m = target.getClass().getMethod(methodName, null);
//            m.setAccessible(true);
//            m.invoke(target, val);
//        } catch (Exception e) {
//            // log ERROR
//        }
//    }
//
//}
