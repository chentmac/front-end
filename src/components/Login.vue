<template>
  <div>
    <el-col :span="24" class="header"></el-col>
    <el-col :xl="44" :lg="22" :offset="1" style="margin-top: 80px;">
      <el-form class="signInForm">
        <el-form-item label="User Name">
          <el-input v-model="username" auto-complete="off" :autofocus="true"></el-input>
          <p>{{usernameError}}</p>
        </el-form-item>
        <el-form-item label="Password">
          <el-input type="password" v-model="password" auto-complete="off" @keyup.enter.native="submitForm()"></el-input>
          <p>{{passwordError}}</p>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm()">Sign in</el-button>
          <el-button @click="resetForm()">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </div>
</template>

<script>
import axios from 'axios';
import Constant from '@/common/Constant'
export default {
  name: 'Login',
  data () {
    return {
      username: '',
      password: '',
      usernameError: 'user name is error.',
      passwordError: '',
      ipAddress: '',
      rsaPublicKey: ''
    }
  },
  methods: {
    resetForm () {
      this.username = ''
      this.password = ''
    },
    submitForm () {
      if (this.username === '') {
        this.showWarning('Please input username')
        return
      }
      if (this.password === '') {
        this.showWarning('Please input password')
        return
      }
      if (this.username !== '' && this.password !== '') {
        this.login()
      }
    },
    showWarning (msg) {
      this.$message({
        message: msg,
        type: 'warning'
      })
    },
    async login () {
      axios.get(Constant.BASE_URL+'/login',{params: {
       username: this.username,
       password: this.password
      }
    }).then(response=>{
        this.username = response.data;
        window.localStorage.username = response.data.data.name
        this.$router.push('/toDoForm');
        },
        error=>{
          if(error.response.status===401){
            this.showWarning(error.response.data.data)
          }
        }
      )
    }
  }
}
</script>

<style scoped>
  .signInForm
  {
    margin:auto;
    width:50%;
  }
  .header{
    background-color: dodgerblue;
    height: 60px;
    position: fixed;
    z-index: 10;
  }
</style>
