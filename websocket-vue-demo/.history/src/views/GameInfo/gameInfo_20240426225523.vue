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
        }
    }
}
</script>
<style scoped>
</style>