<template>
  <div class="app-container">
    讲师列表

    <!--查询表单-->
    <el-card class="operate-container" shadow="never">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="名称">
          <el-input v-model="searchObj.name" placeholder="讲师名称" />
        </el-form-item>

        <el-form-item label="头衔">
          <el-select v-model="searchObj.level" placeholder="讲师头衔" clearable>
            <el-option value="1" label="高级讲师" />
            <el-option value="0" label="首席讲师" />
          </el-select>
        </el-form-item>

        <el-form-item label="入驻时间">
          <el-date-picker
            v-model="searchObj.joinDateBegin"
            placeholder="开始时间"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-form-item label="-">
          <el-date-picker
            v-model="searchObj.joinDateEnd"
            placeholder="结束时间"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button @click="resetData()">重置</el-button>
      </el-form>
    </el-card>

    <!--工具按钮-->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px" />
      <span style="margin-top: 5px">数据列表</span>
      <el-button class="btn-add" style="margin-left: 10px;" @click="add()">添加</el-button>
      <el-button class="btn-add" @click="batchRemove()">批量删除</el-button>
    </el-card>

    <!--表格-->
    <el-table
      :data="list"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <!--复选框-->
      <el-table-column type="selection" />

      <el-table-column
        label="#"
        width="50"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" />

      <el-table-column label="头衔" width="90">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.level === 1" type="success" size="mini">高级讲师</el-tag>
          <el-tag v-if="scope.row.level === 0" size="mini">首席讲师</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="简介" />

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column prop="joinDate" label="入驻时间" width="160" />

      <el-table-column label="操作" width="120">
        <template slot-scope="scope">

          <el-button type="text" size="mini" @click="removeById(scope.row.id)">删除</el-button>

          <router-link :to="'/vod/teacher/edit/' + scope.row.id">
            <el-button type="text" size="mini">编辑</el-button>
          </router-link>

        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      :total="total"
      :current-page="page"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
    />
  </div>
</template>

<script>
import teacherApi from '@/api/vod/teacher'

export default {
  data() {
    return {
      list: [],
      total: 0,
      page: 1,
      limit: 10,
      searchObj: {},
      multipleSelection: [] // 批量删除选中的记录列表
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    // 获取数据
    fetchData() {
      teacherApi.pageList(this.page, this.limit, this.searchObj).then(res => {
        this.list = res.data.records
        this.total = res.data.total
      })
    },

    // 改变每页显示记录数
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },

    // 改变页码数
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },

    // 清空
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },

    // 删除讲师
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return teacherApi.removeById(id)
      }).then(() => {
        this.$message.success('删除成功!')
        this.fetchData()
      }).catch(err => {
        if (err === 'cancel') {
          this.$message.info('已取消删除')
        }
      })
    },

    // 批量删除
    batchRemove() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning('请选择要删除的记录!')
        return
      }
      this.$confirm('此操作将永久删除选中的记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = []
        this.multipleSelection.forEach(item => {
          ids.push(item.id)
        })
        return teacherApi.removeBatch(ids)
      }).then(() => {
        this.$message.success('删除成功!')
        this.fetchData()
      }).catch(err => {
        if (err === 'cancel') {
          this.$message.info('已取消删除')
        }
      })
    },

    // 当多选选项发生变化的时候调用
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },

    // 添加
    add() {
      this.$router.push({ path: '/vod/teacher/create' })
    }
  }
}
</script>

<style scoped>

</style>
