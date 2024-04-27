<template>
    <div>
        <button @click="endSetCookie">前端直接设置cookie</button>
        <button @click="cookieAddTest">跨域cookie存储测试(后端接口处理跨域，前端未代理)</button>
    </div>
</template>
<script>
import Axios from 'axios';
export default {
    data() { 
        return {

        }
    },
    methods: {
        endSetCookie() {
            this.$cookies.set("token", "前端设置的cookie", { expires: "7D" });
        },
        cookieAddTest() {
            const instance = Axios.create({
                // 跨域请求时携带cookie,且只有在前端配置了withCredentials，服务端通过addCookie添加，浏览器才可以正常写入cookie
                // 即使配置withCredentials，前端请求的服务端接口域名是a.b.com,服务端在设置cookie时，domain默认是a.b.com，但是这时当前的浏览器页面域名是a.c.com
                //这时就会导致cookie在浏览器端设置失效，或者首次设置成功，刷新浏览器，就会清除cookie
                withCredentials: true
            });
            instance.get("http://127.0.0.1:8080/api/v1/cookie/test")
        },
    }
 }
</script>