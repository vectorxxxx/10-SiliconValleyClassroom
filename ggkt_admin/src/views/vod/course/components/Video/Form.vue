<template>
  <!-- 添加和修改课时表单 -->
  <el-dialog
    :visible="dialogVisible"
    title="添加课时"
    @close="close()"
  >

    <!-- 表单 -->
    <el-form :model="video" label-width="120px">
      <el-form-item label="课时标题">
        <el-input v-model="video.title" />
      </el-form-item>
      <el-form-item label="课时排序">
        <el-input-number v-model="video.sort" :min="0" />
      </el-form-item>
      <el-form-item label="是否免费">
        <el-radio-group v-model="video.isFree">
          <el-radio :label="0">免费</el-radio>
          <el-radio :label="1">默认</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>

    <!-- 底部按钮 -->
    <div slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import videoApi from '@/api/vod/video'
export default {
  data() {
    return {
      dialogVisible: false,
      video: {
        sort: 0,
        isFree: 0
      }
    }
  },

  methods: {
    // 初始化
    open(chapterId, videoId) {
      this.dialogVisible = true
      this.video.chapterId = chapterId
      if (videoId) {
        videoApi.getVideoById(videoId).then(res => {
          this.video = res.data
        })
      }
    },

    saveOrUpdate() {
      if (!this.video.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    saveData() {
      this.video.courseId = this.$parent.$parent.courseId
      videoApi.addVideo(this.video).then(res => {
        this.$message.success(res.message)
        this.close()
        this.$parent.fetchNodeList()
      })
    },

    updateData() {
      videoApi.updateVideo(this.video).then(res => {
        this.$message.success(res.message)
        this.close()
        this.$parent.fetchNodeList()
      })
    },

    close() {
      this.dialogVisible = false
      // 重置表单
      this.resetForm()
    },

    resetForm() {
      this.video = {
        sort: 0,
        isFree: 0
      }
    }
  }
}
</script>

<style scoped>

</style>
