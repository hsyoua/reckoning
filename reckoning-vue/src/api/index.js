import service from '@/service/index'

const setNewUser = (param) => {
  return service({
    url: '/user/register',
    method: 'POST',
    data: param,
  })
}

const userLogin = (param) => {
  return service({
    url: '/user/login',
    method: 'POST',
    data: param,
  })
}

let Api = {
  setNewUser,
  userLogin
}

export default Api;