<template>
  <div class="header">
    <div class="nav">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item v-for="item in levelList" :key="item.path">
          <router-link :to="item.path">{{item.meta.tiltle}}</router-link>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="Avatar">
      <el-dropdown>
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" :size='60'></el-avatar>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>查看用户信息</el-dropdown-item>
          <el-dropdown-item>修改用户信息</el-dropdown-item>
          <el-dropdown-item>切换账号</el-dropdown-item>
          <el-dropdown-item>退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      levelList: null,
    };
  },
  watch: {
    $route() {
      this.getBreadcrumb();
    },
  },
  methods: {
    getBreadcrumb() {
      //$route.matched一个数组 包含当前路由的所有嵌套路径片段的路由记录
      let matched = this.$route.matched.filter((item) => item.meta.tiltle);
      this.levelList = matched;
    },
  },
  created() {
    this.getBreadcrumb();
  },
};
</script>

<style lang="scss" scoped>
.header {
  padding: 0 20px;
  background: #f2f6fc;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  .nav {
    height: 60px;
    .el-breadcrumb {
      line-height: 60px;
      font-size: 18px;
    }
  }
}
</style>