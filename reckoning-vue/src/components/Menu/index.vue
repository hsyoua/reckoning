<template>
  <div class="menu">
    <el-menu default-active="1" 
    class="el-menu-vertical-demo" 
    @open="handleOpen" 
    @close="handleClose" 
    background-color="#545c64" 
    text-color="#fff" 
    active-text-color="#ffd04b" 
    router>
      <!-- 动态生成菜单 -->
      <!-- 无子级菜单
      <el-menu-item :index="item.path" v-for="item in noChildren" :key="item.path" @click="clickMenu(item)">
        <i :class="'el-icon-' + item.meta.icon"></i>
        <span slot="title">{{ item.meta.tiltle }}</span>
      </el-menu-item>
      有子级菜单
      <el-submenu index="2" v-for="(item, index) in hasChildren" :key="index">
        <template slot="title">
          <i :class="'el-icon-' + item.meta.icon"></i>
          <span>{{ item.meta.tiltle }}</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="subItem.path" v-for="(subItem, subIndex) in item.children" :key="subIndex" @click="clickMenu(subItem)">
            <i :class="'el-icon-' + subItem.meta.icon"></i>
            <span>{{ subItem.meta.tiltle }}</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu> -->
      <!-- 固定菜单 -->
      <el-menu-item index="/home">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-edit"></i>
          <span>账单管理</span>
        </template>
        <el-menu-item index="/billingAdd">新增账单</el-menu-item>
        <el-menu-item index="/bill">我的账单</el-menu-item>
      </el-submenu>
      <el-menu-item index="/userPage">
        <i class="el-icon-user-solid"></i>
        <span slot="title">个人中心</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    //返回一个数组，通过数组的方式循环渲染出来
    return {
      asideMenu: "",
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style lang="scss" scoped>
.menu {
  width: 200px;
  height: 100%;
  background: #545c64;
  .el-menu {
    border: none;
    .el-menu-item {
      font-size: 18px;
    }
    ::v-deep .el-submenu__title {
      font-size: 18px;
    }
  }
}
</style>