<template>
  <el-form :model="user" :rules="userForm" ref="user" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">Login Page</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="user.userName" auto-complete="off" placeholder="account"></el-input>
    </el-form-item>
    <el-form-item prop="password1">
      <el-input type="password" v-model="user.password" auto-complete="off" placeholder="password"></el-input>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="margin-left:5%;width:40%;" @click.native.prevent="login" :loading="loginLoading">login</el-button>
      <el-button type="primary" style="width:40%;" @click.native.prevent="register" :loading="registerLoading">register</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import axios from 'axios';
  import Constant from '@/common/Constant'
  export default {
    data() {
      return {
        loginLoading: false,
        registerLoading:false,
        user:{
          userName:"",
          password:""
        },

        userForm: {
          userName: [
            { required: true, message: 'please input account', trigger: 'blur' },
            //{ validator: validaePass }
          ],
          password: [
            { required: true, message: 'please input password ', trigger: 'blur' },
            //{ validator: validaePass2 }
          ]
        },
      };
    },
    methods: {
      login () {
        this.loginLoading = true
        axios.post(Constant.BASE_URL+'/user/login',this.user).then(response=>{
          if (response.data != null){
            sessionStorage.setItem('users',JSON.stringify(response.data));
            this.$router.push('/toDoForm');
          }
          else{
            this.$message.error('user name or password error');
          }
            this.loginLoading = false
          },
          error=>{
            if(error.response.status===401){
              this.showWarning(error.response.data.data)
              this.loginLoading = false
            }
          }

        )
      },
      async register () {
        this.registerLoading = true;
        axios.post(Constant.BASE_URL+'/user',this.user).then(response=>{
          if (response.data != null) {
            this.username = response.data;
            this.$router.push('/toDoForm');
          }
          else{
            this.$message.error('user name exists!');
          }
            this.registerLoading = false
          },
          error=>{
            if(error.response.status===401){
              this.showWarning(error.response.data.data)
            }
            this.registerLoading = false
          }
        )
      },

  }
  }
</script>

<style>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login-container .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  .login-container .remember {
    margin: 0px 0px 35px 0px;
  }
</style>
