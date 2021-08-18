<template>
  <div class="login">
    <img src="@/assets/image/1.jpg" />
    <div class="from" key="0" v-if="show">
      <h3>欢迎登录</h3>
      <el-form ref="loginForm" :model="loginForm" :rules="rules1">
        手机号
        <el-form-item prop="mobileNo">
          <el-input
            v-model="loginForm.mobileNo"
            class="input"
            placeholder="请输入手机号"
          />
        </el-form-item>
        密码
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            class="input"
            placeholder="请输入密码"
            :type="pwdType"
            @keyup.enter.native="submitForm('loginForm')"
          >
            <i slot="suffix" class="el-icon-view" @click="showPwd"></i>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            bordered
            class="btn"
            type="primary"
            @click="submitForm('loginForm')"
            >登录</el-button
          >
          <div class="add-newuser">
            <el-link type="primary" :underline="false" @click="addUser"
              >没有账号？点击注册</el-link
            >
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="from" key="1" v-else>
      <h3>欢迎注册</h3>
      <el-form ref="userForm" :model="userForm" :rules="rules2">
        用户名
        <el-form-item prop="userName">
          <el-input
            v-model="userForm.userName"
            class="input"
            placeholder="请输入用户名"
          />
        </el-form-item>
        密码
        <el-form-item prop="password">
          <el-input
            v-model="userForm.password"
            class="input"
            placeholder="请输入密码"
            :type="pwdType"
          >
            <i slot="suffix" class="el-icon-view" @click="showPwd"></i>
          </el-input>
        </el-form-item>
        确认密码
        <el-form-item prop="checkPass">
          <el-input
            :type="pwdType"
            v-model="userForm.checkPass"
            autocomplete="off"
            placeholder="请确认密码"
          >
            <i slot="suffix" class="el-icon-view" @click="showPwd"></i>
          </el-input>
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
            >重置信息</el-button
          >
          <div class="add-newuser">
            <el-link type="primary" :underline="false" @click="addUser"
              >已有账号？返回登录</el-link
            >
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Api from "@/api";
import JSEncrypt from "jsencrypt/bin/jsencrypt";

export default {
  data() {
    var validateUser = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入用户名"));
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
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
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
        mobileNo: "",
        userRemarks: "",
        checkPass: "",
      },
      rules1: {
        mobileNo: [
          { validator: validateMobile, required: true, trigger: "blur" },
        ],
        password: [
          { validator: validatePass, required: true, trigger: "blur" },
        ],
      },
      rules2: {
        userName: [
          { validator: validateUser, required: true, trigger: "blur" },
        ],
        mobileNo: [
          { validator: validateMobile, required: true, trigger: "blur" },
        ],
        password: [
          { validator: validatePass, required: true, trigger: "blur" },
        ],
        checkPass: [
          { validator: validatePass2, required: true, trigger: "blur" },
        ],
      },
      pwdType: "password",
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
            Api.setNewUser(this.userForm).then((res) => {
              if (!(res.data.code === 200)) {
                this.$message.error(res.message);
              }
              if (res.data.code === 200) {
                this.$message({
                  message: "注册成功",
                  type: "success",
                });
                this.loginForm.mobileNo = this.userForm.mobileNo;
                this.loginForm.password = this.userForm.password;
                this.getJSEncrypt(this.loginForm);
              }
            });
          }
          //用户登录
          if (formName === "loginForm") {
            this.getJSEncrypt(this.loginForm);
          }
        } else {
          // console.log("error submit!!");
          return false;
        }
      });
    },
    //切换登录或注册
    addUser() {
      this.show = !this.show;
    },
    //重置注册信息
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //用户登录方法
    userGetLogin(val) {
      Api.userLogin(val).then((res) => {
        // console.log(res);
        if (res.data.code === 200) {
          this.$message({
            message: "登录成功",
            type: "success",
          });
          this.$store.commit("set_token", res.data.data.token);
          this.getUserMessage(res.data.data);
        }
        if (!(res.data.code === 200)) {
          this.$message({
            message: res.data.message,
            type: "error",
          });
        }
      });
    },

    //获取公钥
    getJSEncrypt(userData) {
      Api.getJSEncrypt({phone:userData.mobileNo}).then((res) => {
        if (res.data.code === 200) {
          let encryptor = new JSEncrypt() // 新建JSEncrypt对象
          let publicKey = res.data.data;  //获取公钥
          encryptor.setPublicKey(publicKey) // 设置公钥
          let Parmas = {
            mobileNo:userData.mobileNo,
            password:encryptor.encrypt(userData.password)
          }
          this.userGetLogin(Parmas) // 登录接口
        }
      });
    },

    //获取用户信息
    getUserMessage(pramars) {
      let id = {
        id: pramars.userId,
      };
      Api.queryUserDetail(id).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setUserData", res.data.data);
          this.$router.push("/home");
        }
      });
    },

    //密码是否显示
    showPwd() {
      this.pwdType === "password"
        ? (this.pwdType = "")
        : (this.pwdType = "password");
      let e = document.getElementsByClassName("el-icon-view")[0];
      this.pwdType == ""
        ? e.setAttribute("style", "color: #409EFF")
        : e.setAttribute("style", "color: #c0c4cc");
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
    // height: 100%;
    background-color: white;
    border-radius: 5px;
    padding: 0 40px;

    .el-form-item {
      margin-top: 10px;
      margin-bottom: 30px;

      ::v-deep .el-input__inner {
        height: 50px;
        line-height: 50px;
        font-size: 18px;
      }

      ::v-deep .el-textarea__inner {
        font-size: 18px;
      }

      ::v-deep .el-form-item__error {
        font-size: 16px;
      }
      .add-newuser {
        text-align: right;
      }
      .el-icon-view {
        line-height: 50px;
      }
    }

    .btn {
      margin-top: 10px;
      height: 50px;
      font-size: 18px;
      width: 100%;
    }
  }
}
</style>