import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../views/Login'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: 'login',
  },
  {
    path: '/login',
    name: 'login',
    component: login
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/views'),
    meta: {
      tiltle: '首页'
    },
    redirect: 'index',
    children: [{
        path: '/index',
        name: 'index',
        component: () => import('@/views/Home'),
      },
      {
        path: '/bill',
        name: 'bill',
        component: () => import('@/views/Bill'),
        meta: {
          tiltle: '我的账单'
        },
      },
      {
        path: '/billingAdd',
        name: 'billingAdd',
        component: () => import('@/views/Bill/billingAdd.vue'),
        meta: {
          tiltle: '新增账单',
          keepAlive:true,
        },
      },
      {
        path: '/userPage',
        name: 'userPage',
        component: () => import('@/views/UserPage'),
        meta: {
          tiltle: '个人中心',
          keepAlive:true,
        },
      },
    ]
  },

  {
    path: '*',
    name: '404',
    component: () => import('@/views/404'),
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next();
  } else {
    //非跳转到登录界面时，判断本地存储userData是否为null或空，为空则跳回到登录界面，反之到相应的界面(此时有数据)。
    let userData = JSON.parse(sessionStorage.getItem('userData'));
    if (userData === null || userData === '') {
      next('/login');
    } else {
      next();
    }
  }
});


export default router