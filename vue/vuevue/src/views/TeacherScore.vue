<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入班级" suffix-icon="el-icon-message" class="ml-5" v-model="wjyClno"></el-input>
      <el-select style="width: 200px" placeholder="请输入课设学期" suffix-icon="el-icon-message" class="ml-5" v-model="wjyCterm">
        <el-option
            v-for="item in this.term"
            :key="item.value"
            :value="item.value">
        </el-option>
      </el-select>
      <el-select style="width: 200px" placeholder="请输入课程" suffix-icon="el-icon-position" class="ml-5" v-model="wjyCno">
        <el-option
            v-for="item in this.course"
            :key="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
      <el-button class="ml-5" type="success" @click="orderBy">名次排序</el-button>
      <el-button class="ml-5" type="success" @click="handle">查询课程平均成绩</el-button>
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
      <el-table-column prop="wjyScore" label="成绩" >
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
    <el-dialog title="课程平均成绩" :visible.sync="dialogFormVisible" width="30%">
      <el-table :data="avgScoreData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
        <el-table-column prop="wjyCname" label="课程名称" width="240">
        </el-table-column>
        <el-table-column prop="wjyAvgscore" label="平均成绩">
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">

        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "TeacherScore",
  data(){
    return{
      tableData: [],
      avgScoreData:[],
      total:0,
      pageNum:1,
      pageSize:2,
      wjyCno:"",
      wjyCname:"",
      wjyClno:"",
      wjyCterm:"",
      wjyTno:"",
      wjySno:"",
      wjySname:"",
      wjyScore:"",
      wjyAvgScore:"",
      defaultPassword:"1111111",
      form:{},
      course:[],
      term: [{
        value: '2021/2022(1)'
      }, {
        value: '2021/2022(2)'
      }, {
        value: '2022/2023(1)'
      }, {
        value: '2022/2023(2)'
      }],
      dialogFormVisible:false,
      multipleSelection:[],
      headerBg: 'headerBg',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    // this.tno=this.user.id
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
    orderBy(){
      this.request.get("/score-log/orderBy").then(res=>{
        this.tableData=res

      })
    },
    loadCourse() {
      this.request.get("/score-log").then(res=>{
        this.course=[]
        for(let i=0;i<res.length;i++){
          if(!this.course.includes(res[i].wjyCno))
            this.course.push(res[i].wjyCno)
        }
      })
    },
    handle(){
      this.dialogFormVisible = true
      this.request.get("/avg-score").then(res=>{
        this.avgScoreData=res
      })
    },
    reset(){
      this.wjyCno=""
      this.wjyCterm=""
      this.wjyClno=""
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