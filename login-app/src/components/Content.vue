<template>
  <div class="Content">
    <el-card>
      <h1>信息</h1>
      <p><span id="username">UserName:{{username}}</span></p>
      <p><span id="token">Token:{{token}}</span></p>
      <p><span id="expire">Expired:{{expire}}</span></p>
      <p><span id="now">{{time}}</span></p>
      <el-button @click="logout()">登出</el-button>
    </el-card>
  </div>
</template>

<script>
import { localGet, localRemove } from '@/utils'
import router from '@/router/Router.js'
export default {
  name: 'Content',
  components: {
  },
  data: function() {
    return {
      time : '',
      username : '',
      token: '',
      expire: '',
      sesison: null
    }
  },
  created() {
    var session = localGet('session')
    if (session == null)
    {
      router.push('/Login')
    } else
    {
      this.sesison = session
      this.username = session.userName
      this.token = session.authencation
      var expireDate = new Date(session.expiredDate)
      this.expire = expireDate.toDateString() + " " + expireDate.toTimeString();
      this.getTimes()
    }
  },

  methods: {
    getTimes() {
      this.getCurrentTime()
      setInterval(this.getCurrentTime, 1000)
    },

    getCurrentTime() {
      let date = new Date();
      this.time = date.toDateString() + " " + date.toTimeString();
      if (date > this.sesison.expiredDate)
      {
        this.logout()
      }
    },

    logout() {
      localRemove('session')
      router.push('/Login')
    }
  }
}
</script>