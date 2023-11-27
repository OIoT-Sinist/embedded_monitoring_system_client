<template>
  <div id="app">
    <!-- 顶部数据 -->
    <div class="top">
      <div class="box totalOrder">
        <i class="iconfont el-icon-warning-outline"></i>
        <div class="text_info">
          <p>当前警告个数</p>
          <p>1235</p>
        </div>
      </div>

      <div class="box totalPrice">
        <i class="iconfont el-icon-notebook-2"></i>
        <div class="text_info">
          <p>总日志条数</p>
          <p>224</p>
        </div>
      </div>

      <div class="box order">
        <i class="iconfont el-icon-s-data"></i>
        <div class="text_info">
          <p>用户可见设备数量</p>
          <p>32131</p>
        </div>
      </div>

      <div class="box prive">
        <i class="iconfont el-icon-warning"></i>
        <div class="text_info">
          <p>当天可见设备警告数</p>
          <p>4231</p>
        </div>
      </div>
    </div>

    <!-- 折线图 -->
    <div id="line"></div>

    <!-- 底部数据 -->
    <div class="bottom">
      <el-card class="left" style="font-weight: 700;" header="日志面板">
        <el-tabs v-model="activeName" :stretch="true" @tab-click="handleClick">
          <!-- 1 -->
          <el-tab-pane label="用户管理" name="first">
            <el-table :data="tableData" height="100%" style="width: 100%">
              <el-table-column label="序号" width="90" type="index" :index="indexMethod">
              </el-table-column>
              <el-table-column prop="date" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="address" label="地址">
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <!-- 2 -->
          <el-tab-pane label="配置管理" name="second">
            <el-table :data="tableData" height="100%" style="width: 100%">
              <el-table-column label="序号" width="90" type="index" :index="indexMethod">
              </el-table-column>
              <el-table-column prop="date" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="address" label="地址">
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <!-- 3 -->
          <el-tab-pane label="角色管理" name="third">
            <el-table :data="tableData" height="100%" style="width: 100%">
              <el-table-column label="序号" width="90" type="index" :index="indexMethod">
              </el-table-column>
              <el-table-column prop="date" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="address" label="地址">
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-card>
      <!-- 饼图 -->
      <div class="el-card is-always-shadow pieCharts">
        <div style="font-weight: 700;" class="el-card__header">设备状态</div>
        <div id="pie"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  data() {
    return {
      lineCharts: '',
      pieCharts: '',
      activeName: 'first',
      tableData: [{
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-08',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-06',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-07',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }]
    };
  },
  methods: {
    // 表格, 点击tab触发
    handleClick(tab, event) {
      console.log(tab, event);
    },
    // 排序
    indexMethod(index) {
      return index + 1;
    },
    // 折线图
    line() {
      this.lineCharts = echarts.init(document.getElementById("line"));
      this.lineCharts.setOption({
        title: {
          text: "智能变化",
          top: "15px",
          left: "15px"
        },
        backgroundColor: "#fff",
        color: ["#ffc20e", "#00ae9d"],
        grid: {
          top: "15%",
          left: "1%",
          right: "5%",
          bottom: "5%",
          containLabel: true
        },
        tooltip: {
          trigger: "axis",
          backgroundColor: "rgba(61, 85, 102, 0.2)",
          borderWidth: 1,
          borderColor: "#9DBAE1", // 边框颜色
          // 选中线颜色
          axisPointer: {
            lineStyle: {
              color: "#9DBAE1"
            }
          },
          // 字体颜色
          textStyle: {
            color: "#fff",
            fontSize: 10
          }
        },
        legend: {
          right: "3%",
          icon: "circle",
          itemWidth: 8,
          itemGap: 20,
          textStyle: {
            padding: [0, 0, 0, 5]
          }
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLabel: {
              color: "#33a3dc",
              fontSize: 14
            },
            axisLine: {
              lineStyle: {
                color: "rgba(255,255,255,.1)"
              }
            },
            data: ["4月", "5月", "6月", "7月", "8月", "9月", "10月"]
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "",
            axisLabel: {
              color: "#33a3dc",
              fontSize: 14
            },
            splitLine: {
              lineStyle: {
                type: "solid",
                color: "rgba(255,255,255,.1)",
                width: 1
              }
            }
          }
        ],
        series: [
          {
            name: "",
            type: "line",
            smooth: true, // 是否平滑曲线显示
            // symbol:'circle',  // 默认是空心圆（中间是白色的），改成实心圆
            symbolSize: 0,
            lineStyle: {
              normal: {
                color: "#ffc20e" // 线条颜色
              }
            },
            areaStyle: {
              // 区域填充样式
              normal: {
                // 线性渐变，前4个参数分别是x0,y0,x2,y2(范围0~1);相当于图形包围盒中的百分比。如果最后一个参数是‘true’，则该四个值是绝对像素位置。
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,
                  [
                    { offset: 0, color: "rgba(255,194,14, 0.8)" },
                    { offset: 1, color: "rgba(255,194,14, 0)" }
                  ],
                  false
                )
                // shadowColor: "rgba(53,142,215, 0.9)", // 阴影颜色
                // shadowBlur: 20 // shadowBlur设图形阴影的模糊大小。配合shadowColor,shadowOffsetX/Y, 设置图形的阴影效果。
              }
            },
            data: [20, 58, 19, 56, 86, 35, 25]
          },
          {
            name: "",
            type: "line",
            smooth: true, // 是否平滑曲线显示
            // symbol:'circle',  // 默认是空心圆（中间是白色的），改成实心圆
            symbolSize: 0,
            lineStyle: {
              normal: {
                color: "#00ae9d" // 线条颜色
              }
            },
            areaStyle: {
              // 区域填充样式
              normal: {
                // 线性渐变，前4个参数分别是x0,y0,x2,y2(范围0~1);相当于图形包围盒中的百分比。如果最后一个参数是‘true’，则该四个值是绝对像素位置。
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,
                  [
                    { offset: 0, color: "rgba(0,174,157, 0.8)" },
                    { offset: 1, color: "rgba(0,174,157, 0)" }
                  ],
                  false
                )
                // shadowColor: "rgba(53,142,215, 0.9)", // 阴影颜色
                // shadowBlur: 20 // shadowBlur设图形阴影的模糊大小。配合shadowColor,shadowOffsetX/Y, 设置图形的阴影效果。
              }
            },
            data: [40, 29, 38, 28, 43, 45, 62]
          }
        ]
      })
      // 动态处理图表的尺寸
      window.addEventListener("resize", this.lineCharts.resize)
    },
    // 饼图
    pie() {
      this.pieCharts = echarts.init(document.getElementById("pie"));
      this.pieCharts.setOption({

        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: 'Search' },
              { value: 735, name: 'Direct' },
              { value: 580, name: 'Email' },
              { value: 484, name: 'Union' },
              { value: 300, name: 'Video' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      })
      //动态处理图表的尺寸
      window.addEventListener("resize", this.pieCharts.resize)
    },
  },
  mounted() {
    // 折线图
    this.line();
    // 饼图
    this.pie();
  },
  destroyed() {
    window.removeEventListener("resize", () => {
      this.lineCharts.resize()
      this.pieCharts.resize()
    });
  }
};
</script>

