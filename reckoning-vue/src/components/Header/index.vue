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
      <el-dropdown @command="handleCommand">
        <div class="Avatar-user">
          <div class="username">username</div>
          <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" :size='60'></el-avatar>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a">个人中心</el-dropdown-item>
          <el-dropdown-item command="b">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <div class="personal">
        <el-dialog  :visible.sync="dialogVisible" width="60%">
          
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      levelList: null,
      dialogVisible: false,
    };
  },
  computed: {},
  //监听路由变化
  watch: {
    $route() {
      this.getBreadcrumb();
    },
  },
  methods: {
    //获取路由路径生成面包屑
    getBreadcrumb() {
      //$route.matched一个数组 包含当前路由的所有嵌套路径片段的路由记录
      let matched = this.$route.matched.filter((item) => item.meta.tiltle);
      this.levelList = matched;
    },


    //用户菜单
    handleCommand(command) {
      if (command === "a") {
        this.dialogVisible = true;
      }
      if (command === "b") {
        console.log("退出");
      }
    },
  },
  created() {
    this.getBreadcrumb();
  },
};
</script>

<style lang="scss" scoped>
.header {
  height: 60px;
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
  .el-dropdown {
    height: 60px;
    .Avatar-user {
      display: flex;
      flex-direction: row;
      justify-items: center;
      font-size: 18px;
      .el-dropdown-menu__item {
        font-size: 18px;
      }
      .username {
        line-height: 60px;
        color: #67c23a;
      }
    }
  }
}
</style>