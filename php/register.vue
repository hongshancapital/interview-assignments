<template>
  <input placeholder="请输入用户名" v-model="user.username"></input>
  <input placeholder="请输入密码" v-model="user.password"></input>
  <input placeholder="再次输入密码" v-model="user.repeatPassword"></input>
  <button class="cu-btn shadow-blur" @click="search">一键注册</button>
</template>
<script>
import system from '@/common/axios/user.js';

  export default {
    data() {
      return {
        user: {username:null, password:null, repeatPassword: null}
      }
    },
    methods: {
      register() {
        user.register(this.user)
        .then((res) => {
          if (res.code === 200) {
            uni.showToast({
              icon: 'none',
              title: res.message,
              duration: 1500
            })

            return true
          }

          uni.showToast({
            icon: 'error',
            title: res.message,
            duration: 2000
          })
        })
      }
    }
  }
</script>