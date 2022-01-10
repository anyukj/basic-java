# API说明

## 效果说明

### 参数说明 

* `restful api接口规范`
    * **GET | DELETE** 请求参数统一为query
    * **POST | PUT** 请求参数统一为body,提交json格式数据
    
* `时间格式` 入参出参统一使用ISO-8601格式字符串：yyyy-MM-ddTHH:mm:ss，例如: __2020-10-10T16:56:29__
    
---
### token

`token校验` 用户登录成功后获取到token信息，http请求时把token信息放入`header`参数名成为`Authorization`
<font color=red>注意：</font>token有效期为1个小时，过期前可以通过`刷新token接口`获取新的token

---