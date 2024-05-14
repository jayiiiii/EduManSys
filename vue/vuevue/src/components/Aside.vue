<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  ><!--router实现路由-->=
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
      <b style="color: white" v-show="logoTextShow">高校成绩管理系统</b>
    </div>
      <el-menu-item index="/home">
        <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title" v-show="logoTextShow">主页</span>
        </template>
      </el-menu-item>
    <el-submenu index="2"v-show="adminShow">
      <template slot="title">
        <i class="el-icon-school"></i>
        <span slot="title" v-show="logoTextShow">系统管理</span>
      </template>
      <el-menu-item index="/student">
        <template slot="title">
          <i class="el-icon-user"></i>
          <span slot="title" v-show="logoTextShow">学生管理</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/teacher">
        <template slot="title">
          <i class="el-icon-user-solid"></i>
          <span slot="title" v-show="logoTextShow">教师管理</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/cinfoview">
        <template slot="title">
          <i class="el-icon-notebook-1"></i>
          <span slot="title" v-show="logoTextShow">课程管理</span>
        </template>
      </el-menu-item>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-tickets"></i>
        <span slot="title" v-show="logoTextShow">信息查询</span>
      </template>
      <el-menu-item index="/teacherlist">
        <template slot="title">
          <i class="el-icon-user-solid"></i>
          <span slot="title" v-show="logoTextShow">教师通讯录</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/cinfostudent">
        <template slot="title">
          <i class="el-icon-notebook-2"></i>
          <span slot="title" v-show="logoTextShow">开课查询</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/stuscore" v-show="studentShow">
        <template slot="title">
          <i class="el-icon-notebook-2"></i>
          <span slot="title" v-show="logoTextShow">个人成绩查询</span>
        </template>
      </el-menu-item>
    </el-submenu>
    <el-menu-item index="/scorelog" v-show="teacherShow">
      <template slot="title">
        <i class="el-icon-edit"></i>
        <span slot="title" v-show="logoTextShow">成绩录入</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/teacherscore" v-show="teacherShow">
      <template slot="title">
        <i class="el-icon-search"></i>
        <span slot="title" v-show="logoTextShow">成绩查询</span>
      </template>
    </el-menu-item>
  </el-menu>
</template>


<script>
import MiddleUtil from "@/utils/MiddleUtil";

export default {
  name: "Aside",
  data(){
    return {
      form: {},
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      adminShow:false,
      teacherShow:false,
      studentShow:false
    }
  },
  props:{
    isCollapse:Boolean,
    logoTextShow:Boolean
  },
  methods:{

  },
  created(){
    var user=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
   if(user.wjyType === 'admin')this.adminShow=true
    if(user.wjyType === 'teacher') this.teacherShow=true
    if(user.wjyType === 'student') this.studentShow=true
  }
}
</script>

<style scoped>

</style>