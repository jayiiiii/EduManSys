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

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAddCourse">新增课程信息<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="warning" @click="handleAddCinfo">新增开课班级<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">//表格数据需要绑定
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
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
    <el-dialog title="课程信息" :visible.sync="dialogFormVisibleCourse" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="课程编号">
          <el-input v-model="formCourse.wjyCno" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="formCourse.wjyCname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开设学期">
          <el-input v-model="formCourse.wjyCterm" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="formCourse.wjyCcredit" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleCourse = false">取 消</el-button>
        <el-button type="primary" @click="saveCourse">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="开课信息" :visible.sync="dialogFormVisibleCinfo" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="课程编号">
          <el-select v-model="formCinfo.wjyCno" autocomplete="off" @change="loadCname">
            <el-option
                v-for="item in this.course"
                :key="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程名" >
          <el-select v-model="coname" autocomplete="off"  :disabled="true" >
          </el-select>
        </el-form-item>
        <el-form-item label="教师编号">
          <el-select v-model="formCinfo.wjyTno" autocomplete="off" >
            <el-option
                v-for="item in this.teacher"
                :key="item"
                :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开设班级">
          <el-select v-model="formCinfo.wjyClno" autocomplete="off">
            <el-option
                v-for="item in this.class"
                :key="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleCinfo = false">取 消</el-button>
        <el-button type="primary" @click="saveCinfo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "CinfoView",
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
      coname:'',
      teacher:[],
      class:[],
      multipleSelection:[],
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load()
    this.loadCourse()
    this.loadTeacher()
    this.loadClass()
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
    loadCourse(){
      this.request.get("/course").then(res=>{
        this.course=[]
        for(let i=0;i<res.length;i++){
          this.course.push(res[i].wjyCno)
        }
      })
    },
    loadCname(){
      console.log("in");
      this.request.get("/course").then(res=>{
        for(let i=0;i<res.length;i++){
          console.log("for");
          console.log(this.formCinfo.wjyCno);
        if(res[i].wjyCno===this.formCinfo.wjyCno){this.coname=res[i].wjyCname;break;}
        }
      })

    },
    loadTeacher(){
      this.request.get("/teacher").then(res=>{
        this.teacher=[]
        for(let i=0;i<res.length;i++){
          this.teacher.push(res[i].wjyTno)
        }
      })
    },
    loadClass(){
      this.request.get("/class").then(res=>{
        this.class=[]
        for(let i=0;i<res.length;i++){
          this.class.push(res[i].wjyClno)
        }
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
    saveCourse(){
      this.request.post("/course",this.formCourse).then(res =>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisibleCourse=false
        }else {
          this.$message.error("保存失败")
        }
        this.load()
        this.loadCourse()
      })

    },
    saveCinfo(){
      this.request.post("/cinfo",this.formCinfo).then(res =>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisibleCinfo=false
        }else {
          this.$message.error("保存失败")
        }
        this.load()
      })
    },
    handleAddCourse(){
      this.dialogFormVisibleCourse=true//看见表单
      this.formCourse={}
    },
    handleAddCinfo(){
      this.dialogFormVisibleCinfo=true//看见表单
      this.formCinfo={}
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