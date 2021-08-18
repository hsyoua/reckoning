import axios from 'axios';
import store from '../store';
import router from '../router';
// import {
//   Message,
// } from 'element-ui'


// 页面刷新时，重新赋值token
if (sessionStorage.getItem('token')) {
  store.commit('set_token', sessionStorage.getItem('token'))
}


const http = axios.create({
  baseURL: process.env.NODE_ENV === 'development' ? '/api' : '/',
  headers: {
    'Content-Type': 'application/json',
  },
})


// 添加请求拦截器
http.interceptors.request.use(function (config) {
  // console.log(config);
  // 在发送请求之前做些什么
  //判断是否存在token，如果存在将每个页面header都添加token
  if(store.state.token){
    config.headers.common['token']=store.state.token
  }

  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 添加响应拦截器
http.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  // if(!(response.data.code === 2000)){
  //   Message({
  //     message:response.data.message,
  //     type:'error'
  //   })
  // }
  return response;
}, function (error) {
  // 对响应错误做点什么
  if (error.response) {
    switch (error.response.status) {
      case 401:
      this.$store.commit('del_token');
      router.replace({
        path: '/login',
        query: {redirect: router.currentRoute.fullPath}//登录成功后跳入浏览的当前页面
      })
      break
    }
  }
  return Promise.reject(error);
});

export default http;