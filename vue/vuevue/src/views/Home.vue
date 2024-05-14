<template>
<div>
  <el-row :gutter="10" style="margin-bottom: 40px"><!--gutter:为组间容器设置间隔-->
    <el-col :span="12">
      <el-card style="color: #92cbdc">
        <div><i style="padding-right: 5px" class="el-icon-user"></i>学生总数：</div>
        <div style="text-align: center;padding: 10px 0;font-weight: bolder">{{ this.studentTotal }}</div>
        <div style="text-align: center;font-weight:bold"></div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card style="color: #92cbdc">
        <div><i style="padding-right: 5px" class="el-icon-s-shop"></i>教师总数：</div>
        <div style="text-align: center;padding: 10px 0;font-weight: bolder">{{ this.teacherTotal }}</div>
        <div style="text-align: center;font-weight:bold"></div>
      </el-card>
    </el-col>
  </el-row>
  <el-card style="color: #92dcd6">
    <div><i style="padding-right: 5px" class="el-icon-user"></i>学生地区统计：</div>
    <div>
  <el-table :data="areacount">
    <el-table-column prop="wjySarea" label="地区" width="240">
    </el-table-column>
    <el-table-column prop="wjyCount" label="学生人数">
    </el-table-column>
  </el-table>
    </div>
  </el-card>
</div>

</template>

<script>
import MiddleUtil from "@/utils/MiddleUtil";

export default {
  name: "Home",
  data() {
    return {
     studentTotal:'',
      teacherTotal:'',
      tableData: [],
      areacount:[],
      headerBg: 'headerBg',
    }
  },
  methods:{

load(){
  this.request.get("/student").then(res =>{
    console.log(res.length)
    this.studentTotal=res.length
  })
  this.request.get("/teacher").then(res=>{
    console.log(res.length)
    this.teacherTotal=res.length
  })
  this.request.get("/area-count").then(res=>{
    this.areacount=res

  })
}

  },
  mounted() {
    this.load()
  }
}
</script>

<style scoped>

</style>