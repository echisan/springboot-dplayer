# springboot-dplayer
使用java实现的dplayer弹幕接口后端
## 环境依赖
* jdk 1.8
* mongodb
* redis

### api: http://api.echisan.cn/dplayer/
### 演示地址: http://dplayer.echisan.cn/

## 使用
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
