<template>
    <div>
        <button @click="endSetCookie">前端直接设置cookie</button>
        <button @click="cookieAddTest">跨域cookie存储测试(后端接口处理跨域，前端未代理)</button>
        <h1>模拟一对一或在同一个群组聊天案例</h1>
        <div>
            <input v-model="content" placeholder="输入消息内容"></input>
            <button @click="send">发送公告</button>
            <button @click="disconnect">断开连接</button></button>
        </div>
        <div class="content-list">
            <p v-for="(contentItem, index) in contentList">来自服务端的消息：{{ contentItem.content }}</p>
        </div>
    </div>
</template>
<script>
import SockJS from 'sockjs-client/dist/sockjs.min.js';
import Stomp from "stompjs";
import Axios from 'axios';
export default {
    data() {
        return {
            clientId: '123456',
            content: '',
            stompClient: null,
            contentList: []
        }
    },
    created() {
        //客户端标识
        this.clientId = this.$uuid.v1();
        this.init();
    },
    methods: {
        endSetCookie() {
            this.$cookies.set("token", "前端设置的cookie", { expires: "7D" });
        },
        cookieAddTest() { 
            const instance = Axios.create({
                // 跨域请求时携带cookie,并且后端在跨域情况下，只有在前端配置了withCredentials，浏览器才可以正常写入cookie
                withCredentials: true
            });
            instance.get("http://127.0.0.1:8080/api/v1/cookie/test")
        },
        send() {
            if (this.stompClient) {
                //开启事务会发送更多的数据贞，性能不上最优
                //根据测试结果，即使开启事务，并中止掉，客户端依旧会收到消息的回复
                // var tx = this.stompClient.begin();
                // let headers = { transaction: tx.id };
                let headers = {};
                // STOMP消息的body必须为字符串。如果你需要发送 / 接收JSON对象，你可以使用JSON.stringify()和JSON.parse()去转换JSON对象。
                this.stompClient.send(`/app/v3/chat/${this.clientId}`, headers, JSON.stringify({
                    content: this.content,
                    from: "admin",
                    to: "全体成员"
                }));
                //事务提交
                // tx.abort();
            }
        },
        init() {
            //socketjs要用http或https协议进行连接(SockJS包装的Websocket)
            let socket = new SockJS("http://127.0.0.1:8080/endpoint-websocket", null, {
                noCredentials:false
            });
            // STOMP javascript 客户端会使用 ws://的URL与STOMP 服务端进行交互
            // 利用 Stomp.over(ws)这个方法可以使用其他类型的 WebSockets(比如socketjs)，这个方法得到一个满足WebSocket定义的对象
            this.stompClient = Stomp.over(socket);
            //原生websocket类使用ws协议
            // var url = "ws://localhost:61614/stomp";
            // var client = Stomp.client(url);
            this.stompClient.heartbeat.outgoing = 10000; //前端对后端进行心跳检测的时长 ms
            this.stompClient.heartbeat.incoming = 0; //后端对前端就行心跳检测的时长 ms
            this.stompClient.debug = null;
            let headers = {}
            this.stompClient.connect(headers, () => {
                console.info("[WebSocket] 连接请求发送成功!");
                //订阅后端数据（客户端手动确认）
                // this.stompClient.subscribe(`/topic/reply/${this.clientId}`, (message) => {
                //     console.log(message);
                //     // message.ack();
                //     this.contentList.push(JSON.parse(message.body));
                // }, { ack: 'client' });
                // return true;
                //订阅后端数据（服务端默认认定消息一定可达）
                this.stompClient.subscribe(`/topic/v3/reply/${this.clientId}`, (message) => {
                    console.log(message);
                    this.contentList.push(JSON.parse(message.body));
                });
                return true;
            }, () => {
                // 断开连接
                console.log("连接请求发送失败");
            });
        }
    },
    beforeUnmount() {
        console.log('取消订阅');
        this.stompClient.disconnect();  //离开路由之后断开websocket连接
    }
}
</script>
<style scoped>
.content-list {
    height: 100px;
    overflow-y: scroll;
    border: 1px solid red;
}
</style>