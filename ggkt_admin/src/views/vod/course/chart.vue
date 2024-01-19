<template>
  <div class="app-container">

    <!-- 查询条件 -->
    <el-form
      :inline="true"
      class="demo-form-inline"
    >
      <el-form-item>
        <el-date-picker
          v-model="startDate"
          type="date"
          placeholder="请选择开始日期"
          value-format="yyyy-MM-dd"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="endDate"
          type="date"
          placeholder="请选择结束日期"
          value-format="yyyy-MM-dd"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="btnDisabled"
          type="primary"
          icon="el-icon-search"
          @click="showChart()"
        >查询
        </el-button>
      </el-form-item>
    </el-form>

    <!-- Echarts图标区域 -->
    <div id="chart" class="chart" style="height: 500px" />
  </div>
</template>

<script>
import echarts from 'echarts'
import videoVisitorApi from '@/api/vod/videoVisitor'
export default {
  data() {
    return {
      courseId: '',
      startDate: '',
      endDate: '',
      btnDisabled: false
    }
  },

  created() {
    this.courseId = this.$route.params.id
    const currentDate = new Date()
    this.startDate = this.dateFormat(new Date(currentDate.getTime()) - 7 * 24 * 60 * 60 * 1000)
    this.endDate = this.dateFormat(currentDate)
    this.showChart()
  },

  methods: {
    // 显示统计图表
    showChart() {
      debugger
      videoVisitorApi.findCount(this.courseId, this.startDate, this.endDate).then(res => {
        this.setChartData(res.data)
      })
    },

    // 设置统计数据
    setChartData(data) {
      const myChart = echarts.init(document.getElementById('chart'))
      const option = {
        title: {
          text: '观看课程人数统计'
        },
        xAxis: {
          data: data.xData
        },
        yAxis: {
          minInterval: 1
        },
        series: {
          type: 'line',
          data: data.yData
        }
      }
      myChart.setOption(option)
    },

    // 日期格式化
    dateFormat(date) {
      // 这是一个 Vue 组件的方法，用于将日期对象转换为指定格式的字符串。以下是这个方法的详细解释：
      // 1. 定义一个名为 `dateFormat` 的方法，接收一个日期对象 `date` 作为参数。
      // 2. 创建一个名为 `opt` 的对象，用于存储格式化后的日期部分。
      // 3. 使用 `date.getFullYear()`、`date.getMonth() + 1`、`date.getDate()`、`date.getHours()`、`date.getMinutes()` 和 `date.getSeconds()` 获取年、月、日、小时、分钟和秒，并将它们分别存储在 `opt` 对象中。
      // 4. 创建一个名为 `fmt` 的字符串，用于存储格式化后的日期模板。这里使用的是 `'YYYY-MM-dd'`，表示年-月-日格式的日期。
      // 5. 使用一个 `for...in` 循环遍历 `opt` 对象中的每个键值对。
      // 6. 在循环中，使用 `RegExp` 对象和 `exec` 方法获取 `fmt` 字符串中与当前键值对键匹配的子字符串。
      // 7. 如果找到了匹配的子字符串，将该子字符串替换为 `opt` 对象中对应键的值。这里使用了 `padStart` 方法确保替换后的值始终具有相同的位数。
      // 8. 最后，返回格式化后的日期字符串 `fmt`。
      // 总之，这个方法将一个日期对象转换为指定格式的字符串，其中可以包含年、月、日、小时、分钟和秒等日期信息。
      const opt = {
        'Y+': date.getFullYear().toString(),
        'm+': (date.getMonth() + 1).toString(),
        'd+': date.getDate().toString(),
        'H+': date.getHours().toString(),
        'M+': date.getMinutes().toString(),
        'S+': date.getSeconds().toString()
      }
      let fmt = 'YYYY-MM-dd'
      for (const k in opt) {
        const ret = new RegExp('(' + k + ')').exec(fmt)
        if (ret) {
          const replaceValue = ret[1].length === 1 ? opt[k] : (opt[k].padStart(ret[1].length, '0'))
          fmt = fmt.replace(ret[1], replaceValue)
        }
      }
      return fmt
    }
  }
}
</script>

<style scoped>

</style>
