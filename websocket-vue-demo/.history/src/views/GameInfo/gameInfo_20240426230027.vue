<template>
    <div>

    </div>
</template>
<script>
import SockJS from "sockjs-client";
import Stomp from "stompjs";
export default {
    data() { 
        return {
            stompClient:null
        }
    },
    methods: {
        init() { 
            let socket = new SockJS("/websocket/ws/sockjs");
            this.stompClient = Stomp.over(socket);
            this.stompClient.heartbeat.outgoing = 10000; //前端对后端进行心跳检测的时长 ms
            this.stompClient.heartbeat.incoming = 0; //后端对前端就行心跳检测的时长 ms
            this.stompClient.debug = null;
            this.stompClient.connect({}, () => { 
                console.info("[WebSocket] 连接请求发送成功!");
                this.stompClient.subscribe(this.subscribeUrl, (message) => {
                    //后端ws推送的数据
                    let data = this.getRealJsonData(message.body);
                    console.log(data, 'data--');//打印后端推送的数据，根据你需要的数据进行编写
                    //拷贝ws中data到页面属性
                    for (let item in data) {
                        this.formDevice[item] = data[item];
                    }
                    //JSON 支持
                    //STOMP 帧的 body 必须是 string 类型，若希望接收/发送 json 对象，
                    //可通过 JSON.stringify() and JSON.parse() 实现；
                    if (JSON.stringify(data) != '{}') {
                    }
          });

        return true;
            })
        }
    }
}
</script>
<style scoped>
</style>