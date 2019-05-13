package com.eappcat.http.client;

import com.alibaba.fastjson.JSONObject;
import okhttp3.ResponseBody;
import okhttp3.*;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SimpleHttpClientProxy implements InvocationHandler {
    private static final String PATH_SEPARATOR ="/";

    private OkHttpClient client;
    private ConfigurableBeanFactory configurableBeanFactory;

    public SimpleHttpClientProxy(OkHttpClient client,ConfigurableBeanFactory configurableBeanFactory){
        this.client=client;
        this.configurableBeanFactory=configurableBeanFactory;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //处理默认的Object方法
        if (method.getDeclaringClass().equals(Object.class)){
            return method.invoke(this,args);
        }
        SimpleClient simpleClient=AnnotationUtils.findAnnotation(method.getDeclaringClass(),SimpleClient.class);
        String baseUrl=simpleClient.baseUrl();
        //使用Spring EL读取url
        StandardBeanExpressionResolver resolver=new StandardBeanExpressionResolver();
        BeanExpressionContext context=new BeanExpressionContext(configurableBeanFactory,null);
        baseUrl=String.valueOf(resolver.evaluate(baseUrl,context));

        //解析RequestMapping
        RequestMapping requestMapping=AnnotationUtils.findAnnotation(method,RequestMapping.class);
        if(requestMapping==null){
            throw new HttpException(-1,"request mapping is missing on method",null);
        }
        if(requestMapping.path().length==0){
            throw new HttpException(-1,"request mapping path is missing on method",null);
        }
        if(requestMapping.method().length==0){
            throw new HttpException(-1,"request mapping method is missing on method",null);
        }


        String contentType= org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
        if(requestMapping.produces().length>0){
             contentType=requestMapping.produces()[0];
        }

        String requestPath=requestMapping.path()[0];
        RequestMethod requestMethod=requestMapping.method()[0];


        Request.Builder builder=new Request.Builder();

        String url=requestPath;
        if(!requestPath.toLowerCase().startsWith("http://")&&!requestPath.toLowerCase().startsWith("https://")){
            if(!requestPath.startsWith(PATH_SEPARATOR)){
                url=baseUrl.concat(PATH_SEPARATOR).concat(requestPath);
            }else {
                url=baseUrl.concat(requestPath);
            }
        }

        if(requestMapping.produces().length>0){
            builder.header("Content-Type",contentType);
        }
        int i=0;
        //process path variable
        for(Parameter parameter:method.getParameters()){
            PathVariable pathVariable=AnnotationUtils.findAnnotation(parameter,PathVariable.class);
            if(pathVariable!=null){
                if(StringUtils.isEmpty(pathVariable.value())){
                    Map<String,Object> map=convertToMap(args[i]);
                    for(String key:map.keySet()){
                        if (!StringUtils.isEmpty(map.get(key))){
                            url=url.replaceAll("\\{"+ Pattern.quote(key)+"\\}",String.valueOf(map.get(key)));
                        }
                    }

                }else {
                    url=url.replaceAll("\\{"+ Pattern.quote(pathVariable.value())+"\\}",String.valueOf(args[i]));
                }
            }
            i++;
        }
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        //process query parameter
        i=0;
        for(Parameter parameter:method.getParameters()){
            RequestParam queryParam=AnnotationUtils.findAnnotation(parameter,RequestParam.class);
            if(queryParam!=null){
                if(StringUtils.isEmpty(queryParam.value())){
                    Map<String,Object> map=convertToMap(args[i]);
                    for(String key:map.keySet()){
                        if (!StringUtils.isEmpty(map.get(key))){
                            urlBuilder.addQueryParameter(key,String.valueOf(map.get(key)));
                        }
                    }
                }else {
                    urlBuilder.addQueryParameter(queryParam.value(),String.valueOf(args[i]));
                }
            }
            i++;
        }
        builder.url(urlBuilder.build());
        //process request header
        i=0;
        for(Parameter parameter:method.getParameters()){
            RequestHeader header=AnnotationUtils.findAnnotation(parameter,RequestHeader.class);
            if(header!=null){
                if(StringUtils.isEmpty(header.value())){
                    Map<String,Object> map=convertToMap(args[i]);
                    for(String key:map.keySet()){
                        if (!StringUtils.isEmpty(map.get(key))) {
                            builder.addHeader(key, String.valueOf(map.get(key)));
                        }
                    }
                }else {
                    builder.addHeader(header.value(),String.valueOf(args[i]));
                }
            }
            i++;
        }
        okhttp3.RequestBody body=okhttp3.RequestBody.create(MediaType.get(contentType),"{}");
        i=0;
        for(Parameter parameter:method.getParameters()){
            RequestBody requestBody=AnnotationUtils.findAnnotation(parameter,RequestBody.class);
            if(requestBody!=null){
                if(args[i]!=null){
                    if(parameter.getType().equals( okhttp3.RequestBody.class)){
                        body = (okhttp3.RequestBody)args[i];
                    }else {
                        MediaType mediaType=MediaType.get(contentType);
                        switch (mediaType.subtype()){
                            case "json":
                                body= okhttp3.RequestBody.create(MediaType.get(contentType),JSONObject.toJSONString(args[i]));
                                break;
                            case "x-www-form-urlencoded":
                                body=toUrlEncoded(args[i]);
                                break;
                            case "form-data":
                                body=toFormData(args[i]);
                                break;
                            default:
                                body= okhttp3.RequestBody.create(MediaType.get(contentType),String.valueOf(args[i]));
                        }
                    }
                }
                break;

            }
            i++;
        }
        builder.tag(method.getDeclaringClass());
//        if(requestMapping.method().equals(RequestMethod.POST)||requestMapping.method().equals(RequestMethod.PUT)){
//            if()
//        }
        builder.method(requestMethod.toString(),body);

        Call call=client.newCall(builder.build());

        Response response=call.execute();

        if(response.isSuccessful()){
            if(method.getReturnType().equals(String.class)){
                return response.body().string();
            }else if(method.getReturnType().equals(ResponseBody.class)){
                return response.body();
            } else if(method.getReturnType().equals(JSONObject.class)){
                return JSONObject.parseObject(response.body().string());
            } else {
                return JSONObject.parseObject(response.body().string()).toJavaObject(method.getGenericReturnType());
            }

        }else {
            throw new HttpException(response.code(),response.message(),response.body().string());
        }
    }

    private okhttp3.RequestBody toFormData(Object arg) {
        MultipartBody.Builder builder=new MultipartBody.Builder();
        Map beanMap=convertToMap(arg);
        for (Object key:beanMap.keySet()){
            builder.addFormDataPart(key.toString(),beanMap.getOrDefault(key,"").toString());
        }
        return builder.build();
    }

    private Map convertToMap(Object arg) {
        if(arg instanceof Map){
            return (Map)arg;
        }
        Map test=new HashMap();
        BeanMap map=new BeanMap(arg);
        test.putAll(map);
        test.remove("class");
        return test;
    }

    private okhttp3.RequestBody toUrlEncoded(Object arg) {
        FormBody.Builder builder=new FormBody.Builder();
        Map beanMap=convertToMap(arg);
        for (Object key:beanMap.keySet()){
            builder.add(key.toString(),beanMap.getOrDefault(key,"").toString());
        }
        return builder.build();
    }
}
