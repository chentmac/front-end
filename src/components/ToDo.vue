<template>
  <el-row class="text-align-left">
    <Header></Header>
    <el-col :span="24">
      <el-col :span="22" :offset="1" class="page-title">Items To Do</el-col>
      <el-col :span="22" :offset="1" class="hight-btn">
        <el-button @click="showAddDialog">Add</el-button>
        <el-button @click="showModifyDialog">Modify</el-button>
        <el-button @click="handleDelete">Delete</el-button>
        <el-input v-model="searchItem" placeholder="input title" @keyup.enter.native="onSearch"></el-input>
        <el-button @click="onSearch" icon="el-icon-search">Search</el-button>
        <el-badge :value="toDayTaskCount" class="item">
          <el-button icon="el-icon-warning" @click="showTodayTasks">Today</el-button>
        </el-badge>
      </el-col>
      <el-col :span="22" :offset="1">
        <el-table class="item-table" empty-text="No Data"
                  :data="toDoList.slice((currentPage-1)*pageSize,currentPage*pageSize)" ref="multipleTable" stripe
                  style="width: 100%" highlight-current-row @selection-change="handleSelectionChange"
                  @row-dblclick="handleRowDBClick"
                  :default-sort="{prop: 'endDate', order: 'descending'}">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="Executors:">
                  <span>{{ scope.row.executorsName.join(',') }}</span>
                </el-form-item>
                <el-form-item label="Content:">
                  <span>{{ scope.row.content}}</span>
                </el-form-item>
                <el-form-item label="Detail:">
                  <li v-for="(isFinish,key) in scope.row.executorCompleteMap">
                    <span>{{key}}:</span>
                    <span v-if="isFinish">Complete</span>
                    <span v-if="!isFinish">In Progress</span>
                  </li>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-input type="hidden" v-model="item.initiatorId"></el-input>
          <el-table-column label="Title" sortable prop="title">
            <template slot-scope="scope">
              <span>{{scope.row.title}}</span>
            </template>
          </el-table-column>
          <el-table-column label="Initiator" sortable prop="initiator">
            <template slot-scope="scope">
              <span>{{scope.row.initiatorName}}</span>
            </template>
          </el-table-column>
          <el-table-column label="ExpireDay" sortable prop="expireDay" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span v-if="checkToday(scope.row.expireDate)">Today</span>
              <span v-else-if="scope.row.expireDate !== null">{{scope.row.expireDate.substring(0, 10)}}</span>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination class="table-nav" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       :current-page="currentPage" :page-sizes="[10,20,30, 40, 50]" :page-size=pageSize
                       layout="total, sizes, prev, pager, next, jumper" :total="toDoList.length">
        </el-pagination>
        <el-dialog :title="updateDialogTitle" :visible.sync="dialogVisible" width="500px" top="100px"
                   @close='closeDialog'>
          <el-form label-position="top" :model="item" :rules="rules" ref="updateForm" class="dialog-form">
            <el-input type="hidden" v-model="item.taskId"></el-input>
            <el-form-item label="Title" prop="title">
              <el-input v-model="item.title"></el-input>
            </el-form-item>
            <span v-if="!isAdd">
            <el-form-item label="Initiator">
              <el-input v-model="item.initiatorName" :readonly=true></el-input>
            </el-form-item>
              </span>
            <el-form-item label="Executors">
              <el-select v-model="item.executorsName" multiple placeholder="Select">
                <el-option
                  v-for="item in users"
                  :key="item.userName"
                  :label="item.userName"
                  :value="item.userName">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="ExpireDate">
              <el-col :span="11">
                <el-date-picker type="date" placeholder="Pick a date" v-model="item.expireDate"
                                style="width: 100%;"></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="Content">
              <el-input type="textarea" v-model="item.content"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
          <el-button @click="handleCancelBtn">Cancle</el-button>
          <el-button type="danger" @click="saveItem">Save</el-button>
        </span>
        </el-dialog>
      </el-col>
    </el-col>
  </el-row>
</template>

