声明式Http客户端
-------
一个用OKHttp实现的类似于Feign Client的声明式客户端。

示例代码
```java
@SimpleClient(baseUrl = "https://www.baidu.com")
public interface TestClient {
    @RequestMapping(method = RequestMethod.GET,path = "/s",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String test(@RequestParam("wd") String test);
}
```

最终的请求：

```http request
GET https://www.baidu.com/s?wd=aaa HTTP 1.1

```
