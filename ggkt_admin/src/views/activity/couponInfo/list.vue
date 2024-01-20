<template>
  <div class="app-container">

    <!-- 工具条 -->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px" />
      <span style="margin-top: 5px">数据列表</span>
      <el-button class="btn-add" size="mini" @click="add()">添加</el-button>
    </el-card>

    <!-- banner列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据正在加载......"
      border
      fit
      highlight-current-row
    >

      <el-table-column
        label="序号"
        width="70"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="couponName" label="购物券名称" />
      <el-table-column prop="couponType" label="购物券类型">
        <template slot-scope="scope">
          {{ scope.row.couponType === 'REGISTER' ? '注册卷' : '推荐赠送卷' }}
        </template>
      </el-table-column>
      <el-table-column label="规则">
        <template slot-scope="scope">
          {{ `现金卷：${scope.row.amount}元` }}
        </template>
      </el-table-column>
      <el-table-column label="使用范围 ">
        所有商品
      </el-table-column>
      <el-table-column prop="publishCount" label="发行数量" />
      <el-table-column prop="expireTime" label="过期时间" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="`/activity/couponInfo/edit/${scope.row.id}`">
            <el-button size="mini" type="text">修改</el-button>
          </router-link>
          <el-button size="mini" type="text" @click="removeDataById(scope.row.id)">删除</el-button>
          <router-link :to="`/activity/couponInfo/show/${scope.row.id}`">
            <el-button size="mini" type="text">详情</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
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
import couponInfoApi from '@/api/activity/couponInfo'

export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: [], // banner列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      searchObj: {
        couponType: '',
        amount: '',
        id: ''
      } // 查询表单对象
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    fetchData(page = 1) {
      this.page = page
      couponInfoApi.getPageList(this.page, this.limit).then(res => {
        this.list = res.data.records
        this.total = res.data.total
        this.listLoading = false
      })
    },

    // 重置查询表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },

    // 当页码发生改变的时候
    changeSize(size) {
      this.limit = size
      this.fetchData(1)
    },

    add() {
      this.$router.push({ path: '/activity/couponInfo/add' })
    },

    // 根据id删除数据
    removeDataById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return couponInfoApi.removeById(id)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.code) {
          this.$message.success('删除成功!')
        }
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    }
  }
}
</script>

<style scoped>

</style>
