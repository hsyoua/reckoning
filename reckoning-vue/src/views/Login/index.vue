<template>
  <div class="login">
    <img src="@/assets/image/1.jpg" />
    <div class="from" v-if="show">
      <h3>欢迎登录</h3>
      <el-form ref="loginForm" :model="loginForm" :rules="rules">
        手机号
        <el-form-item prop="mobileNo">
          <el-input
            v-model="loginForm.mobileNo"
            class="input"
            placeholder="请输入账号"
          />
        </el-form-item>
        密码
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            class="input"
            placeholder="请输入密码"
            type="password"
            @keyup.enter.native="submitForm('loginForm')"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            bordered
            class="btn"
            type="primary"
            @click="submitForm('loginForm')"
            >登录</el-button
          >
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
          <el-input
            v-model="userForm.userName"
            class="input"
            placeholder="请输入账号"
          />
        </el-form-item>
        密码
        <el-form-item prop="password">
          <el-input
            v-model="userForm.password"
            class="input"
            placeholder="请输入密码"
            type="password"
          />
        </el-form-item>
        确认密码
        <el-form-item prop="checkPass">
          <el-input
            type="password"
            v-model="userForm.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        手机号
        <el-form-item prop="mobileNo">
          <el-input
            v-model="userForm.mobileNo"
            class="input"
            placeholder="请输入手机号"
          />
        </el-form-item>
        备注
        <el-form-item prop="userRemarks">
          <el-input
            v-model="userForm.userRemarks"
            class="input"
            placeholder="请输入备注"
            type="textarea"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            bordered
            class="btn"
            type="primary"
            @click="submitForm('userForm')"
            >注册</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button bordered class="btn" @click="resetForm('userForm')"
            >重置</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button bordered class="btn" @click="addUser()">登录</el-button>
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
      let regPhone = /^1[3456789]{1}\d{9}$/;
      if (value === ("" || null)) {
        callback(Error("手机号码不能为空"));
      } else if (!regPhone.test(value)) {
        callback(Error("手机号格式错误"));
      }
      callback();
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.userForm.password) {
        callback(new Error("两次输入密码不一致!"));
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
        checkPass: "",
        mobileNo: "",
        userRemarks: "",
      },
      rules: {
        userName: [{ validator: validateUser, trigger: "blur" }],
        password: [{ validator: validatePass, trigger: "blur" }],
        mobileNo: [{ validator: validateMobile, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
    };
  },
  methods: {
    //登录校验
    submitForm(formName) {
      // console.log(formName)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //用户注册
          if (formName === "userForm") {
            let pramars = {
              userName: this.userForm.userName,
              password: this.userForm.password,
              mobileNo: this.userForm.mobileNo,
              userRemarks: this.userForm.userRemarks,
            };
            Api.setNewUser(pramars).then((res) => {
              // console.log(res);
              if (res.data.code === 50001) {
                this.$message.error(res.message);
              }
              if (res.data.result) {
                this.$message({
                  message: "注册成功",
                  type: "success",
                });
                this.loginForm.mobileNo = this.userForm.mobileNo;
                this.loginForm.password = this.userForm.password;
                this.userGetLogin(this.loginForm);
              }
            });
          }
          //用户登录
          if (formName === "loginForm") {
            this.userGetLogin(this.loginForm);
          }
        } else {
          // console.log("error submit!!");
          return false;
        }
      });
    },
    //切换登录或注册
    addUser() {
      this.show = false;
    },
    //重置注册信息
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //用户登录方法
    userGetLogin(val) {
      Api.userLogin(val).then((res) => {
        // console.log(res);
        if (res.data.code === 20000) {
          this.$store.commit("setUserData", res.data);
          this.$message({
            message: "登录成功",
            type: "success",
          });
          this.$router.push("/home");
        }
        if (!(res.data.code === 2000)) {
          this.$message({
            message: res.data.message,
            type: "error",
          });
        }
      });
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