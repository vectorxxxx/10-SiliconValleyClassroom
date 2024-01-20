<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-card class="operate-container" shadow="never">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searchObj.outTradeNo" placeholder="订单号"/>
        </el-form-item>

        <el-form-item>
          <el-input v-model="searchObj.phone" placeholder="手机"/>
        </el-form-item>

        <el-form-item>
          <el-date-picker
            v-model="searchObj.createTimeBegin"
            type="date"
            placeholder="选择下单开始日期"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>

        <el-form-item>
          <el-date-picker
            v-model="searchObj.createTimeEnd"
            type="date"
            placeholder="选择下单截止日期"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>

        <el-form-item>
          <el-select
            v-model="searchObj.orderStatus"
            placeholder="订单状态"
            class="v-select patient-select"
          >
            <el-option
              v-for="item in statusList"
              :key="item.status"
              :label="item.name"
              :value="item.status"
            />
          </el-select>
        </el-form-item>

        <!-- 操作按钮 -->
        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form>
    </el-card>

    <!-- 列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      fit
      border
      highlight-current-row
    >
      <el-table-column
        label="序号"
        width="60"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="outTradeNo" label="订单号" width="160"/>

      <el-table-column prop="courseName" label="课程名称" width="160">
        <template slot-scope="scope">
          {{ scope.row.param.courseName }}
        </template>
      </el-table-column>

      <el-table-column prop="finalAmount" label="订单金额" width="90"/>

      <el-table-column prop="nickName" label="下单用户"/>

      <el-table-column prop="phone" label="用户手机"/>

      <el-table-column prop="payTime" label="支付时间" width="156"/>

      <el-table-column prop="orderStatus" label="订单状态">
        <template slot-scope="scope">
          {{ scope.row.orderStatus === '0' ? '未支付' : '已支付' }}
        </template>
      </el-table-column>

      <el-table-column prop="createTime" label="下单时间" width="156"/>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="fetchData"
      @size-change="changeSize"
    />
  </div>
</template>

<script>
import orderInfoApi from '@/api/order/orderInfo'

export default {
  data() {
    return {
      searchObj: {
        outTradeNo: '',
        phone: '',
        createTimeBegin: '',
        createTimeEnd: '',
        orderStatus: ''
      },
      statusList: [
        {
          'status': 0,
          'name': '未支付'
        },
        {
          'status': 1,
          'name': '已支付'
        }
      ],
      total: 0,
      page: 1,
      limit: 10,
      list: [],
      listLoading: true
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    fetchData(page = 1) {
      this.page = page
      orderInfoApi.getPageList(page, this.limit, this.searchObj).then(res => {
        this.list = res.data.records
        this.total = res.data.total
        this.listLoading = false
      })
    },

    resetData() {
      this.searchObj = {}
      this.fetchData(1)
    },

    changeSize(size) {
      this.limit = size
      this.fetchData(1)
    }
  }
}
</script>

<style scoped>

</style>
