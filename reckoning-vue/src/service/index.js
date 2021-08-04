import axios from 'axios'
// import {
//   Message,
// } from 'element-ui'

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
  return Promise.reject(error);
});

export default http;