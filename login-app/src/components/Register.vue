<template>
  <div class="Login">
    <el-card>
      <h1>注册</h1>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="loginForm" label-width="100px" class="login-from">
        <el-form-item label="用户名" prop="username">
          <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="password2">
          <el-input type="password" v-model="ruleForm.password2" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input type="email" v-model="ruleForm.email" autocomplete="on"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input type="number" v-model="ruleForm.mobile" autocomplete="on"></el-input>
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
  name: 'Register',
  setup() {

    var checkMobile = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|6|7|8][0-9]{9}$/
      if (!value) {
        return callback(new Error('电话号码不能为空'))
      }
      setTimeout(() => {
        
        if (!Number.isInteger(+value)) {
          callback(new Error('请输入数字值'))
        } else {
          if (phoneReg.test(value)) {
            callback()
          } else {
            callback(new Error('电话号码格式不正确'))
          }
        }
      }, 100)
    }


      const loginForm = ref(null)
      const state = reactive({
        ruleForm: {
          username: '',
          password: '',
          password2: '',
          mobile: '',
        },
        checked: true,
        rules: {
          username: [
            { required: 'true', message: '账户不能为空', trigger: 'blur' }
          ],
          password: [
            { required: 'true', message: '密码不能为空', trigger: 'blur' }
          ],
          password2: [
            { required: 'true', message: '确认密码不能为空', trigger: 'blur' }
          ],
          mobile: [
            { required: 'true', validator: checkMobile, trigger: 'blur' }
          ],
          email: [
            { required: 'true', message: '请输入邮箱地址', trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
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
  }
}
</script>