import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../views/Login'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'login',
    component: login
  },
  {
    path: '/home',
    name:'home',
    component: () => import('@/views'),
    meta: {
      tiltle: '首页'
    },
    redirect: 'index',
    children: [
      {
        path: '/index',
        name: 'index',
        component: () => import('@/views/Home'),
      },
      {
        path: '/compoment1',
        name: 'compoment1',
        component: () => import('@/views/compoments/compoment1'),
        meta: {
          tiltle: '第一个页面'
        },
      },
      {
        path: '/compoment2',
        name: 'compoment2',
        component: () => import('@/views/compoments/compoment2'),
        meta: {
          tiltle: '第二个页面'
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

export default router