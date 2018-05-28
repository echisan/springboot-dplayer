# springboot-dplayer
使用java实现的dplayer弹幕接口后端
## 环境依赖
* jdk 1.8
* mongodb
* redis
* maven

### api: http://api.echisan.cn/dplayer/
### 演示地址: http://dplayer.echisan.cn/

## 安装
打包使用maven，所以需要先安装好maven

在根目录下，即有pom.xml文件的目录下执行
```
mvn package -Dmaven.test.skip=true
```

默认打包成jar，想打包成war的可自行搜索

## 运行
```java
java -jar springboot-player.jar
```

## 使用
不支持获取b站弹幕,其他用法一致
```javascript
const dp = new DPlayer({
            container:document.getElementById('dplayer'),
            video:{
                url:'http://danmaku.echisan.cn/static/video/the-day.mp4'
            },
            danmaku:{
		id:'thedaydanmaku',
                api:'http://api.echisan.cn/dplayer/',
		user:'echisan'
            }
        });
```

***

## update log
* 新增了基于jwt的token验证，需要把token放到请求头上，请求头key为`Authorization`，值为`Bearer {token}`

token
```json
# header
{
  "typ": "JWT",
  "alg": "HS512"
}
# payload
{
  "loc": 0,
  "sub": "gaygui2",
  "ema": 0,
  "iss": "DMCollection",
  "exp": 1527415398,
  "iat": 1526983398,
  "rol": "ROLE_USER"
}
```
* 修复了发弹幕时可能会出现的413的问题
