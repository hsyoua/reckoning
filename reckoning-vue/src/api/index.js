import service from '@/service/index'

export const setNewUser = (param) => {
  return service({
    url: '/user/register',
    method: 'POST',
    data: param,
  })
}

export const userLogin = (param) => {
  return service({
    url: '/user/login',
    method: 'POST',
    data: param,
  })
}