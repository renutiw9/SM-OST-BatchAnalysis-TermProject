$(function() {
  echart_1();
  echart_3();
  echart_4();

  echart_map();
  echart_5();
  echart_6();
  $(".wrapper").liMarquee({
    direction: "up", //身上滚动
    //runshort: false,//内容不足时不滚动
    scrollamount: 20 //速度
  });
  //echart_1  按地区客车流量对比
  function echart_1() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById("chart_1"));

    var data = null ;
    console.log(data) ;
    $.get('http://localhost:8088/getPart1', function (res) {
      option = {
        backgroundColor: "transparent",
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        color: ["#0035f9", "#f36f8a", "#ffff43", "#25f3e6"],
        legend: {
          //图例组件，颜色和名字
          left: "65%",
          top: "95",
          orient: "vertical",
          itemGap: 12, //图例每项之间的间隔
          itemWidth: 16,
          itemHeight: 12,

          icon: "rect",
          data: ["PC","H5","android","IOS"],
          textStyle: {
            color: [],
            fontStyle: "normal",
            fontFamily: "微软雅黑",
            fontSize: 12
          }
        },
        series: [
          {
            name: "设备类型",
            type: "pie",
            clockwise: false, //饼图的扇区是否是顺时针排布
            minAngle: 20, //最小的扇区角度（0 ~ 360）
            center: ["35%", "50%"], //饼图的中心（圆心）坐标
            radius: [50, 80], //饼图的半径
            avoidLabelOverlap: true, ////是否启用防止标签重叠
            itemStyle: {
              //图形样式
              normal: {
                borderColor: "#1e2239",
                borderWidth: 1.5
              }
            },

            label: {
              //标签的位置
              normal: {
                show: false,
                position: "inside", //标签的位置
                formatter: "{d}%",
                textStyle: {
                  color: "#fff"
                }
              },
              emphasis: {
                show: true,
                textStyle: {
                  fontWeight: "bold"
                }
              }
            },
            data: res
          },
          {
            name: "",
            type: "pie",
            clockwise: false,
            silent: true,
            minAngle: 20, //最小的扇区角度（0 ~ 360）
            center: ["35%", "50%"], //饼图的中心（圆心）坐标
            radius: [0, 45], //饼图的半径
            itemStyle: {
              //图形样式
              normal: {
                borderColor: "#1e2239",
                borderWidth: 1.5,
                opacity: 0.21
              }
            },
            label: {
              //标签的位置
              normal: {
                show: false
              }
            },
            data: res
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    });

    window.addEventListener("resize", function() {
      myChart.resize();
    });
  }

  // echart_map中国地图
  function echart_map() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById("chart_map"));
    $.get('http://localhost:8088/getPart2', function (res) {
      var data = res ;
    var geoCoordMap = {
      南通: [121.05, 32.08],
      上海: [121.48, 31.22],
      南京: [118.78, 32.04],
      北京: [116.46, 39.92],
      苏州: [120.62, 31.32],
      天津: [117.2, 39.13],
      广州: [113.23, 23.16],
      深圳: [114.07, 22.62],
      杭州: [120.19, 30.26],
      郑州: [113.65, 34.76],
      西安: [108.95, 34.27],
      重庆: [106.54, 29.59],
      成都: [104.06, 30.67]
    };
    var convertData = function(data) {
      var res = [];
      for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
          res.push({
            name: data[i].name,
            value: geoCoord.concat(data[i].value)
            //value: data[i].value
          });
        }
      }
      return res;
    };

    option = {
      tooltip: {
        trigger: "item"
      },

      geo: {
        map: "china",
        label: {
          emphasis: {
            show: false
          }
        },
        roam: true,
        itemStyle: {
          normal: {
            areaColor: "#3eabff",
            borderColor: "#fff"
          },
          emphasis: {
            areaColor: "#006be4"
          }
        }
      },
      series: [
        {
          name: "实时访问",
          type: "scatter",
          coordinateSystem: "geo",
          data: convertData(data),
          symbolSize: function(val) {
            return val[2] / 10;
          },
          label: {
            normal: {
              formatter: "{b}",
              position: "right",
              show: false
            },
            emphasis: {
              show: true
            }
          },
          itemStyle: {
            normal: {
              color: "#fff"
            }
          }
        },
        {
          name: "Top 5",
          type: "effectScatter",
          coordinateSystem: "geo",
          data: convertData(
            data
              .sort(function(a, b) {
                return b.value - a.value;
              })
              .slice(0, 6)
          ),
          symbolSize: function(val) {
            return val[2] / 20;
          },
          showEffectOn: "render",
          rippleEffect: {
            brushType: "stroke"
          },
          hoverAnimation: true,
          label: {
            normal: {
              formatter: "{b}",
              position: "right",
              show: true
            }
          },
          itemStyle: {
            normal: {
              color: "#0041d2",
              shadowBlur: 10,
              shadowColor: "rgba(0,0,0,.3)"
            }
          },
          zlevel: 1
        }
      ]
    };
    myChart.setOption(option);
    });
    window.addEventListener("resize", function() {
      myChart.resize();
    });
  }

  function echart_3() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById("chart_3"));

    $.get('http://localhost:8088/getPart3', function (res) {
      var data = res;

      option = {
        backgroundColor: "transparent",
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        color: ["#0035f9", "#f36f8a", "#ffff43", "#25f3e6"],
        legend: {
          //图例组件，颜色和名字
          left: "65%",
          top: "95",
          orient: "vertical",
          itemGap: 12, //图例每项之间的间隔
          itemWidth: 16,
          itemHeight: 12,

          icon: "rect",
          data: res,
          textStyle: {
            color: [],
            fontStyle: "normal",
            fontFamily: "微软雅黑",
            fontSize: 12
          }
        },
        series: [
          {
            name: "浏览排名",
            type: "pie",
            clockwise: false, //饼图的扇区是否是顺时针排布
            minAngle: 20, //最小的扇区角度（0 ~ 360）
            center: ["35%", "50%"], //饼图的中心（圆心）坐标
            radius: [50, 80], //饼图的半径
            avoidLabelOverlap: true, ////是否启用防止标签重叠
            itemStyle: {
              //图形样式
              normal: {
                borderColor: "#1e2239",
                borderWidth: 1.5
              }
            },

            label: {
              //标签的位置
              normal: {
                show: false,
                position: "inside", //标签的位置
                formatter: "{d}%",
                textStyle: {
                  color: "#fff"
                }
              },
              emphasis: {
                show: true,
                textStyle: {
                  fontWeight: "bold"
                }
              }
            },
            data: data
          },
          {
            name: "",
            type: "pie",
            clockwise: false,
            silent: true,
            minAngle: 20, //最小的扇区角度（0 ~ 360）
            center: ["35%", "50%"], //饼图的中心（圆心）坐标
            radius: [0, 45], //饼图的半径
            itemStyle: {
              //图形样式
              normal: {
                borderColor: "#1e2239",
                borderWidth: 1.5,
                opacity: 0.21
              }
            },
            label: {
              //标签的位置
              normal: {
                show: false
              }
            },
            data: data
          }
        ]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    });

    window.addEventListener("resize", function() {
      myChart.resize();
    });
  }

  function echart_4() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById("chart_4"));
    var color = ["#e9df3d", "#f79c19", "#21fcd6", "#08c8ff", "#df4131"];
    $.get('http://localhost:8088/getPart5', function (res) {
      var data = res;
      var max = data[0].value;
      data.forEach(function(d) {
        max = d.value > max ? d.value : max;
      });

      var renderData = [
        {
          value: [],
          name: "浏览站点TOP5",
          symbol: "none",
          lineStyle: {
            normal: {
              color: "#ecc03e",
              width: 2
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  1,
                  0,
                  [
                    {
                      offset: 0,
                      color: "rgba(203, 158, 24, 0.8)"
                    },
                    {
                      offset: 1,
                      color: "rgba(190, 96, 20, 0.8)"
                    }
                  ],
                  false
              )
            }
          }
        }
      ];

      data.forEach(function(d, i) {
        var value = ["", "", "", "", ""];
        (value[i] = max), (renderData[0].value[i] = d.value);
        renderData.push({
          value: value,
          symbol: "circle",
          symbolSize: 12,
          lineStyle: {
            normal: {
              color: "transparent"
            }
          },
          itemStyle: {
            normal: {
              color: color[i]
            }
          }
        });
      });
      var indicator = [];

      data.forEach(function(d) {
        indicator.push({
          name: d.name,
          max: max,
          color: "#fff"
        });
      });
      var option = {
        tooltip: {
          show: true,
          trigger: "item"
        },
        radar: {
          center: ["50%", "50%"], //偏移位置
          radius: "80%",
          startAngle: 40, // 起始角度
          splitNumber: 4,
          shape: "circle",
          splitArea: {
            areaStyle: {
              color: "transparent"
            }
          },
          axisLabel: {
            show: false,
            fontSize: 20,
            color: "#000",
            fontStyle: "normal",
            fontWeight: "normal"
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "rgba(255, 255, 255, 0.5)"
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "rgba(255, 255, 255, 0.5)"
            }
          },
          indicator: indicator
        },
        series: [
          {
            type: "radar",
            data: renderData
          }
        ]
      };
      myChart.setOption(option);
    });

  }

  function echart_5() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById("chart_2"));
    $.get('http://localhost:8088/getPart4', function (res) {
      option = {

        xAxis: {
          type: 'category',
          data: res.key,
          axisLine: {
            lineStyle: {
              color: '#fefdff',
              width: 1, //这里是为了突出显示加上的
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: '#fefdff',
              width: 1, //这里是为了突出显示加上的
            }
          }
        },
        series: [{
          data: res.val,
          type: 'line'
        }]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    });

    window.addEventListener("resize", function() {
      myChart.resize();
    });
  }
  function echart_6() {
    var myChart = echarts.init(document.getElementById("chart_6"));

    option = {
      tooltip: {
        trigger: "axis",
        axisPointer: {
          lineStyle: {
            color: "#57617B"
          }
        },
        formatter: "{b}:<br/> 完成率{c}%"
      },

      grid: {
        left: "0",
        right: "20",
        top: "10",
        bottom: "20",
        containLabel: true
      },
      xAxis: [
        {
          type: "category",
          boundaryGap: false,
          axisLabel: {
            show: true,
            textStyle: {
              color: "rgba(255,255,255,.6)"
            }
          },
          axisLine: {
            lineStyle: {
              color: "rgba(255,255,255,.1)"
            }
          },
          data: [
            "1月",
            "2月",
            "3月",
            "4月",
            "5月",
            "6月",
            "7月",
            "8月",
            "9月",
            "10月",
            "11月",
            "12月"
          ]
        }
      ],
      yAxis: [
        {
          axisLabel: {
            show: true,
            textStyle: {
              color: "rgba(255,255,255,.6)"
            }
          },
          axisLine: {
            lineStyle: {
              color: "rgba(255,255,255,.1)"
            }
          },
          splitLine: {
            lineStyle: {
              color: "rgba(255,255,255,.1)"
            }
          }
        }
      ],
      series: [
        {
          name: "完成率",
          type: "line",
          smooth: true,
          symbol: "circle",
          symbolSize: 5,
          showSymbol: false,
          lineStyle: {
            normal: {
              width: 3
            }
          },

          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(
                0,
                0,
                0,
                1,
                [
                  {
                    offset: 0,
                    color: "rgba(98, 201, 141, 0.5)"
                  },
                  {
                    offset: 1,
                    color: "rgba(98, 201, 141, 0.1)"
                  }
                ],
                false
              ),
              shadowColor: "rgba(0, 0, 0, 0.1)",
              shadowBlur: 10
            }
          },
          itemStyle: {
            normal: {
              color: "#4cb9cf",
              borderColor: "rgba(98, 201, 141,0.27)",
              borderWidth: 12
            }
          },
          data: [91, 60, 70, 54, 80, 40, 99, 33, 80, 20, 60, 10]
        }
      ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    window.addEventListener("resize", function() {
      myChart.resize();
    });
  }
  //点击跳转
  // $("#chart_map").click(function() {
  //   window.location.href = "./page/index.html";
  // });
  // $(".t_btn2").click(function() {
  //   window.location.href = "./page/index.html?id=2";
  // });
  // $(".t_btn3").click(function() {
  //   window.location.href = "./page/index.html?id=3";
  // });
  // $(".t_btn4").click(function() {
  //   window.location.href = "./page/index.html?id=4";
  // });
  // $(".t_btn5").click(function() {
  //   window.location.href = "./page/index.html?id=5";
  // });
  // $(".t_btn6").click(function() {
  //   window.location.href = "./page/index.html?id=6";
  // });
  // $(".t_btn7").click(function() {
  //   window.location.href = "./page/index.html?id=7";
  // });
  // $(".t_btn8").click(function() {
  //   window.location.href = "./page/index.html?id=8";
  // });
  // $(".t_btn9").click(function() {
  //   window.location.href = "./page/index.html?id=9";
  // });
});
