<template>
  <div id="lineCharts" :class="className" :style="{ width: width }" />
</template>

<script>
import * as echarts from 'echarts'
import { warnStateToday, warnNumPerHour } from "@/api/iot/deviceLog";
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    autoResize: {
      type: Boolean,
      default: true
    }
    // chartData: {
    //   type: Object,
    //   required: true
    // }
  },
  data() {
    return {
      chart: null,
      warnToday: {}
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },

    setOptions({ expectedData, actualData } = {}) {
      // 当天警告个数
      warnNumPerHour().then(res => {
        this.warnToday = res.data
        this.chart.setOption({
          title: {
            text: "智能变化",
            left: "10px",
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
              data: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"]
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
              data: this.warnToday
            },
          ]
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
#lineCharts {
  height: 300px;
}

@media (min-width:1200px) {
  #lineCharts {
    height: 600px;
  }
}
</style>
