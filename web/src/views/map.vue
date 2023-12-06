<template>
  <div>
    <div id="container"></div>
  </div>
</template>
<script>
import AMapLoader from "@amap/amap-jsapi-loader";
import { listDeviceLog } from "@/api/iot/deviceLog"

import { reverse, uniqBy } from "lodash";// npm i lodash --save 操作数组插件 https://lodash.shujuwajue.com/array/reverse

window._AMapSecurityConfig = {
  // 安全密钥
  securityJsCode: "0f5c6bb3d88fb7c9934498c22b0b7260",
};
export default {
  data() {
    return {
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
      }
    }
  },
  methods: {
    map() {
      // 跳转
      let jump = this.$router

      AMapLoader.reset()
      AMapLoader.load({
        key: "e70997f50b4c02bfc3fe258a67222adf", //申请好的Web端开发者 Key，调用 load 时必填
        version: "2.0", //指定要加载的 JS API 的版本，缺省时默认为 1.4.15
        // 需要用到的插件
        plugins: ["AMap.Geocoder", "AMap.AutoComplete"],
      })
        .then((AMap) => {
          const map = new AMap.Map("container", {
            viewMode: "3D", //是否为3D地图模式
            mapStyle: 'amap://styles/whitesmoke',
            zoom: 6, //初始化地图级别
            center: [104.074043, 30.551491], //初始化地图中心点位置
          });

          // 正常的图标
          const icon1 = {
            type: 'image', // 图标类型，现阶段只支持 image 类型
            image: 'https://webapi.amap.com/images/mass/mass1.png', // 图片 url
            size: [40, 40],// 图片尺寸
            anchor: 'center' // 图片相对 position 的锚点，默认为 bottom-center 
          };
          // 异常的图标
          const icon2 = {
            type: 'image', // 图标类型，现阶段只支持 image 类型
            image: 'https://webapi.amap.com/images/mass/mass0.png', // 图片 url
            size: [40, 40],// 图片尺寸
            anchor: 'center' // 图片相对 position 的锚点，默认为 bottom-center 
          };
          listDeviceLog().then(res => {
            if (res.rows) {
              // JSON数组倒序
              let arr = reverse(res.rows);
              // JSON数组去重
              arr = uniqBy(res.rows, 'deviceName');

              arr.forEach((v) => {

                const normal = {
                  content: v.deviceName, // 要展示的文字内容
                  direction: 'right', // 文字方向，有 icon 时为围绕文字的方向，没有 icon 时，则为相对 position 的位置
                  offset: [0, -3], // 在 direction 基础上的偏移量
                  style: { // 文字样式       
                    fontSize: 14,// 字体大小        
                    fillColor: '#22886f', // 字体颜色
                    strokeColor: '#fff', // 描边颜色
                    strokeWidth: 2,  // 描边宽度
                  }
                }
                // 标点
                let labelsMarker = new AMap.LabelMarker({
                  name: '标注', // 此属性非绘制文字内容，仅最为标识使用
                  position: [v.locationLatitude, v.locationLogitude],
                  zIndex: 16,
                  icon: v.hasError == 0 ? icon1 : icon2, // 将第一步创建的 icon 对象传给 icon 属性
                  text: normal,// 将第二步创建的 text 对象传给 text 属性
                });
                const labelsLayer = new AMap.LabelsLayer({
                  zooms: [3, 20],
                  zIndex: 1000,
                  collision: true,  // 该层内标注是否避让
                  allowCollision: true, // 设置 allowCollision：true，可以让标注避让用户的标注
                });

                // 添加labelMarker数组
                labelsLayer.add(labelsMarker)
                map.add(labelsLayer);
                // 点击跳转到设备配置
                labelsMarker.on("click", function (e) {
                  jump.push('/device/deviceConfig')
                });
              })
            }
          });

        })
        .catch((e) => {
          console.error(e); //加载错误提示
        });
    },
  },
  mounted() {
    this.map()
  }
}
</script>

<style lang="scss" scoped>
#container {
  width: 100vw;
  height: 100vh;
}
</style>