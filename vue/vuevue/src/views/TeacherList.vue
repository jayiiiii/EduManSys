<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入教师编号" suffix-icon="el-icon-search" v-model="wjyTno"></el-input>
      <el-input style="width: 200px" placeholder="请输入职位" suffix-icon="el-icon-message" class="ml-5" v-model="wjyTposition"></el-input>
      <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-position" class="ml-5" v-model="wjyTname"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">//表格数据需要绑定
      <el-table-column prop="wjyTno" label="教师编号" width="80">
      </el-table-column>
      <el-table-column prop="wjyTname" label="教师姓名" width="140">
      </el-table-column>
      <el-table-column prop="wjyTposition" label="职位" width="120">
      </el-table-column>
      <el-table-column prop="wjyTnumber" label="联系方式">
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
  name: "TeacherList",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:2,
      wjyTno:"",
      wjyTname:"",
      wjyTsex:"",
      wjyTage:"",
      wjyTposition:"",
      wjyTnumber:"",
      wjyTpassword:"",
      defaultPassword:"1111111",
      form:{},
      dialogFormVisible:false,
      multipleSelection:[],
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load()
  },
  methods:{
    load() {
      this.request.get("/teacher/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          wjyTno:this.wjyTno,
          wjyTposition:this.wjyTposition,
          wjyTname:this.wjyTname

        }
      }).then(res =>{console.log(res)

        this.tableData=res.records
        this.total=res.total
        console.log(this.tableData)
      })

    },
    reset(){
      this.wjyTno=""
      this.wjyTposition=""
      this.wjyTname=""
      this.load()
    },
    save(){
      this.request.post("/teacher",this.form).then(res =>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
        }else {
          this.$message.error("保存失败")
        }
        this.load()
      })
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
      window.open("http://localhost:9090/teacher/export")
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>>