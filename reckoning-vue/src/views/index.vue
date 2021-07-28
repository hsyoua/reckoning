<template>
  <div class="home">
    <el-container class="menu">
      <el-aside width="200px">
        <HomeMenu></HomeMenu>
      </el-aside>
      <el-container class="main">
        <el-header>
          <HomeHeader></HomeHeader>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import HomeMenu from "@/components/Menu";
import HomeHeader from "@/components/Header";

export default {
  data() {
    return {};
  },
  components: {
    HomeMenu,
    HomeHeader,
  },
  computed: {},
};
</script>


<style lang="scss" scoped>
.home {
  height: 100%;
  .menu {
    height: 100%;
  }
  .main {
    height: 100%;
    .el-header {
      padding: 0;
      margin: 0;
    }
    .el-main {
      padding: 0;
      margin: 0;
    }
  }
}
</style>