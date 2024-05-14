<template>
  <div>
    <div style="margin: 10px 0">
      <el-select style="width: 200px" placeholder="请输入专业" suffix-icon="el-icon-position" class="ml-5" v-model="wjyDept" @change="loadClass(),loadSno()">
        <el-option
            v-for="item in this.dept"
            :key="item"
            :value="item">
        </el-option>
    </el-select>

      <el-select style="width: 200px" placeholder="请输入班级" suffix-icon="el-icon-message" class="ml-5" v-model="wjyClno" @change="loadSno">
        <el-option
            v-for="item in this.class"
            :key="item"
            :value="item">
        </el-option>
      </el-select>
      <el-select
          style="width: 200px" placeholder="请输入学号" suffix-icon="el-icon-search" v-model="wjySno" >
        <el-option
            v-for="item in this.sno"
            :key="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="你确定删除吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" >批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:9090/student/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">//表格数据需要绑定
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="wjySno" label="学号" width="80">
      </el-table-column>
      <el-table-column prop="wjySname" label="学生姓名" width="140">
      </el-table-column>
      <el-table-column prop="wjyClno" label="班级编号" width="120">
      </el-table-column>
      <el-table-column prop="wjyDept" label="专业" width="120">
      </el-table-column>
      <el-table-column prop="wjySsex" label="性别" width="120">
      </el-table-column>
      <el-table-column prop="wjySage" label="年龄" width="120">
      </el-table-column>
      <el-table-column prop="wjySarea" label="地区" width="120">
      </el-table-column>
      <el-table-column prop="wjyScredits" label="总学分">
      </el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="你确定删除吗？"
              @confirm="handleDel(scope.row.wjySno)"
              class="ml-5"
          >
            <el-button type="danger" slot="reference" >删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
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
    <el-dialog title="学生信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="学号">
          <el-input v-model="form.wjySno" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="form.wjySname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="班级编号">
          <el-input v-model="form.wjyClno" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.wjyDept" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.wjySsex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.wjySage" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地区">
          <el-input v-model="form.wjySarea" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="总学分">
          <el-input v-model="form.wjyScredits" autocomplete="off" :disabled="true" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Student",
  data(){
    return{
      dept:[],
      class:[],
      sno:[],
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:2,
      wjySno:"",
     wjySname:"",
      wjyClno:"",
      wjyDept:"",
      wjyDno:"",
      wjySsex:"",
     wjySage:"",
     wjySarea:"",
     wjyScredits:"",
      wjySpassword:"",
      defaultPassword:"1111111",
      form:{},
      dialogFormVisible:false,
      multipleSelection:[],
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load()
    this.loadDept()
    this.loadClass()
    this.loadSno()
  },
  methods:{
    loadClass(){
      console.log(this.wjyDept);
      this.request.get("/dept").then(res=>{
            for(let i=0;i<res.length;i++) {
              if (res[i].wjyDept === this.wjyDept) {
                this.wjyDno = res[i].wjyDno;
                break;
              }
            }
        //console.log( this.wjyDno);
      })
      this.request.get("/class").then(res=>{
        this.class=[];
        for(let i=0;i<res.length;i++){
          if(this.wjyDept!=="") {
            if (res[i].wjyDno ===  this.wjyDno) {
              this.class.push(res[i].wjyClno);
            }
          }
         else{this.class.push(res[i].wjyClno)}
        }
      })
    },
    loadDept(){
      console.log("in");
      this.request.get("/dept").then(res=>{
        this.dept=[]
        for(let i=0;i<res.length;i++){
          this.dept.push(res[i].wjyDept)
        }
      })
    },
   loadSno(){

     this.request.get("/student").then(res=>{
       this.sno=[];
       if(this.wjyDept!=="") {
           for(let i=0;i<res.length;i++){
           if (res[i].wjyDept ===  this.wjyDept) {
             this.sno.push(res[i].wjySno);
           }
         }
       }
       else if(this.wjyClno!=="") {
         for (let i = 0; i < res.length; i++) {
           if (res[i].wjyClno === this.wjyClno) {
             this.sno.push(res[i].wjySno);
           }
         }
       }
         else
         { for (let i = 0; i < res.length; i++)
           this.sno.push(res[i].wjySno)
         }

     })
},
    load() {
      this.request.get("/student/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          wjySno:this.wjySno,
          wjyClno:this.wjyClno,
          wjyDept:this.wjyDept
        }
      }).then(res =>{console.log(res)
        this.tableData=res.records
        this.total=res.total
        console.log(this.tableData)
      })

    },
    reset(){
      this.wjySno=""
      this.wjyClno=""
      this.wjyDept=""
      this.wjyDno=""
      this.load()
      this.loadDept()
      this.loadClass()
      this.loadSno()
    },
    save(){
      this.request.post("/student",this.form).then(res =>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
        }else {
          this.$message.error("保存失败")
        }
        this.load()
      })
    },
    handleAdd(){
      this.dialogFormVisible=true//看见表单
      this.form={}
    },
    handleDel(sno){
      this.request.delete("/student/"+sno).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.dialogFormVisible=false
        }else {
          this.$message.error("删除失败")
        }
        this.load()
      })
    },
    handleEdit(row){
      this.dialogFormVisible=true
      this.form=row//已经绑定过save（）
      this.load()
    },
    delBatch(){
      let snos=this.multipleSelection.map(v =>v.wjySno)
      this.request.post("/student/del/batch",snos).then(res =>{
        if(res){
          this.$message.success("批量删除成功")
          this.dialogFormVisible=false
        }else {
          this.$message.error("批量删除失败")
        }

      })
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