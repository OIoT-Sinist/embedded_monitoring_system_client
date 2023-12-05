<template>
  <div :class="className" :style="{ width: width }" />
</template>

<script>
import * as echarts from 'echarts'
import { warnState } from "@/api/iot/deviceLog";
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
    }
  },
  data() {
    return {
      chart: null,
      warnNumber: 0,
      noWarnNumber: 0
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
    // 当前警告个数
      warnState().then(res => {
        this.warnNumber = res.data.ExceedsTheThreshold
        this.noWarnNumber = res.data.ThresholdNotExceeded
        this.chart.setOption({
          tooltip: {
            trigger: 'item'
          },
          color: ['#ee6666', '#91cb74'],
          series: [
            {
              name: '设备状态',
              type: 'pie',
              radius: '50%',
              data: [
                { value: this.warnNumber, name: '当前警告状态设备' },
                { value: this.noWarnNumber, name: '当前非警告状态设备' },
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            },
          ],
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped></style>
