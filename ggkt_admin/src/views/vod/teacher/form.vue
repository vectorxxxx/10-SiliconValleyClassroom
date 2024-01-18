<template>
  <div class="app-container">
    <!-- 输入表单 -->
    <el-form label-width="120px">

      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>

      <el-form-item label="入驻时间">
        <el-date-picker v-model="teacher.joinDate" value-format="yyyy-MM-dd" />
      </el-form-item>

      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" :min="0" />
      </el-form-item>

      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level">
          <el-option label="高级讲师" :value="1" />
          <el-option label="首席讲师" :value="0" />
        </el-select>
      </el-form-item>

      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" />
      </el-form-item>

      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" type="textarea" :rows="10" />
      </el-form-item>

      <el-form-item label="讲师头像" />

      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacherApi from '@/api/vod/teacher'
export default {
  data() {
    return {
      BASE_API: 'http://localhost:8301',
      teacher: {
        sort: 0,
        level: 1
      }
    }
  },

  created() {
    const id = this.$route.params.id
    if (id) {
      this.fetchDataById(id)
    }
  },

  methods: {
    // 根据id获取数据
    fetchDataById(id) {
      teacherApi.getById(id).then(res => {
        this.teacher = res.data
      })
    },
    // 保存或更新
    saveOrUpdate() {
      if (!this.teacher.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 保存数据
    saveData() {
      teacherApi.addTeacher(this.teacher).then(res => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        })
        this.$router.push({ path: '/vod/teacher/list' })
      })
    },

    // 更新数据
    updateData() {
      teacherApi.updateTeacher(this.teacher).then(res => {
        this.$message({
          type: 'success',
          message: '更新成功!'
        })
        this.$router.push({ path: '/vod/teacher/list' })
      })
    }
  }
}
</script>

<style scoped>

</style>
