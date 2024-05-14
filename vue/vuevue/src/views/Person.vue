
<template>
  <div>
    <el-card class="box-card">
    <el-form label-width="80px" size="small">
      <el-form-item label="编号ID" label-width="20%">
        <el-input v-model="form.wjyId" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="姓名" label-width="20%">
        <el-input v-model="form.wjyName" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="用户类型" label-width="20%">
        <el-input v-model="form.wjyType" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="密码" label-width="20%">
        <el-input v-model="form.wjyUpassword" autocomplete="off" :disabled="true" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="editPassword" align="left" style="padding-right: 10px">修改密码</el-button>
      </el-form-item>
    </el-form>
    </el-card>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="旧密码">
          <el-input v-model="form.wjyUpassword" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="form.newpassword" autocomplete="off" show-password></el-input>
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
import MiddleUtil from "@/utils/MiddleUtil";

export default {
  name: "Person",
    data(){
      return {
        form: {},
       sosForm:{},
        wjyId:"",
        wjyName:"",
        wjyType:"",
        wjyUpassword:"",
        newpassword:"",
        dialogFormVisible:false,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
      }
    },
  created() {
      this.load()
  },
  methods:{
    load(){
      this.form.wjyId=this.user.wjyId
      this.form.wjyName=this.user.wjyName
      this.form.wjyType=this.user.wjyType
      this.form.wjyUpassword=this.user.wjyUpassword
    },
    save(){
      this.form.wjyUpassword=this.form.newpassword
      this.request.post("/user",this.form).then(res =>{
        if(res){
          this.$message.success("修改成功，请重新登录")//清空数据
          this.dialogFormVisible=false
        }else {
          this.$message.error("保存失败")
        }
        this.load()
      })
      this.newpassword=""
      this.$router.push("/login")
      localStorage.removeItem("user")

    },
    editPassword(){
      this.dialogFormVisible=true;
    }
  }


}
</script>

<style scoped>

</style>
