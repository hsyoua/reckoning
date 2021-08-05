<template>
  <div class="newbill">
    <div class="billFrom">
      <el-form
        ref="billFrom"
        :model="billFrom"
        label-width="80px"
        :rules="rules"
      >
        <el-form-item label="账单主题" prop="billTheme">
          <el-input
            v-model="billFrom.billTheme"
            clearable
            placeholder="请输入账单主题"
          ></el-input>
        </el-form-item>
        <el-form-item label="账单金额" prop="amount">
          <el-input
            v-model="billFrom.amount"
            clearable
            placeholder="请输入账单金额"
          ></el-input>
        </el-form-item>
        <el-form-item label="分摊方式" prop="allocationMethod">
          <el-select
            v-model="billFrom.allocationMethod"
            placeholder="请选择分摊方式"
            clearable
          >
            <!-- <el-option label="自费" value="自费"></el-option> -->
            <el-option label="AA制" value="01"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="消费时间" prop="dissipate">
          <el-date-picker
            type="datetime"
            placeholder="选择消费日期"
            v-model="billFrom.dissipate"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="消费地址" prop="consumerAddress">
          <el-input
            v-model="billFrom.consumerAddress"
            placeholder="请输入消费地址"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="consumptionNotes">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            v-model="billFrom.consumptionNotes"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="参与用户" class="billuser">
          <!-- 动态增加项目 -->
          <!-- 不止一个项目，用div包裹起来 -->
          <div
            v-for="(item, index) in billFrom.userBillAssociationReqs"
            :key="index"
            class="billItem"
          >
            <el-form-item
              :prop="'userBillAssociationReqs.' + index + '.userId'"
            >
              <!-- <el-autocomplete v-model="item.userId" :fetch-suggestions="querySearchAsync" placeholder="请输入用户名" @select="handleSelect">
              </el-autocomplete> -->
              <el-select
                v-model="item.userId"
                clearable
                filterable
                remote
                placeholder="请输入关键词"
                :remote-method="remoteMethod"
                :loading="loading"
                @change="handleSelect"
                v-loadmore="loadmore"
              >
                <el-option
                  v-for="item in userOptions"
                  :key="item.userId"
                  :label="item.userName + '  ' + item.mobileNo"
                  :value="item.userId"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              :prop="
                'userBillAssociationReqs.' + index + '.userParticipationType'
              "
            >
              <el-select
                v-model="item.userParticipationType"
                placeholder="请选择参与类型"
                clearable
              >
                <el-option label="支出者" value="01"></el-option>
                <el-option label="分摊者" value="02"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                class="el-icon-circle-plus-outline"
                @click="addItem()"
                v-if="index === 0"
              ></el-button>
              <el-button
                type="danger"
                class="el-icon-delete"
                @click="deleteItem(item, index)"
                v-else
              ></el-button>
            </el-form-item>
          </div>
        </el-form-item>
        <el-form-item class="btn">
          <el-button @click="resetForm('billFrom')">重置</el-button>
          <el-button type="primary" @click="onSubmit('billFrom')"
            >提交</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Api from "@/api";

export default {
  name: "billingAdd",
  data() {
    return {
      billFrom: {
        billTheme: "",
        amount: null,
        allocationMethod: "",
        dissipate: "",
        consumerAddress: "",
        consumptionNotes: "",
        userBillAssociationReqs: [{ userId: "", userParticipationType: "" }],
      },
      rules: {
        billTheme: [
          { required: true, message: "请输入账单主题", trigger: "blur" },
        ],
        amount: [
          { required: true, message: "请输入账单金额", trigger: "blur" },
        ],
        allocationMethod: [
          { required: true, message: "请选择分摊方式", trigger: "blur" },
        ],
        dissipate: [
          {
            required: true,
            message: "请选择消费时间",
            trigger: "change",
          },
        ],
        consumerAddress: [
          { required: true, message: "请输入消费地址", trigger: "blur" },
        ],
        userBillAssociationReqs: [
          {
            userId: [
              { required: true, message: "请输入参与用户", trigger: "blur" },
            ],
            userParticipationType: [
              { required: true, message: "请选择参与方式", trigger: "blur" },
            ],
          },
        ],
      },
      loading: false,
      userOptions: [],
      list: [],
      pageSize: 5,
      pageNo: 1,
      pramars: "",
      userData:'',
    };
  },
  methods: {
    //提交表单信息
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.billFrom.dissipate = this.billFrom.dissipate.format(
            "yyyy-MM-dd HH:mm:ss"
          );
          let billData = {
            billTheme: this.billFrom.billTheme,
            amount: this.billFrom.amount,
            allocationMethod: this.billFrom.allocationMethod,
            dissipate: this.billFrom.dissipate,
            consumerAddress: this.billFrom.consumerAddress,
            consumptionNotes: this.billFrom.consumptionNotes,
            userBillAssociationReqs: this.billFrom.userBillAssociationReqs,
            peopleNum: this.billFrom.userBillAssociationReqs.length,
            createUserId: this.userData.userId,
          };

          Api.addNewBill(billData).then((res) => {
            this.remoteMethod;
            if (res.data.code === 200) {
              this.$message({
                message: "新增成功",
                type: "success",
              });
              this.$router.push("/home");
            }
            if (!(res.data.code === 200)) {
              this.$message({
                message: res.data.message,
                type: "error",
              });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //重置注册信息
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //增加参与者
    addItem() {
      this.billFrom.userBillAssociationReqs.push({
        userId: "",
        userParticipationType: "",
      });
    },
    //删除参与者
    deleteItem(item, index) {
      this.billFrom.userBillAssociationReqs.splice(index, 1);
    },

    //搜索数据并展示
    remoteMethod(query) {
      console.log(query);
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.pramars = {
            keyWord: query,
            pageNo: this.pageNo,
            pageSize: this.pageSize,
          };
          this.getUserList(this.pramars);
          this.loading = false;
        }, 200);
      } else {
        this.options = [];
      }
    },

    //返回选择数据
    handleSelect(item) {
      console.log(item);
      this.userOptions = [];
    },

    //获取用户列表
    getUserList(pramars) {
      Api.userFound(pramars).then((res) => {
        this.userOptions = res.data.data.userInfoByNamePhone;
      });
    },

    //下划加载
    loadmore() {
      console.log("获取更多院线名称");
      this.pageSize = this.pageSize + 5;
      this.getUserList(this.pramars);
    },
  },
  mounted() {
    this.restaurants = [];
    this.userData = JSON.parse(sessionStorage.getItem("userData"));
  },
};
</script>

<style lang="scss" scoped>
.newbill {
  // background: url("../../assets/image/background.jpg") no-repeat;
  // background-size: 100% 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  .billFrom {
    margin-top: 40px;
    width: 600px;
    .el-input {
      width: 100%;
    }
    .el-textarea {
      width: 100%;
    }
    .billuser {
      text-align: center;
    }
    .billItem {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      margin-bottom: 20px;
    }
    .btn {
      text-align: center;
    }
  }
}
</style>