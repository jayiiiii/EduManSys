<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入课程编号" suffix-icon="el-icon-search" v-model="wjyCno"></el-input>
      <el-input style="width: 200px" placeholder="请输入班级编号" suffix-icon="el-icon-message" class="ml-5" v-model="wjyClno"></el-input>
      <el-select style="width: 200px" placeholder="请选择开课学期" suffix-icon="el-icon-position" class="ml-5" v-model="wjyCterm">
        <el-option
            v-for="item in this.term"
            :key="item.value"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">//表格数据需要绑定
      <el-table-column prop="wjyCno" label="课程编号" width="80">
      </el-table-column>
      <el-table-column prop="wjyCname" label="课程名称" width="140">
      </el-table-column>
      <el-table-column prop="wjyClno" label="班级编号" width="120">
      </el-table-column>
      <el-table-column prop="wjyTno" label="教师编号" width="120">
      </el-table-column>
      <el-table-column prop="wjyTname" label="教师姓名" width="120">
      </el-table-column>
      <el-table-column prop="wjyCterm" label="开设学期" width="120">
      </el-table-column>
      <el-table-column prop="wjyCcredit" label="学分">
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">//需要绑定数据,total是后台返回给前端的，pageNum和pageSize是前端返回的
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "CinfoStudent",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:2,
      wjyCno:"",
      wjyCname:"",
      wjyClno:"",
      wjyTno:"",
      wjyTname:"",
      wjyCterm:"",
      wjyCcredit:"",
      term: [{
        value: '2021/2022(1)'
      }, {
        value: '2021/2022(2)'
      }, {
        value: '2022/2023(1)'
      }, {
        value: '2022/2023(2)'
      }],
      formCourse:{},
      formCinfo:{},
      dialogFormVisibleCourse:false,
      dialogFormVisibleCinfo:false,
      course:[],
      teacher:[],
      class:[],
      multipleSelection:[],
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load()
  },
  methods:{
    load() {
      this.request.get("/cinfo-view/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          wjyCno:this.wjyCno,
          wjyClno:this.wjyClno,
          wjyCterm:this.wjyCterm
        }
      }).then(res =>{console.log(res)
        this.tableData=res.records
        this.total=res.total
        console.log(this.tableData)
      })
    },
    reset(){
      this.wjyCno=""
      this.wjyClno=""
      this.wjyCterm=""
      this.load()
      this.loadCourse()
      this.loadTeacher()
      this.loadClass()
    },
    handleSizeChange(pageSize){
      console.log(pageSize)//控制台输出相关数据
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val;
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },
    exp() {
      window.open("http://localhost:9090/student/export")
    }
  }
}
</script>
<style>
.headerBg {
  background: #eee!important;
}
</style>>