<script>
  import Constant from '@/common/Constant'
  import axios from 'axios'
  import Header from '@/components/Header.vue'
  import Utils from '@/util/Utils'

  export default {
    name: 'ToDo',
    components: {
      Header
    },
    data() {
      return {
        item: {
          taskId: '',
          title: '',
          initiatorName: '',
          executorsName: [],
          expireDate: '',
          content: '',
          initiatorId: '',
          executorsId: [],
        },
        toDayList:[],
        toDayTaskCount: 0,
        user: {},
        users: [],
        searchItem: '',
        dialogVisible: false,
        isAdd: false,
        updateDialogTitle: '',
        value: '',
        toDoList: [],
        multipleSelection: [],
        currentPage: 1,
        pageSize: 10,
        rules: {
          title: [
            {required: true, message: Constant.INPUT_TITLE, trigger: ['blur', 'change']}
          ],
        }
      }
    },
    methods: {
      showTodayTasks(){
        this.toDoList = this.toDayList;
      },
      async findALLuser() {
        let userInfo;
        await axios.get(Constant.BASE_URL + '/user/').then(response => {
          userInfo = response.data;
        });
        this.users = userInfo;
      },
      checkToday(date) {
        return Utils.checkToday(date)
      },
      handleSizeChange(val) {
        this.pageSize = val;
      },
      handleCurrentChange(val) {
        this.currentPage = val;
      },
      handleDelete() {
        if (this.multipleSelection.length > 0) {
          this.$confirm(Constant.SELECT_DELETE, {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel'
          }).then(action => {
            this.deleteItem();
          }).catch(_ => {
          });
        } else {
          this.$message.warning(Constant.NO_SELECTED);
        }
      },
      handleSelectionChange(value) {
        this.multipleSelection = value;
      },
      handleRowDBClick(row, event) {
        this.updateDialogTitle = 'Modify item';
        this.item = JSON.parse(JSON.stringify(row));
        this.dialogVisible = true;
        this.isAdd = false;
      },
      showAddDialog() {
        this.updateDialogTitle = 'Add item';
        this.dialogVisible = true;
        this.isAdd = true;
      },
      async findAllitem() {
        this.toDayList = [];
        this.toDayTaskCount = 0;
        let itemInfo;
        await axios.get(Constant.BASE_URL + '/task/todo').then(response => {
          console.log(response.data)
          itemInfo = response.data;
          itemInfo.forEach(value => {
            if(Utils.checkToday(value.expireDate)){
              this.toDayTaskCount++;
              this.toDayList.push(value);
            }
          })
        });
        this.toDoList = itemInfo;
      },
      async addNewItem(item) {
        let itemInfo;
        if (this.isAdd) {
          await axios.post(Constant.BASE_URL + "/task", item).then(response => {
            if(response.data){
              this.showWarning(Constant.SAVE_SUCCEED, "success");
            }
          },error=>{

          });
        } else {
          await axios.put(Constant.BASE_URL + "/task", item).then(response => {
            if(response.data){
              this.showWarning(Constant.SAVE_SUCCEED, "success");
            }
          }, error => {

            this.showWarning(Constant.SAVE_SUCCEED, "error");
          });
        }
        this.closeDialog()
        this.currentPage = 1;
        this.findAllitem(this.user.userName);
      },
      async remove(item) {
        await axios({
          url: Constant.BASE_URL + '/task',
          method: 'delete',
          data: item
        }).then(response => {
          this.showWarning(Constant.DELETE_SUCCEED, "success")
        });
        this.currentPage = 1;
        this.findAllitem(this.user.userName);
      },
      saveItem() {
        this.$refs.updateForm.validate((valid) => {
          if (!valid) {
            return false;
          } else {
            let array1 = [];
            if(this.item.executorsName!=null || this.item.executorsName !== []){
              this.item.executorsName.forEach(value => {
                this.users.forEach(value1 => {
                  if (value == value1.userName) {
                    array1.push(value1.id)
                  }
                })
              });
            }
            this.item.executorsId = array1;
            if(this.isAdd){
              this.item.initiatorName = this.user.userName;
            }
            this.addNewItem(this.item);
          }
        });
      },
      finishClick(taskId) {
        this.$confirm(Constant.FINISH_ITEM, {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel'
        }).then(action => {
          this.finish(taskId);
        }).catch(_ => {
        })
      },
      async finish(taskId) {
        await axios.put(Constant.BASE_URL + "/task/finished/"+this.user.userName+"/"+taskId).then(response => {
          this.showWarning(Constant.FINISH_SUCCEED, "success")
          this.findAllitem(this.user.userName);
        }, error => {
          this.showWarning(Constant.FINISH_SUCCEED, "error")
        })
      },
      deleteItem() {
        this.remove(this.multipleSelection);
      },
      closeDialog() {

        this.dialogVisible = false;
        this.item.executorsName = [];
        console.log(this.item.executorsName)
        this.item.title = '';
        this.item.expireDate = '';
        this.item.content = '';
        this.$refs.updateForm.resetFields();
      },
      showModifyDialog() {
        if (this.multipleSelection.length === 1) {
          this.updateDialogTitle = 'Modify item';
          this.item = JSON.parse(JSON.stringify(this.multipleSelection[0]));
          this.dialogVisible = true;
          this.isAdd = false;
        } else {
          let selectWarningInfo = this.multipleSelection.length === 0 ? Constant.SELECT_NONE_LINE : Constant.SELECT_MULTIPLE_LINES;
          this.$message.warning(selectWarningInfo);
        }
      },
      handleCancelBtn() {
        this.$refs.updateForm.resetFields();
        this.dialogVisible = false;
      },
      showWarning(msg, type) {
        this.$message({
          message: msg,
          type: type
        })
      },
      onSearch() {
        axios.get(Constant.BASE_URL + '/task/' + this.searchItem.trim()).then(response => {
          this.toDoList = response.data;
        }, error => {

        })
      }
    },
    mounted() {
      this.user = JSON.parse(sessionStorage.getItem('users'));
      this.findAllitem(this.user.userName);
      this.findALLuser();
    }
  };
