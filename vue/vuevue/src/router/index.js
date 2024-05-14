import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: ()=>import('../views/Manage.vue'),
    redirect:"/login",
    children:[
      {path: 'home', name: '首页', component: ()=>import('../views/Home.vue')},
      { path: 'person', name: '个人信息', component: () => import('../views/Person.vue')},
      { path: 'student', name: '学生管理', component: () => import('../views/Student.vue')},
      { path: 'teacher', name: '教师管理', component: () => import('../views/Teacher.vue')},
      { path: 'cinfoview', name: '课程管理', component: () => import('../views/CinfoView.vue')},
      { path: 'teacherlist', name: '教师通讯录', component: () => import('../views/TeacherList.vue')},
      { path: 'cinfostudent', name: '开课查询', component: () => import('../views/CinfoStudent.vue')},
      { path: 'scorelog', name: '成绩录入', component: () => import('../views/ScoreLog.vue')},
      { path: 'stuscore', name: '个人成绩查询', component: () => import('../views/StuScore.vue')},
      { path: 'teacherscore', name: '成绩查询', component: () => import('../views/TeacherScore.vue')}
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
{
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新
  next()  // 放行路由
})
export default router
