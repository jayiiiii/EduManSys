<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>高校成绩系统登录界面</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="id">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.wjyId"
                    placeholder="请输入登录ID"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.wjyUpassword" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <Vcode :show="isShow" @success="success" @close="close"/>
          <div style="text-align: center">
            <el-button type="primary" size="small" autocomplete="off" @click=" submit()">登录</el-button>
          </div>
        </el-form-item>
      </el-form>

    </div>

  </div>
</template>

<script>
import Vcode from "vue-puzzle-vcode";

export default {
  name: "Login",
  components: {
    Vcode
  },
  data() {
    return {
      isShow: false,
      user: {
        wjyId: '',
        wjyUpassword: '',
        wjyName: '',
        wjyType: ''
      },
      rules: {
        wjyId: [
          {required: true, message: '请输入登录名', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在1 到 10个字符', trigger: 'blur'}
        ],
        wjyUpassword: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    submit() {
      this.isShow = true;
    },
    login() {
      if (this.user.wjyId === "" || this.user.wjyUpassword === "") {
        this.$message.error("请输入用户名和密码！")
      } else {
        this.$refs['userForm'].validate((valid) => {

          if (valid) {  // 表单校验合法
            this.request.post("/user/login", this.user).then(res => {
              if (res.code === '200') {
                localStorage.setItem("user", JSON.stringify(res.data))//获取信息到用户浏览器
                this.$router.push("/home")
                this.$message.success("登录成功")

              } else {
                this.$message.error(res.msg)

              }
            })
          }
        });
      }
    },
    success(msg) {
      this.isShow = false; // 通过验证后，需要手动隐藏模态框
      this.login()
    },
    // 用户点击遮罩层，应该关闭模态框
    close() {
      this.isShow = false;
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh; /*视图窗口高度*/
  background-image: linear-gradient(to bottom right, #f2e6ce, #b9dec9);
  overflow: hidden;
}
</style>