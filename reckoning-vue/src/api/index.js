import service from '@/service/index'


/******************************登录接口*****************************/ 
//注册接口
const setNewUser = (param) => {
  return service({
    url: '/user/register',
    method: 'POST',
    data: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}

//登录接口
const userLogin = (param) => {
  return service({
    url: '/user/login',
    method: 'POST',
    data: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}

//获取公钥
const getJSEncrypt = (param) => {
  return service({
    url: '/key/getPublicKey',
    method: 'GET',
    params: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}


/******************************用户接口*****************************/ 

//用户查询接口
const userFound = (param) => {
  return service({
    url: '/user/queryUser',
    method: 'POST',
    data: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}

//用户修改密码接口
const updatePassword = (param) => {
  return service({
    url: '/user/updatePassword',
    method: 'POST',
    data: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}

//用户修改信息接口
const updateUser = (param) => {
  return service({
    url: '/user/updateUser',
    method: 'POST',
    data: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}

//用户信息接口
const queryUserDetail = (param) => {
  return service({
    url: '/user/queryUserDetail',
    method: 'GET',
    params: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}


/******************************账单接口*****************************/ 

//新增账单
const addNewBill = (param) => {
  return service({
    url: '/billing/addBill',
    method: 'POST',
    data: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}

//账单信息
const queryBilling = (param) => {
  return service({
    url: '/billing/queryBilling',
    method: 'POST',
    data: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}


//账单详情
const fingBillDetail = (param) => {
  return service({
    url: '/billing/fingBillDetail',
    method: 'POST',
    data: param,
    headers: {
      'Content-Type': 'application/json',
  },
  })
}



let Api = {
  setNewUser,
  userLogin,
  userFound,
  addNewBill,
  updatePassword,
  updateUser,
  queryUserDetail,
  getJSEncrypt,
  fingBillDetail,
  queryBilling
}

export default Api;