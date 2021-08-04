import Vue from 'vue'
// 获取电影列表
export default Vue.directive('loadmore', {
  bind (el, binding, vnode) {
    // console.log(el, binding, vnode)
    // 获取element-ui定义好的scroll盒子
    // console.log(el, 'el')
    // console.log(vnode, 'vnode')
    // console.log('bind', binding)
    const SELECTWRAP_DOM = el.querySelector('.el-select-dropdown .el-select-dropdown__wrap')
    SELECTWRAP_DOM.addEventListener('scroll', function () {
      const CONDITION = this.scrollHeight - this.scrollTop <= this.clientHeight
      if (CONDITION) {
        binding.value()
      }
    })
  }
})