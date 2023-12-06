<template>
  <div id="app">
    <!-- 顶部数据 -->
    <div class="top">
      <div class="box totalOrder">
        <i class="iconfont el-icon-warning-outline" style="color: #d41111;"></i>
        <div class="text_info">
          <p>当前警告个数</p>
          <p>{{ warn.warnNumber }}</p>
        </div>
      </div>

      <div class="box totalPrice">
        <i class="iconfont el-icon-notebook-2" style="color: #e6641b;"></i>
        <div class="text_info">
          <p>总日志条数</p>
          <p>{{ logTotal }}</p>
        </div>
      </div>

      <div class="box order">
        <i class="iconfont el-icon-s-data" style="color: #d334ed;"></i>
        <div class="text_info">
          <p>用户可见设备数量</p>
          <p>{{ equipmentTotal }}</p>
        </div>
      </div>

      <div class="box prive">
        <i class="iconfont el-icon-warning" style="color: #0ac0b5;"></i>
        <div class="text_info">
          <p>当天可见设备警告个数</p>
          <p>{{ warnToday }}</p>
        </div>
      </div>
    </div>

    <!-- 折线图 -->
    <LineChart></LineChart>

    <!-- 底部数据 -->
    <div class="bottom">
      <el-card class="left" style="font-weight: 700;" header="日志面板">
        <el-tabs v-model="activeName" :stretch="true" @tab-click="handleClick">
          <!-- 1 -->
          <el-tab-pane label="设备日志" name="first">
            <el-table :data="deviceLogList" max-height="400px" style="width: 100%;">
              <el-table-column label="id" align="center" prop="id" v-if="true" />
              <el-table-column label="设备名" align="center" prop="deviceName" />
              <el-table-column label="co2" align="center" prop="co2" />
              <el-table-column label="亮度" align="center" prop="luminance" />
              <el-table-column label="湿度" align="center" prop="humidity" />
              <el-table-column label="温度" align="center" prop="temprature" />
              <el-table-column label="位置纬度" align="center" prop="locationLatitude" />
              <el-table-column label="位置经度" align="center" prop="locationLogitude" />
              <el-table-column label="是否超出阈值" align="center" prop="hasError" />
              <el-table-column label="自定义数据" align="center" prop="customData" />
            </el-table>
          </el-tab-pane>
          <!-- 2 -->
          <el-tab-pane label="设备配置" name="second">
            <el-table :data="deviceConfigList" max-height="400px" style="width: 100%;">
              <el-table-column label="id" align="center" prop="id" v-if="true" />
              <el-table-column label="位置纬度" align="center" prop="locationLatitude" />
              <el-table-column label="位置经度" align="center" prop="locationLogitude" />
              <el-table-column label="co2范围" align="center" prop="co2Range" />
              <el-table-column label="亮度范围" align="center" prop="luminanceRange" />
              <el-table-column label="湿度范围" align="center" prop="humidityRange" />
              <el-table-column label="温度范围" align="center" prop="tempratureRange" />
            </el-table>
          </el-tab-pane>
          <!-- 3 -->
          <el-tab-pane label="物联网设备数据传输" name="third">
            <el-table :data="deviceTransDataList" max-height="400px" style="width: 100%;">
              <el-table-column label="id" align="center" prop="id" v-if="true" />
              <el-table-column label="co2" align="center" prop="co2" />
              <el-table-column label="亮度" align="center" prop="luminance" />
              <el-table-column label="湿度" align="center" prop="humidity" />
              <el-table-column label="温度" align="center" prop="temprature" />
              <el-table-column label="自定义数据" align="center" prop="customData" />
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-card>
      <!-- 饼图 -->
      <div class="el-card is-always-shadow pieCharts">
        <div style="font-weight: 700;" class="el-card__header">设备状态</div>
        <PieChart :warn="warn" id="pie"></PieChart>
      </div>
    </div>
  </div>
</template>

