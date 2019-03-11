<template>
  <el-col :span="24" class="header">
    <el-col :xl="2" :lg="3" :offset="1">
      <span class="header-title">To Do List</span>
    </el-col>
    <el-col :xl="2" :lg="2">&nbsp;</el-col>
    <el-col :span="6" class="item-menu">
      <!-- :span="6" :md="4" :xl="4" :lg="5"-->
      <el-menu class="el-menu-demo" mode="horizontal" @select="handleSelect"
               background-color="dodgerblue" text-color="#fff" active-text-color="#ffdc73">
        <el-menu-item index="1">To Do</el-menu-item>
        <el-menu-item index="2">Finish</el-menu-item>
      </el-menu>
    </el-col>
    <el-col :xl="10" :lg="6">&nbsp;</el-col>
    <el-col :xl="2" :lg="3" class="header-welcome"></el-col>
    <el-col :xl="2" :lg="3" class="text-align-right">
    </el-col>
    <el-col :xl="1" :lg="1">&nbsp;</el-col>
  </el-col>
</template>

<script>
import Constant from '@/common/Constant'

export default {
  data () {
    return {
      userName: '',
      userType: '',
      roleVendor: Constant.USER_TYPE_VENDOR,
      roleStakeholder: Constant.USER_TYPE_STAKEHOLDER,
      roleAdmin: Constant.USER_TYPE_ADMIN
    };
  },
  methods: {
    handleSelect (key, keyPath) {
      switch (key) {
        case '1':
          this.$router.push('/toDoForm');
          break;
        case '2':
          this.$router.push('/finishForm');
          break;
      }
    }
  },
};
</script>

<style scoped>
.header{
  background-color: dodgerblue;
  height: 60px;
  position: fixed;
  z-index: 10;
}
.header-title{
  color: #fff;
  font-weight: bold;
  font-size: 26px;
  position: relative;
  top: 15px;
}

.text-align-right{
  position: fixed;
  right: 3%;
  text-align: right;
}

.item-menu{
  position: relative;
}

@media screen and (max-width: 1141px) {
  .el-menu-demo{
    position: fixed;
    margin-right: 10%;
  }
}

@media screen and (max-width: 1230px) {
  .header-welcome{
    font-size: 13px;
  }
}

.header-welcome{
  position: fixed;
  right: 15%;
  margin-right: 15px;
  color: #fff;
  padding-top: 20px;
  text-align: right;
}

</style>
