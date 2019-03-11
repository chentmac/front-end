<template>
  <el-row class="text-align-left">
    <Header></Header>
    <el-col :span="24">
      <el-col :span="22" :offset="1"  class="page-title">Items To Do</el-col>
      <el-col :span="22" :offset="1" class="hight-btn" >
        <el-button @click="showAddDialog">Add</el-button>
        <el-button @click="showModifyDialog">Modify</el-button>
        <el-button @click="handleDelete">Delete</el-button>
        <el-input v-model="searchItem" placeholder="input title" @keyup.enter.native="onSearch"></el-input>
        <el-button @click="onSearch" >Search</el-button>
      </el-col>
      <el-col :span="22" :offset="1">
        <el-table class="item-table" empty-text="No Data" :data="toDoList.slice((currentPage-1)*pageSize,currentPage*pageSize)" ref="multipleTable" stripe
                  max-height="700" style="width: 100%" highlight-current-row @selection-change="handleSelectionChange" @row-dblclick="handleRowDBClick"
                  :default-sort = "{prop: 'endDate', order: 'descending'}">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column label="Title" sortable prop="title">
            <template slot-scope="scope">
              <span>{{scope.row.title}}</span>
            </template>
          </el-table-column>
          <el-table-column label="Creator" sortable prop="creator">
            <template slot-scope="scope">
              <span>{{scope.row.creator}}</span>
            </template>
          </el-table-column>
          <el-table-column label="EndDate" sortable prop="endDate" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span v-if="scope.row.endDate !== null">{{scope.row.endDate.substring(0, 10)}}</span>
            </template>
          </el-table-column>
          <el-table-column label="Action" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span><el-button type="danger" @click="finishClick(scope.row)">Finish</el-button></span>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination class="table-nav" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       :current-page="currentPage" :page-sizes="[10,20,30, 40, 50]" :page-size=pageSize
                       layout="total, sizes, prev, pager, next, jumper" :total="toDoList.length">
        </el-pagination>
        <el-dialog :title="updateDialogTitle" :visible.sync="dialogVisible" width="500px" top="100px" @close='closeDialog'>
          <el-form label-position="top" :model="item" :rules="rules" ref="updateForm" class="dialog-form">
            <el-input type="hidden" v-model="item.id"></el-input>
            <el-form-item label="Title" prop="title">
              <el-input v-model="item.title"></el-input>
            </el-form-item>
            <el-form-item label="Creator" prop="creator">
              <el-input v-model="item.creator"></el-input>
            </el-form-item>
            <el-form-item label="EndDate">
                <el-col :span="11">
                  <el-date-picker type="date" placeholder="Pick a date" v-model="item.endDate" style="width: 100%;"></el-date-picker>
                </el-col>
            </el-form-item>
            <el-form-item label="Detail">
              <el-input type="textarea" v-model="item.detail"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
          <el-button @click="handleCancleBtn">Cancle</el-button>
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
  export default {
    name: 'ToDo',
    components: {
      Header
    },
    data () {
      return {
        item: {
          id:'',
          title: '',
          creator: '',
          endDate: '',
          detail: ''
        },
        searchItem:'',
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
          creator: [
            {required: true, message: Constant.INPUT_TITLE, trigger: ['blur', 'change']}
          ],
        }
      }
    },
    props:['username'],
    methods: {
      handleSizeChange (val) {
        this.pageSize = val;
      },
      handleCurrentChange (val) {
        this.currentPage = val;
      },
      handleDelete () {
        if (this.multipleSelection.length > 0) {
          this.$confirm(Constant.SELECT_DELETE, {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel'
          }).then(action => {
              this.deleteItem();
            }).catch(_ => {});
        } else {
          this.$message.warning(Constant.NO_SELECTED);
        }
      },
      handleSelectionChange (value) {
        this.multipleSelection = value;
      },
      handleRowDBClick (row, event) {
        this.updateDialogTitle = 'Modify item';
        this.item = JSON.parse(JSON.stringify(row));
        this.dialogVisible = true;
        this.isAdd = false;
      },
      showAddDialog () {
        this.updateDialogTitle = 'Add item';
        this.dialogVisible = true;
        this.isAdd = true;
      },
      async findAllitem () {
        let itemInfo;
        await axios.get(Constant.BASE_URL+'/findAllToDoItem').then(response=>{
          itemInfo = response.data.data;
        });
        this.toDoList = itemInfo;
      },
      async addNewItem (item) {
        let itemInfo;
        if(this.isAdd){
          await axios.post(Constant.BASE_URL+"/addToDoItem",item).then(response=>{
            itemInfo = response.data.data;
            this.toDoList.push(itemInfo);
          });
        }else {
          await axios.put(Constant.BASE_URL+"/updateToDoItem",item).then(response=>{
            itemInfo = response.data.data;
          },error=>{

          });
        }
        this.closeDialog();
        this.showWarning(Constant.SAVE_SUCCEED,"success");
        this.currentPage = 1;
        this.findAllitem();
      },
      async remove (item) {
        let itemInfo;
          await axios({
            url: Constant.BASE_URL+'/deleteItems',
            method: 'delete',
            data: item
          }).then(response=>{
            this.showWarning(Constant.DELETE_SUCCEED,"success")
          });
          this.currentPage = 1;
          this.findAllitem();
      },

      saveItem () {
        this.$refs.updateForm.validate((valid) => {
          if (!valid) {
            return false;
          } else {
            this.addNewItem(this.item);
          }
        });
      },
      finishClick(item){
        this.$confirm(Constant.FINISH_ITEM, {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel'
        }).then(action => {
            this.finish(item);
          }).catch(_=>{})
      },
      async finish(item){
         await axios.put(Constant.BASE_URL+"/finishItem",item).then(response=>{
              this.showWarning(Constant.FINISH_SUCCEED,"success")
              this.findAllitem();
         },error=>{
           this.showWarning(Constant.FINISH_SUCCEED,"error")
         })
      },
      deleteItem () {
        this.remove(this.multipleSelection);
      },
      closeDialog () {
        this.dialogVisible = false;
        this.item = {};
        this.$refs.updateForm.resetFields();
      },
      showModifyDialog () {
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
      handleCancleBtn () {
        this.$refs.updateForm.resetFields();
        this.dialogVisible = false;
      },
      showWarning (msg,type) {
        this.$message({
          message: msg,
          type: type
        })
      },
      onSearch(){
          axios.get(Constant.BASE_URL+'/searchByTitle?title='+this.searchItem.trim()+'&status=0').then(response=>{
            this.toDoList = response.data.data;
          },error=>{

          })
      }
    },
    mounted () {
      this.findAllitem();
    }
  };
