<template>
  <div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">//表格数据需要绑定
      <el-table-column prop="wjyCno" label="课程编号" width="120">
      </el-table-column>
      <el-table-column prop="wjyCname" label="课程名称" width="120">
      </el-table-column>
      <el-table-column prop="wjyTno" label="教师编号" width="120">
      </el-table-column>
      <el-table-column prop="wjyClno" label="班级编号" width="120">
      </el-table-column>
      <el-table-column prop="wjySno" label="学号" width="80">
      </el-table-column>
      <el-table-column prop="wjySname" label="学生姓名" width="140">
      </el-table-column>
      <el-table-column prop="wjyScore" label="成绩">
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
  name: "StuScore",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:2,
      wjyCno:"",
      wjyCname:"",
      wjyCterm:"",
      wjyClno:"",
      wjyTno:"",
      wjySno:"",
      wjySname:"",
      wjyScore:"",
      defaultPassword:"1111111",
      form:{},
      course:[],
      dialogFormVisible:false,
      multipleSelection:[],
      headerBg: 'headerBg',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.wjySno=this.user.wjyId
    this.load()
    this.loadCourse()

  },
  methods:{
    load() {
      this.request.get("/score-log/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          wjyCno:this.wjyCno,
          wjyClno:this.wjyClno,
          wjyCterm:this.wjyCterm,
          wjyTno:this.wjyTno,
          wjySno:this.wjySno,
        }
      }).then(res =>{console.log(res)
        console.log(this.wjySno);
        this.tableData=res.records
        this.total=res.total
        console.log(this.tableData)
      })
    },
    loadCourse() {
      this.request.get("/score-log").then(res=>{
        this.course=[]
        for(let i=0;i<res.length;i++){
          if(!this.course.includes(res[i].cno) && res[i].tno === this.tno)
            this.course.push(res[i].cno)
        }
      })
    },
    save(){
      this.request.post("/score",this.form).then(res =>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
        }else {
          this.$message.error("保存失败")
        }
        this.load()
      })
    },
    handleEdit(row){
      this.dialogFormVisible=true
      this.form=row//已经绑定过save（）
      this.load()
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