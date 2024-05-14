<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入教师编号" suffix-icon="el-icon-search" v-model="wjyTno"></el-input>
      <el-input style="width: 200px" placeholder="请输入职位" suffix-icon="el-icon-message" class="ml-5" v-model="wjyTposition"></el-input>
      <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-position" class="ml-5" v-model="wjyTname"></el-input>
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
      <el-upload action="http://localhost:9090/teacher/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">//表格数据需要绑定
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="wjyTno" label="教师编号" width="80">
      </el-table-column>
      <el-table-column prop="wjyTname" label="教师姓名" width="140">
      </el-table-column>
      <el-table-column prop="wjyTsex" label="性别" width="120">
      </el-table-column>
      <el-table-column prop="wjyTage" label="年龄" width="120">
      </el-table-column>
      <el-table-column prop="wjyTposition" label="职位" width="120">
      </el-table-column>
      <el-table-column prop="wjyTnumber" label="联系方式">
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
              @confirm="handleDel(scope.row.wjyTno)"
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
    <el-dialog title="教师信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="教师编号">
          <el-input v-model="form.wjyTno" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="教师姓名">
          <el-input v-model="form.wjyTname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.wjyTsex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.wjyTage" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="form.wjyTposition" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="form.wjyTnumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.wjyTpassword" autocomplete="off"></el-input>
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
  name: "Teacher",
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
          wjyTname:this.wjyTname,
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
    handleAdd(){
      this.dialogFormVisible=true//看见表单
      this.form={}
    },
    handleDel(tno){
      this.request.delete("/teacher/"+tno).then(res =>{
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
      let tnos=this.multipleSelection.map(v =>v.wjyTno)
      this.request.post("/teacher/del/batch",tnos).then(res =>{
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


