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
        }
    }
}
</script>
<style scoped>
</style>