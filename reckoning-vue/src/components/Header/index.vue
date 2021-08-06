<template>
  <div class="header">
    <div class="nav">
      <!-- 面包屑导航栏 -->
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item v-for="item in levelList" :key="item.path">
          <router-link :to="item.path">{{ item.meta.tiltle }}</router-link>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="Avatar">
      <!-- 头像与下拉菜单 -->
      <el-dropdown @command="handleCommand">
        <div class="Avatar-user">
          <div class="username">{{ userForm.userName }}</div>
          <el-avatar
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            :size="60"
          ></el-avatar>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a">个人中心</el-dropdown-item>
          <el-dropdown-item command="b">修改密码</el-dropdown-item>
          <el-dropdown-item command="e">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <!-- 修改密码 -->
      <div class="personal">
        <el-dialog title="修改密码" :visible.sync="dialogVisible" width="30%">
          <el-form
            ref="passwordForm"
            :model="passwordForm"
            :rules="rules"
            label-width="80px"
          >
            <el-form-item label="旧密码" prop="oldPass">
              <el-input
                v-model="passwordForm.oldPass"
                class="input"
                placeholder="请输入旧密码"
                type="password"
              />
            </el-form-item>
            <el-form-item label="新密码" prop="newPass">
              <el-input
                v-model="passwordForm.newPass"
                class="input"
                placeholder="请输入新密码"
                type="password"
              />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmNesPass">
              <el-input
                type="password"
                v-model="passwordForm.confirmNesPass"
                autocomplete="off"
                placeholder="请确认新密码"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                bordered
                class="btn"
                type="primary"
                @click="submitForm('passwordForm')"
                >保存</el-button
              >
              <el-button bordered class="btn" @click="resetForm('passwordForm')"
                >重置</el-button
              >
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import Api from "@/api";

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入新密码"));
      } else {
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.passwordForm.confirmNesPass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      levelList: null,
      dialogVisible: false,
      userForm: "",
      passwordForm: {
        oldPass: "",
        newPass: "",
        confirmNesPass: "",
        userId: "",
      },
      rules: {
        oldPass: [{ message: "请输入旧密码", required: true, trigger: "blur" }],
        newPass: [{ validator: validatePass, required: true, trigger: "blur" }],
        confirmNesPass: [
          { validator: validatePass2, required: true, trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getBreadcrumb();
  },
  mounted() {
    this.userForm = JSON.parse(sessionStorage.getItem("userData"));
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
        this.$router.push("/userPage");
      }
      if (command === "b") {
        this.dialogVisible = true;
      }
      if (command === "e") {
        sessionStorage.clear();
        this.$router.push("/");
        console.log("退出");
      }
    },

    //重置注册信息
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    //修改密码
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.passwordForm.userId = this.userForm.userId;
          Api.updatePassword(this.passwordForm).then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "修改密码成功",
                type: "success",
              });
              this.dialogVisible = false;
            }
          });
        }
      });
    },
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