import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userData: sessionStorage.getItem('userData') ? sessionStorage.getItem('userData') : '',
  },
  mutations: {
    setUserData: (state, userData) => {
      state.userData = userData;
      sessionStorage.setItem('userData', JSON.stringify(userData));
    },
  },
  actions: {},
  modules: {}
})