<template>
  <div class="login-body">
    <el-card class="login-form">
      <div class="head">
        <h1>登录</h1>
      </div>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="loginForm" label-width="100px" class="login-from">
        <el-form-item label="用户名" prop="username">
          <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="goPath('/Register')">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { reactive, ref, toRefs } from 'vue'
import md5 from 'js-md5'
import { localSet } from '@/utils'
import axios from '@/utils/axios'

export default {
  name: 'Login',
  setup() {
      // test purpose
      // axios.post('/login', {
      //   username: 'name',
      //   passwordMd5: '123344'
      // }).then(res => {
      //   console.log(res)
      //   window.location.href='/#/Register'
      // })
      const loginForm = ref(null)
      const state = reactive({
        ruleForm: {
          username: '',
          password: ''
        },
        checked: true,
        rules: {
          username: [
            { required: 'true', message: '账户不能为空', trigger: 'blur' }
          ],
          password: [
            { required: 'true', message: '密码不能为空', trigger: 'blur' }
          ]
        }
      })
    const submitForm = async () => {
      loginForm.value.validate((valid) => {
        if (valid) {
          axios.post('/login', {
            userName: state.ruleForm.username || '',
            passwordMd5: md5(state.ruleForm.password)
          }).then(res => {
            localSet('token', res)
            window.location.href = '/'
          })
        } else {
          console.log('error submit!!')
          return false;
        }
      })
    }
    const resetForm = () => {
      loginForm.value.resetFields();
    }
    return {
      ...toRefs(state),
      loginForm,
      submitForm,
      resetForm
    }
  },

  methods: {
    goPath(url) {
      this.$router.push(url)
    }
  }
}
</script>

<style scoped>
  .login-body {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    background-color: #fff;
  }

  .head {
    padding: 20px 0 20px 0;
  }
</style>