<style lang="scss" scoped>
#app {
  background-color: #fff;
}
.top {
  // 移动端适配
  display: grid;
  grid-template-columns: 48% 48%;
  grid-template-rows: 87px 87px;
  justify-content: space-around;
  .prive,.order {
    margin-top: 5px;
  }
  .box {
    background-color: #ccc;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
  
    .iconfont {
      font-size: 50px;
      color: #3aa8e1;
    }

    p {
      font-size: 14px;
    }
  }
}

#line {
  width: 100%;
  margin: 20px 0 20px 20px;
  height: 300px;
}

.bottom {
  display: flex;
  width: 100%;
  flex-wrap: wrap;

  .left {
    width: 100%;
    margin: 10px 10px 0 10px;
  }

  .pieCharts {
    margin: 10px;
    width: 100%;
    height: 300px;

    #pie {
      margin-left: 5%;
      height: 100%;
    }
  }

  ::v-deep .el-card__header {
    border-bottom: none;
  }
}

@media (min-width:1200px) {
  .top {
    display: flex;
    justify-content: space-around;
    margin-top: 30px;
    .prive,.order {
      margin-top: 0px;
    }
    .box {
      width: 250px;
      height: 100px;
      display: flex;
      justify-content: space-around;
      align-items: center;
      text-align: center;
      p {
        font-size: 20px;
      }
    }
    
  }

  #line {
    width: 100%;
    margin: 20px 0;
    height: 600px;
  }

  .bottom {
    display: flex;
    justify-content: space-between;
    width: 100%;
    height: 500px;

    .left {
      margin: 0 20px 10px 20px;
      flex: 1;
    }

    .pieCharts {
      margin: 0 20px 10px 0;
      flex: 1;
      height: 100%;
      #pie {
        margin-left: 0;
      }
    }
  }
}
</style>