<script>
import LineChart from "./dashboard/LineChart.vue"
import PieChart from "./dashboard/PieChart.vue"
import { listDeviceLog, warnState, warnStateToday } from "@/api/iot/deviceLog";
import { listDeviceConfig } from "@/api/iot/deviceConfig";
import { listDeviceTransData } from "@/api/iot/deviceTransData";
export default {
  components: {
    LineChart,
    PieChart
  },
  data() {
    return {
      warn: {
        // 当前警告个数
        warnNumber: 0,
        // 当前非警告个数
        noWarnNumber: 0,
      },
      // 当天警告个数
      warnToday: 0,
      // 设备日志
      deviceLogList: [],
      // 日志条数
      logTotal: 0,
      lineCharts: '',
      pieCharts: '',
      activeName: 'first',
      // 设备配置
      deviceConfigList: [],
      // 设备数量
      equipmentTotal: 0,
      // 物联网设备数据传输表格数据
      deviceTransDataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: undefined,
        locationLatitude: undefined,
        locationLogitude: undefined,
        co2Range: undefined,
        alarmThreshold: undefined,
        luminanceRange: undefined,
        humidityRange: undefined,
        tempratureRange: undefined,
      },
    };
  },
  created() {
    this.getlogList();
    this.getEquipmentList();
    this.getTransferList();
    this.warnStates();
    this.warnStatesToday();
  },
  methods: {
    /** 查询设备配置列表 */
    getEquipmentList() {
      listDeviceConfig(this.queryParams).then(response => {
        this.deviceConfigList = response.rows;
        this.equipmentTotal = response.total;
      });
    },
    // 当前警告个数
    warnStates() {
      warnState().then(res => {
        this.warn.warnNumber = res.data.ExceedsTheThreshold
        this.warn.noWarnNumber = res.data.ThresholdNotExceeded
      })
    },
    // 当天警告个数
    warnStatesToday() {
      warnStateToday().then(res => {
        this.warnToday = res.data.NumberOfAlarmsDate
      })
    },

    /** 查询设备日志列表 */
    getlogList() {
      listDeviceLog(this.queryParams).then(response => {
        this.deviceLogList = response.rows;
        this.logTotal = response.total;
      });
    },
    /** 查询物联网设备数据传输列表 */
    getTransferList() {
      listDeviceTransData(this.queryParams).then(response => {
        this.deviceTransDataList = response.rows;
      });
    },
    // 表格, 点击tab触发
    handleClick(tab, event) {
    },
    // 排序
    indexMethod(index) {
      return index + 1;
    },
  }
};
</script>

<style lang="scss" scoped>
#app {
  background-color: #fff;
}

.top {
  // 移动端适配
  margin: 10px 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;

  .prive,
  .order {
    margin-top: 10px;
  }

  .box {
    width: 45%;
    height: 45%;
    background-color: #fff;
    border-radius: 10px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    text-align: center;

    .iconfont {
      font-size: 30px;
    }

    p {
      font-size: 14px;
    }
  }
}

// #line {
//   height: 300px;
//   margin: 0 10px;
// }
.bottom {
  width: 100%;

  .left {
    margin: 10px 10px 0 10px;
  }

  .pieCharts {
    margin: 10px;

    #pie {
      height: 300px;
    }
  }

  ::v-deep .el-card__header {
    border-bottom: none;
  }
}

@media (min-width:1200px) {
  .top {
    display: flex;
    margin-top: 30px;

    .prive,
    .order {
      margin-top: 0px;
    }

    .box {
      width: 250px;
      height: 100px;
      display: flex;
      justify-content: space-around;
      align-items: center;
      text-align: center;

      .iconfont {
        font-size: 45px;
      }

      p {
        font-size: 20px;
      }
    }

  }

  .bottom {
    margin: 5px;
    display: flex;
    justify-content: space-between;
    width: 100%;
    height: 500px;

    .left {
      width: 49%;
      height: 100%;
      margin: 0 10px;

    }

    // 饼图
    .pieCharts {
      height: 100%;
      width: 49%;
      margin: 0 10px;

      #pie {
        height: 100%;
      }
    }
  }
}

::v-deep .is-scrolling-none {
  height: 100% !important;
}
</style>