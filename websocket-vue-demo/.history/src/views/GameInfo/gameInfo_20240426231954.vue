<template>
    <div>
        游戏公告案例展示
    </div>
</template>
<script>
import SockJS from "sockjs-client";
import Stomp from "stompjs";
export default {
    data() {
        return {
            stompClient: null
        }
    },
    mounted() {
        this.init();
    }
}
</script>