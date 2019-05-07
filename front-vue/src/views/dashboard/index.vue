<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ name }}</div>
    <div class="dashboard-text">time: {{ time }}</div>

    <div id="tab">
      <div id="Tradion">
        <div id="hours" class="tran"></div>
        <div id="minutes" class="tran"></div>
        <div id="seconds" class="tran"></div>
        <div id="dian"></div>

      </div>
      <h1 id="aTime">13:58</h1>
      <h3 id="aDate"></h3>
      <h2 id="week"></h2>
    </div>

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
        // console.log("Connected: " + frame);
        var queue = "/exchange/time-exchange";
        // var queue = "/exchange/exchange.out/nihaoa";
        this.client.subscribe(queue, this.responseCallback, this.onFailed);
        // console.log(frame)
      },
      onFailed(frame) {
        // console.log("Failed: " + frame);
      },
      responseCallback(frame) {
        // console.log("得到的消息 msg=>" + frame.body);
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
<style type="text/css">
  #tab {
    width: 500px;
    height: 200px;
    /*background:-webkit-gradient(linear,center top,center bottom,from(blue), to(white));*/
    background: -webkit-linear-gradient(top, #007fff, #84bff9); /*css3设置渐变*/
    margin-bottom: 100px;
  }

  #aTime {
    color: #fff;
    /*text-align: center;*/
    /*line-height: 200px;*/
    float: left;
  }

  #Tradion {
    width: 100px;
    height: 100px;
    border: 2px solid #fff;
    border-radius: 100px;
    float: left;
    margin: 50px 50px;
  }

  #hours {
    width: 30px;
    height: 2px;
    margin: 50px 50px;
    background: #fff;
    transform-origin: left bottom;
  }

  #minutes {
    width: 38px;
    height: 2px;
    background: #fff;
    margin: -50px 50px;
    transform-origin: left bottom;
    transform: rotate(0deg);
  }

  #seconds {
    width: 45px;
    height: 1px;
    background: #fff;
    margin: 50px 50px;
    transform-origin: left bottom;
    transform: rotate(0deg);

  }

  .tran {
    transform: rotate(-90deg); /*这里测试了一下旋转角*/
  }

  #dian {
    width: 6px;
    height: 6px;
    border-radius: 6px;
    background: #fff;
    margin: -55px 46px;
  }

  #aDate, #week {
    color: #fff;
    padding-top: 50px;
  }
</style>