</script>

<style scoped>
  .hight-btn {
    padding: 30px 0 15px 0;
  }

  .text-align-left {
    text-align: left;
  }

  .page-title {
    font-size: 20px;
    margin-top: 80px;
  }

  .margin-top {
    margin-top: 30px;
  }

  .line {
    width: 1px;
    height: 700px;
    background-color: #e6e6e6;
    margin-left: 20px;
  }

  .item-table {
    margin-top: 15px;
  }

  .item {
    margin-top: -1px;
    margin-right: 0px;
    float: right;
  }

  .table-nav {
    margin-top: 10px;
    bottom: 40px;
  }

  /*rewrite element-ui */
  >>> .dialog-form .el-input {
    width: 360px;
  }

  >>> .el-dialog__body {
    padding: 5px 70px;
  }

  >>> .el-dialog__footer {
    padding: 40px 70px 20px;
  }

  >>> .el-button {
    margin-top: auto;
    border-color: dodgerblue;
    color: dodgerblue;
  }

  >>> .el-button:hover {
    border-color: dodgerblue;
    background-color: dodgerblue;
    color: #fff;
  }

  >>> .el-button:focus {
    border-color: dodgerblue;
    background-color: dodgerblue;
    color: #fff;
  }

  >>> .el-button--danger {
    background-color: dodgerblue;
    border-color: dodgerblue;
    color: #fff;
  }

  >>> .el-button--danger:hover {
    background-color: #4b6ebe;
    border-color: #4b6ebe;
    color: #fff;
  }

  >>> .el-button--danger:focus {
    background-color: #4b6ebe;
    border-color: #4b6ebe;
    color: #fff;
  }

  >>> .el-table {
    color: black;
  }

  >>> .el-table thead {
    color: black;
  }

  >>> .el-form--label-top .el-form-item__label {
    padding: 0;
  }

  >>> .el-form-item {
    margin-bottom: 5px;
  }

  >>> .el-pager li.active {
    color: dodgerblue;
  }

  >>> .el-pager li:hover {
    color: dodgerblue;
  }

  >>> .el-checkbox__inner {
    border: 1px solid #aaaaaa;
  }

  >>> .el-form-item__label {
    line-height: 32px;
  }

  .el-input {
    margin-top: 0;
    width: 200px;
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    display: inline-block;
    vertical-align: top;
    box-sizing: border-box;
    line-height: 40px;
    position: relative;
    font-size: 14px;
    /*margin-right: 0;*/
    /*margin-bottom: 0;*/
    width: 100%;
  }
</style>
