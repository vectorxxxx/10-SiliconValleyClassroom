<template>
  <!-- 添加和修改课时表单 -->
  <el-dialog
    :visible="dialogVisible"
    title="添加课时"
    @close="close()"
  >

    <!-- 表单 -->
    <el-form :model="video" label-width="120px">

      <!-- 课时标题 -->
      <el-form-item label="课时标题">
        <el-input v-model="video.title" />
      </el-form-item>

      <!-- 课时排序 -->
      <el-form-item label="课时排序">
        <el-input-number v-model="video.sort" :min="0" />
      </el-form-item>

      <!-- 是否免费 -->
      <el-form-item label="是否免费">
        <el-radio-group v-model="video.isFree">
          <el-radio :label="1">免费</el-radio>
          <el-radio :label="0">默认</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="上传视频">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :file-list="fileList"
          :limit="1"
          :action="BASE_API + '/admin/vod/upload'"
          :on-exceed="handleUploadExceed"
          :on-success="handleUploadSuccess"
          :on-error="handleUploadError"
          :before-remove="handleBeforeRemove"
          :on-remove="handleOnRemove"
        >
          <el-button slot="trigger" size="small" type="primary">选择视频</el-button>
          <el-button
            :disabled="uploadBtnDisabled"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload()"
          >上传
          </el-button>
        </el-upload>
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
import vodApi from '@/api/vod/vod'
export default {
  data() {
    return {
      BASE_API: 'http://localhost:8301',
      dialogVisible: false,
      video: {
        sort: 0,
        isFree: 0
      },
      fileList: [],
      uploadBtnDisabled: false
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
          // 回显
          if (this.video.videoOriginalName) {
            this.fileList = [{ 'name': this.video.videoOriginalName }]
          } else {
            this.fileList = []
          }
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
    },

    // 上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },

    // 上传
    submitUpload() {
      this.uploadBtnDisabled = true
      this.$refs.upload.submit()
    },

    // 上传成功
    handleUploadSuccess(response, file, fileList) {
      this.uploadBtnDisabled = false
      this.video.videoSourceId = response.data
      this.video.videoOriginalName = file.name
    },

    // 上传失败
    handleUploadError(err, file, fileList) {
      this.uploadBtnDisabled = false
      this.$message.error(err.message)
    },

    // 删除确认
    handleBeforeRemove(file, fileList) {
      this.$confirm(`确定移除${file.name}吗？`)
    },

    // 删除
    handleOnRemove(file, fileList) {
      if (!this.video.videoSourceId) {
        return
      }
      vodApi.removeVideo(this.video.videoSourceId).then(res => {
        this.uploadBtnDisabled = false
        this.video.videoSourceId = ''
        this.video.videoOriginalName = ''
        videoApi.updateVideo(this.video)
        this.$message.success(res.message)
      })
    }
  }
}
</script>

<style scoped>

</style>
