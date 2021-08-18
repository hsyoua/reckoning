<template>
  <div class="bill">
    <div class="billHeader">
      <h2>我的账单</h2>
    </div>
    
    <div class="billTable">
      <el-table
        :data="billTable"
        style="width: 100%"
        @row-dblclick="dbSelectedBill"
        v-loading="loading"
      >
        <el-table-column label="账单ID" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.billingId }}</span>
          </template>
        </el-table-column>
        <el-table-column label="账单主题" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.billTheme }}</span>
          </template>
        </el-table-column>
        <el-table-column label="金额" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.amount }}</span>
          </template>
        </el-table-column>
        <el-table-column label="人数" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.peopleNum }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.billingStatus }}</span>
          </template>
        </el-table-column>
        <el-table-column label="时间" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.dissipate }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="billPage">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNo"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
    <div class="billMessage">
      <el-dialog title="账单详情" :visible.sync="dialog" width="60%" center>
        <div v-loading="loading">
          <el-row>
            <el-col :span="4"><span>账单ID</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.billingId"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>账单主题</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.billTheme"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>金额</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.amount"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>人数</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.peopleNum"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>状态</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.billingStatus"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>分摊方式</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.allocationMethod"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>消费备注</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.consumptionNotes"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>消费地址</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.consumerAddress"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>创建用户</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.createUserName"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-row>
            <el-col :span="4"><span>消费时间</span></el-col>
            <el-col :span="20"
              ><el-input
                v-model="billMessage.dissipate"
                :disabled="disabled"
              ></el-input
            ></el-col>
          </el-row>
          <el-table :data="billMessage.conSumeList" style="width: 100%">
            <el-table-column label="参与用户" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.userName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="参与类型" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.userParticipationType }}</span>
              </template>
            </el-table-column>
            <el-table-column label="分摊金额" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.apportionedAmount }}</span>
              </template>
            </el-table-column>
            <el-table-column label="支付状态" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.paymentStatus }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import Api from "@/api";

export default {
  data() {
    return {
      billTable: [],
      billForm: {
        data1: "",
        data2: "",
        userType: "",
        billzhuti: "",
        billingId: "",
      },
      billMessage: {},
      pageSize: 10,
      total: 10,
      pageNo: 1,
      dialog: false,
      disabled: true,
      loading: false,
    };
  },
  created() {
    this.getQueryBilling();
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    //每页多少条数据
    handleSizeChange(val) {
      this.pageSize = val;
      this.getQueryBilling();
    },
    //选择第n页
    handleCurrentChange(val) {
      this.pageNo = val;
      this.getQueryBilling();
    },
    //展开或收回搜索
    open() {
      if (this.formHeight == "60px") {
        this.formHeight = "auto";
        this.openButton = "收回";
      } else {
        this.formHeight = "60px";
        this.openButton = "展开";
      }
    },
    //双击打开账单详情
    dbSelectedBill(row) {
      this.loading = true;
      this.dialog = true;
      let data = {
        userId: JSON.parse(sessionStorage.getItem("userData")).userId,
        billingId: row.billingId,
      };
      console.log(data);
      Api.fingBillDetail(data).then((res) => {
        console.log("res", res.data);
        if (res.data.code === 200) {
          let data = res.data.data;
          //分摊方式：01-AA
          if (data.allocationMethod === "01") {
            data.allocationMethod = "AA制";
          }
          //账单状态：01-待结算、02-结算中、03-结算完成（账单关闭）
          if (data.billingStatus === "01") {
            data.billingStatus = "待结算";
          } else if (data.billingStatus === "02") {
            data.billingStatus = "结算中";
          } else if (data.billingStatus === "03") {
            data.billingStatus = "结算完成（账单关闭）";
          }
          //用户参与类型：01-支出者、02-分摊者
          data.conSumeList.forEach((item) => {
            if (item.userParticipationType === "01") {
              item.userParticipationType = "支出者";
            } else if (item.userParticipationType === "02") {
              item.userParticipationType = "分摊者";
            }
          });
          //支付状态：00-无需支付、01-待支付、02-已支付
          data.conSumeList.forEach((item) => {
            if (item.paymentStatus === "00") {
              item.paymentStatus = "无需支付";
            } else if (item.paymentStatus === "01") {
              item.paymentStatus = "待支付";
            } else if (item.paymentStatus === "02") {
              item.paymentStatus = "已支付";
            }
          });
          console.log("data", data);
          this.billMessage = data;
          this.loading = false;
        }
      });
    },
    //获取用户账单信息   01-待结算、02-结算中、03-结算完成（账单关闭）
    getQueryBilling() {
      this.loading = true;
      let parmas = {
        pageSize: this.pageSize,
        pageNo: this.pageNo,
        userId: JSON.parse(sessionStorage.getItem("userData")).userId,
      };
      Api.queryBilling(parmas).then((res) => {
        let data = res.data.userBillingInfo;
        //账单状态
        data.forEach((item) => {
          if (item.billingStatus === "01") {
            item.billingStatus = "待结算";
          } else if (item.billingStatus === "02") {
            item.billingStatus = "结算中";
          } else if (item.billingStatus === "03") {
            item.billingStatus = "结算完成（账单关闭）";
          }
        });
        this.billTable = data;
        this.total = res.data.totals;
        this.loading = false;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.bill {
  height: 100%;
  .billHeader {
    text-align: center;
    h2 {
      margin: 0;
      padding: 20px 0;
    }
  }
  .billPage {
    text-align: center;
  }
  .billMessage {
    .el-row {
      line-height: 40px;
      font-size: 16px;
    }
    ::v-deep .el-input__inner {
      border: none;
      background: none;
      color: #606266;
      font-size: 16px;
      cursor: default;
    }
  }
}
</style>