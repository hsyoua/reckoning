import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userData: sessionStorage.getItem('userData') ? sessionStorage.getItem('userData') : '',
    token: '',
  },
  mutations: {
    setUserData: (state, userData) => {
      state.userData = userData;
      sessionStorage.setItem('userData', JSON.stringify(userData));
    },
    set_token(state, token) {
      state.token = token
      sessionStorage.token = token
    },
    del_token(state) {
      state.token = ''
      sessionStorage.removeItem('token')
    }
  },
  actions: {},
  modules: {}
})