import service from '@/service/index'

//注册接口
const setNewUser = (param) => {
  return service({
    url: '/user/register',
    method: 'POST',
    data: param,
  })
}

//登录接口
const userLogin = (param) => {
  return service({
    url: '/user/login',
    method: 'POST',
    data: param,
  })
}

//用户查询接口
const userFound = (param) => {
  return service({
    url: '/user/queryUser',
    method: 'POST',
    data: param,
  })
}

//新增账单
const addNewBill = (param) => {
  return service({
    url: '/billing/addBill',
    method: 'POST',
    data: param,
  })
}

let Api = {
  setNewUser,
  userLogin,
  userFound,
  addNewBill
}

export default Api;