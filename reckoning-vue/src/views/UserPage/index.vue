<template>
  <div class="user">
    <div class="user-avatar">
      <el-avatar
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        :size="200"
      ></el-avatar>
    </div>
    <div class="user-message">
      <h3>个人信息</h3>
      <el-form
        ref="userData"
        :model="userData"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="用户名" prop="userName">
          <el-input
            v-model="userData.userName"
            class="input"
            placeholder="请输入用户名"
          />
        </el-form-item>
        <el-form-item label="手机号" prop="mobileNo">
          <el-input
            v-model="userData.mobileNo"
            class="input"
            placeholder="请输入手机号"
          />
        </el-form-item>
        <el-form-item label="备注" prop="userRemarks">
          <el-input
            v-model="userData.userRemarks"
            class="input"
            placeholder="请输入备注"
            type="textarea"
          />
        </el-form-item>
        <el-form-item>
          <el-button bordered class="btn" @click="resetForm('userData')"
            >重置</el-button
          >
          <el-button
            bordered
            type="primary"
            class="btn"
            @click="submitForm('userData')"
            >保存</el-button
          >
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
    var validateMobile = (rule, value, callback) => {
      let regPhone = /^1[3456789]{1}\d{9}$/;
      if (value === ("" || null)) {
        callback(Error("手机号码不能为空"));
      } else if (!regPhone.test(value)) {
        callback(Error("手机号格式错误"));
      }
      callback();
    };
    return {
      userData: {
        userName: "",
        mobileNo: "",
        userRemarks: "",
        userId: "",
      },
      rules: {
        userName: [
          { validator: validateUser, trigger: "blur" },
        ],
        mobileNo: [
          { validator: validateMobile, trigger: "blur" },
        ],
      },
      disabled: true,
    };
  },
  mounted() {
    this.userData = JSON.parse(sessionStorage.getItem("userData"));
    this.getUserMessage();
  },
  methods: {
    //登录校验
    submitForm(formName) {
      // console.log(formName)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.userData);
          Api.updateUser(this.userData).then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "修改信息成功",
                type: "success",
              });
              location.reload();
            }
          });
        } else {
          // console.log("error submit!!");
          return false;
        }
      });
    },
    //重置注册信息
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //获取用户信息
    getUserMessage() {
      Api.queryUserDetail(this.userData.userId).then((res) => {
        if (res.data.code === 200) {
          this.userData = res.data.data;
        }
      });
    },
    //修改用户信息
    editMessage() {
      this.disabled = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.user {
  display: flex;
  flex-direction: column;
  align-items: center;
  .user-message {
    width: 400px;
    // padding: 20px;
    text-align: center;
    .userMessage {
      height: 50px;
      font-size: 18px;
      line-height: 50px;
      span {
        padding: 0 10px;
      }
      input {
        line-height: 26px;
        font-size: 18px;
        border: none;
        background-color: #ffffff;
      }
      :focus {
        outline: 1px solid #afecab;
      }
      i {
        padding: 0 10px;
      }
    }
  }
}
</style>