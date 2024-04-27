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
                // 跨域请求时携带cookie,且只有在前端配置了withCredentials，后端通过addCookie添加，浏览器才可以正常写入cookie
                withCredentials: true
            });
            instance.get("http://127.0.0.1:8080/api/v1/cookie/test")
        },
    }
 }
</script>