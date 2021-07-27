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
      <!-- 无子级菜单-->
      <!-- <el-menu-item :index="item.path" v-for="item in noChildren" :key="item.path" @click="clickMenu(item)">
        <i :class="'el-icon-' + item.meta.icon"></i>
        <span slot="title">{{ item.meta.tiltle }}</span>
      </el-menu-item> -->
      <!-- 有子级菜单-->
      <!-- <el-submenu index="2" v-for="(item, index) in hasChildren" :key="index">
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
      <el-menu-item index="/">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-edit"></i>
          <span>编辑页面</span>
        </template>
        <el-menu-item index="/compoment1">选项1</el-menu-item>
        <el-menu-item index="/compoment2">选项2</el-menu-item>
      </el-submenu>

      <!-- <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>导航一</span>
        </template>
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="1-1">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="1-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <template slot="title">选项4</template>
          <el-menu-item index="1-4-1">选项1</el-menu-item>
        </el-submenu>
      </el-submenu> -->
      <!-- <el-menu-item index="3">
        <i class="el-icon-menu"></i>
        <span slot="title">导航二</span>
      </el-menu-item>
      <el-menu-item index="4" disabled>
        <i class="el-icon-document"></i>
        <span slot="title">导航三</span>
      </el-menu-item>
      <el-menu-item index="5">
        <i class="el-icon-setting"></i>
        <span slot="title">导航四</span>
      </el-menu-item> -->
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
  computed: {
    // noChildren() {
    //   return this.asideMenu.filter((item) => !item.children);
    // },
    // hasChildren() {
    //   return this.asideMenu.filter((item) => item.children);
    // },
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    // clickMenu(item) {
    //   // this.$store.commit("selectMenu", item);
    //   console.log(item)
    // },
    // getBreadcrumb() {
    //   console.log()
    //   //$route.matched一个数组 包含当前路由的所有嵌套路径片段的路由记录
    //   let matched = this.$route.matched.filter((item) => item.meta.tiltle);
    //   this.asideMenu = matched;
    // },
  },
  // created() {
  //   this.getBreadcrumb();
  // },
  // watch: {
  //   $route() {
  //     this.getBreadcrumb();
  //   },
  // },
};
</script>

<style lang="scss" scoped>
.menu {
  width: 200px;
  height: 100%;
  background: #545c64;
  .el-menu {
    border: none;
  }
}
</style>