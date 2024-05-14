<template>
  <div>
    <div style="margin: 10px 0">

      <el-button class="ml-5" type="primary" @click="loadNon">查看未录入成绩的学生</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">//表格数据需要绑定
      <el-table-column prop="wjyCno" label="课程编号" width="120">
      </el-table-column>
      <el-table-column prop="wjyCname" label="课程名称" width="120">
      </el-table-column>
      <el-table-column prop="wjyCterm" label="开设学期" width="120">
      </el-table-column>
      <el-table-column prop="wjyTno" label="教师编号" width="120">
      </el-table-column>
      <el-table-column prop="wjyClno" label="班级编号" width="120">
      </el-table-column>
      <el-table-column prop="wjySno" label="学号" width="80">
      </el-table-column>
      <el-table-column prop="wjySname" label="学生姓名" width="140">
      </el-table-column>
      <el-table-column prop="wjyScore" label="成绩" width="120">
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">成绩录入 <i class="el-icon-edit"></i></el-button>
        </template>
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
    <el-dialog title="成绩信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="成绩分数">
          <el-input v-model="form.wjyScore" autocomplete="off" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="请选择要修改的选课记录" :visible.sync="dialogFormVisibleCinfo" width="30%" :show-close="false" :close-on-click-modal="false">
      <el-form label-width="80px" size="small">
        <el-form-item label="课程名">
          <el-select v-model="wjyCname" autocomplete="off" >
            <el-option
                v-for="item in this.cname"
                :key="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教学班">
          <el-select v-model="wjyClno" autocomplete="off" >
          <el-option
              v-for="item in this.class"
              :key="item"
              :value="item">
          </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveCinfo">确 定</el-button>
        <el-button type="danger" @click="back">取消</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ScoreLog",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      wjyCno:"",
      wjyCname:"",
      wjyCterm:"",
      wjyClno:"",
      wjyTno:"",
      wjySno:"",
      wjySname:"",
      wjyScore:"",
      check:"0",
      defaultPassword:"1111111",
      form:{},
      course:[],
      class:[],
      cname:[],
      dialogFormVisible:false,
      dialogFormVisibleCinfo:true,
      multipleSelection:[],
      headerBg: 'headerBg',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.wjyTno=this.user.wjyId
    this.load()
   this.loadCourse()

  },
  methods:{
    back(){
window.location.href='http://localhost:8080/home'
    },
    loadNon(){
      this.check="1"
      // console.log(this.wjyCno)
      this.request.get("/score-log/non",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          wjyCno:this.wjyCno,
          wjyClno:this.wjyClno,
          wjyTno:this.wjyTno,
          wjySno:this.wjySno,
          wjyCterm:this.wjyCterm,
          check:this.check
        }
      }).then(res =>{console.log(res)
        this.tableData=res.records
        this.total=res.total
        console.log(this.tableData)
      })
    },
    saveCinfo(){
      this.request.get("/course").then(res=>{
        for(let i=0;i<res.length;i++){
          if( res[i].wjyCname===this.wjyCname){
            this.wjyCno=res[i].wjyCno;
            break;
          }
        }
        // console.log(this.wjyCname)
        // console.log( this.wjyCno)
            this.request.get("/course").then(res=>{
              for(let i=0;i<res.length;i++){
                if( res[i].wjyCname===this.wjyCname){
                  this.wjyCno=res[i].wjyCno;
                  break;
                }
              }
              this.load();
            })
      })
      if(this.wjyCname!==""&&this.wjyClno!=="") {this.dialogFormVisibleCinfo=false;}
      else{
        this.$message.error("请选择班课信息！")
      }
    },
    load() {
      // console.log(this.wjyCno)
      this.request.get("/score-log/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          wjyCno:this.wjyCno,
          wjyClno:this.wjyClno,
          wjyTno:this.wjyTno,
          wjySno:this.wjySno,
          wjyCterm:this.wjyCterm
        }
      }).then(res =>{console.log(res)
        this.tableData=res.records
        this.total=res.total
        console.log(this.tableData)
      })
    },
    loadCourse() {
      this.request.get("/score-log").then(res=>{
        this.course=[]
        for(let i=0;i<res.length;i++){
          if(res[i].wjyTno === this.wjyTno&&!this.class.includes(res[i].wjyClno)){
            this.class.push(res[i].wjyClno)
          }
          if(!this.course.includes(res[i].wjyCno) && res[i].wjyTno === this.wjyTno) {
            this.course.push(res[i].wjyCno)
          }
          if(res[i].wjyTno === this.wjyTno&&!this.cname.includes(res[i].wjyCname)){
            this.cname.push(res[i].wjyCname)
          }
        }
      })
    },
    reset(){
      this.check="0"

      this.loadCourse()
      this.load()
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