<template>
    <div>
        <h1>模拟一对一或在同一个群组聊天案例</h1>
        <div>
            <input v-model="content" placeholder="输入消息内容"></input>
            <button @click="send">发送公告</button>
        </div>
        <div class="content-list">
            <p v-for="(contentItem, index) in contentList">来自服务端的消息：{{ contentItem.content }}</p>
        </div>
    </div>
</template>
<script>
import SockJS from 'sockjs-client/dist/sockjs.min.js';
import Stomp from "stompjs";
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
        send() {
            if (this.stompClient) {
                this.stompClient.send(`/app/v2/chat/${this.clientId}`, {}, JSON.stringify({
                    content: this.content,
                    from: "admin",
                    to: "全体成员"
                }));
            }
        },
        init() {
            
            let socket = new SockJS("http://localhost:8080/endpoint-websocket");
            this.stompClient = Stomp.over(socket);
            this.stompClient.heartbeat.outgoing = 10000; //前端对后端进行心跳检测的时长 ms
            this.stompClient.heartbeat.incoming = 0; //后端对前端就行心跳检测的时长 ms
            this.stompClient.debug = null;
            this.stompClient.connect({}, () => {
                console.info("[WebSocket] 连接请求发送成功!");
                //订阅后端数据
                this.stompClient.subscribe(`/topic/reply/${this.clientId}`, (message) => {
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