</script>

<style scoped>
  .hight-btn{
    padding: 30px 0 15px 0;
  }
  .text-align-left {
    text-align: left;
  }

  .page-title{
    font-size: 20px;
    margin-top: 80px;
  }
  .margin-top{
    margin-top: 30px;
  }
  .line {
    width: 1px;
    height: 700px;
    background-color: #e6e6e6;
    margin-left: 20px;
  }

  .item-table{
    margin-top: 15px;
  }

  .table-nav{
    position: fixed;
    bottom: 40px;
  }
  /*rewrite element-ui */
  >>>.dialog-form .el-input{
    width: 360px;
  }
  >>>.el-dialog__body{
    padding: 5px 70px;
  }
  >>>.el-dialog__footer {
    padding: 40px 70px 20px;
  }
  >>>.el-button{
    margin-top: auto;
    border-color: dodgerblue;
    color: dodgerblue;
  }

  >>>.el-button:hover{
    border-color: dodgerblue;
    background-color: dodgerblue;
    color: #fff;
  }

  >>>.el-button:focus{
    border-color: dodgerblue;
    background-color: dodgerblue;
    color: #fff;
  }

  >>>.el-button--danger{
    background-color: dodgerblue;
    border-color: dodgerblue;
    color: #fff;
  }

  >>>.el-button--danger:hover{
    background-color: #4b6ebe;
    border-color: #4b6ebe;
    color: #fff;
  }

  >>>.el-button--danger:focus{
    background-color: #4b6ebe;
    border-color: #4b6ebe;
    color: #fff;
  }

  >>>.el-table{
    color: black;
  }

  >>>.el-table thead{
    color: black;
  }

  >>>.el-form--label-top .el-form-item__label{
    padding: 0;
  }

  >>>.el-form-item{
    margin-bottom: 5px;
  }

  >>>.el-pager li.active{
    color: dodgerblue;
  }

  >>>.el-pager li:hover{
    color: dodgerblue;
  }

  >>>.el-checkbox__inner{
    border: 1px solid #aaaaaa;
  }

  >>>.el-form-item__label{
    line-height: 32px;
  }

  .el-input {
    margin-top: 0;
    width: 200px;
  }
</style>
