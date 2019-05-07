<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ name }}</div>
    <div class="dashboard-text">time: {{ time }}</div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import Stomp from 'stompjs'

  export default {
    name: 'Dashboard',
    data() {
      return {
        time: '',
      }
    },
    computed: {
      ...mapGetters([
        'name'
      ])
    },
    created() {
      this.connect();
    },
    methods: {
      onConnected(frame) {
        console.log("Connected: " + frame);
        var queue = "/queue/time";
        this.client.subscribe(queue, this.responseCallback, this.onFailed);
        console.log(frame)
      },
      onFailed(frame) {
        console.log("Failed: " + frame);
      },
      responseCallback(frame) {
        console.log("得到的消息 msg=>" + frame.body);
        this.time = frame.body
        // console.log(frame)
        //接收到服务器推送消息，向服务器发送确认消息
        // this.client.send("/exchange/exchange_pushmsg/rk_recivemsg", {"content-type": "text/plain"}, frame.body);
      },
      connect() {
        this.client = Stomp.client("ws://10.4.82.184:30965/ws")
        var headers = {
          "login": "guest",
          "passcode": "guest",
          //虚拟主机，默认“/”
          "host": "/",
          "heart-beat": "0,0"
        };
        this.client.connect(headers, this.onConnected, this.onFailed);
      },
    }
  }
</script>

<style lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }

    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>
