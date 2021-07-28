<template>
  <div class="login">
    <img src="@/assets/image/1.jpg">
    <div class="from" v-if="show">
      <h3>欢迎登录</h3>
      <el-form ref="loginForm" :model="loginForm" :rules="rules">
        手机号
        <el-form-item prop="mobileNo">
          <el-input v-model="loginForm.mobileNo" class="input" placeholder="请输入账号" />
        </el-form-item>
        密码
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" class="input" placeholder="请输入密码" type="password" @keyup.enter.native="submitForm" />
        </el-form-item>
        <el-form-item>
          <el-button bordered class="btn" type="primary" @click="submitForm('loginForm')">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button bordered class="btn" @click="addUser">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="from" v-else>
      <h3>欢迎注册</h3>
      <el-form ref="userForm" :model="userForm" :rules="rules">
        用户名
        <el-form-item prop="userName">
          <el-input v-model="userForm.userName" class="input" placeholder="请输入账号" />
        </el-form-item>
        密码
        <el-form-item prop="password">
          <el-input v-model="userForm.password" class="input" placeholder="请输入密码" type="password" />
        </el-form-item>
        手机号
        <el-form-item prop="mobileNo">
          <el-input v-model="userForm.mobileNo" class="input" placeholder="请输入手机号" />
        </el-form-item>
        备注
        <el-form-item prop="userRemarks">
          <el-input v-model="userForm.userRemarks" class="input" placeholder="请输入备注" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button bordered class="btn" type="primary" @click="submitForm('userForm')">注册</el-button>
        </el-form-item>
        <el-form-item>
          <el-button bordered class="btn" @click="resetForm('userForm')">重置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button bordered class="btn" @click="addUser">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Api from "@/api";

export default {
  data() {
    var validateUser = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入用户名"));
      } else {
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };

    var validateMobile = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };

    return {
      show: true,
      loginForm: {
        mobileNo: "",
        password: "",
      },
      userForm: {
        userName: "",
        password: "",
        mobileNo: "",
        userRemarks: "",
      },
      rules: {
        userName: [{ validator: validateUser, trigger: "blur" }],
        password: [{ validator: validatePass, trigger: "blur" }],
        mobileNo: [{ validator: validateMobile, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      // console.log(formName)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (formName === "userForm") {
            Api.setNewUser(this.userForm).then((res) => {
              if (res.code === 50001) {
                this.$message.error(res.message);
              }
            });
          }
          if (formName === "loginForm") {
            Api.userLogin(this.loginForm).then((res) => {
              if (res.code === 50001) {
                this.$message.error(res.message);
              }
            });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    addUser() {
      this.show = false;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style lang="scss" scoped>
.login {
  //background: url("../../assets/image/1.jpg") no-repeat;
  //background-size: 100% 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;

  img {
    height: 100%;
    width: 75%;
  }

  .from {
    width: 25%;
    //height: 230px;
    background-color: white;
    border-radius: 5px;
    padding: 40px;

    .el-form-item {
      margin-top: 10px;
      margin-bottom: 30px;

      ::v-deep .el-input__inner {
        height: 55px;
        line-height: 55px;
        font-size: 18px;
      }

      ::v-deep .el-textarea__inner {
        font-size: 18px;
      }

      ::v-deep .el-form-item__error {
        font-size: 16px;
      }
    }

    .btn {
      margin-top: 10px;
      height: 55px;
      font-size: 18px;
      width: 100%;
    }
  }
}
</style>