
<template>
    <div id="app">
      <div class="text">
        <div>
          <span>搜索：</span>
          <el-select
            v-model="keywords"
            filterable
            remote
            reserve-keyword
            placeholder="请输入关键词地址"
            :remote-method="remoteMethod"
            :loading="loading"
            :clearable="true"
            size="mini"
            @change="currentSelect"
          >
            <el-option
              v-for="item in options"
              :key="item.id + item.adcode"
              :label="item.name"
              :value="item"
              class="one-text"
            >
              <span style="float: left">{{ item.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{
                item.district
              }}</span>
            </el-option>
          </el-select>
        </div>
        <div class="info-box">
          <div>经度：{{ form.lng }}</div>
          <div>纬度：{{ form.lat }}</div>
          <div>详细地址：{{ form.address }}</div>
        </div>
      </div>
      <div id="container" class="container"></div>
      
    </div>
  </template>
   
  <script>
  import AMapLoader from "@amap/amap-jsapi-loader";
  window._AMapSecurityConfig = {
    // 安全密钥
    securityJsCode: "0f5c6bb3d88fb7c9934498c22b0b7260",
  };
  export default {
    name: "TestIndex",
    data() {
      return {
        // 地图实例
        map: null,
        // 标记点
        marker: "",
        // 地址逆解析
        geoCoder: null,
        // 搜索提示
        AutoComplete: null,
        // 搜索关键字
        keywords: "",
        // 位置信息
        form: {
          lng: "",
          lat: "",
          address: "",
          adcode: "", //地区编码
        },
        // 搜索节流阀
        loading: false,
        // 搜索提示信息
        options: [],
      };
    },
    methods: {
      initMap() {
        AMapLoader.reset()
        AMapLoader.load({
          // 申请的Key
          key: "e70997f50b4c02bfc3fe258a67222adf",
          version: "2.0",
          // 需要用到的插件
          plugins: ["AMap.Geocoder", "AMap.AutoComplete"],
        })
          .then((AMap) => {
            this.map = new AMap.Map("container", {
              viewMode: "3D", //是否为3D地图模式
              zoom: 12, //初始化地图级别
              center: [104.074043, 30.551491], //初始化地图中心点位置
            });
            //地址逆解析插件
            this.geoCoder = new AMap.Geocoder({
              city: "010", //城市设为北京，默认：“全国”
              radius: 1000, //范围，默认：500
            });
            // 搜索提示插件
            this.AutoComplete = new AMap.AutoComplete({ city: "全国" });
            //点击获取经纬度;
            this.map.on("click", (e) => {
              // 获取经纬度
              this.form.lng = e.lnglat.lng;
              this.form.lat = e.lnglat.lat;
              // 清除点
              this.removeMarker();
              // 标记点
              this.setMapMarker();
            });
          })
          .catch((err) => {
            // 错误
            console.log(err);
          });
      },
      // 标记点
      setMapMarker() {
        // 自动适应显示想显示的范围区域
        this.map.setFitView();
        this.marker = new AMap.Marker({
          map: this.map,
          position: [this.form.lng, this.form.lat],
        });
        this.toGeoCoder();
        this.map.setFitView();
        this.map.add(this.marker);
      },
      // 清除点
      removeMarker() {
        if (this.marker) {
          this.map.remove(this.marker);
        }
      },
      // 逆解析地址
      toGeoCoder() {
        let lnglat = [this.form.lng, this.form.lat];
        this.geoCoder.getAddress(lnglat, (status, result) => {
          if (status === "complete" && result.regeocode) {
            this.form.address = result.regeocode.formattedAddress;
          }
        });
      },
    },
    mounted() {
      this.initMap();
    },
  };
  </script>
   
  <style>
  #app {
    position: relative;
    width: 100%;
    height: 100%;
  }
  .text {
    font-size: 15px;
    position: absolute;
    left: 20px;
    top: 20px;
    z-index: 100;
    .el-input {
      width: 250px;
    }
  }
  .container {
    width: 100%;
    height: 100vh;
  }
  @media (min-width:1000px) {
    .text {
      font-size: 18px;
      position: absolute;
      .el-input {
        width: 400px;
      }
    }
  }
  
  </style>