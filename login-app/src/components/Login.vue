<template>
  <div class="Login">
    <el-card>
      <h1>登录</h1>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="loginForm" label-width="100px" class="login-from">
        <el-form-item label="用户名" prop="username">
          <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
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
      this.$router.push('/Register')
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
}
</script>