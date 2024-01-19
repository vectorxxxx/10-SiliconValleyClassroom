<template>
  <!-- 添加和修改章节表单 -->
  <el-dialog
    :visible="dialogVisible"
    title="添加章节"
    @close="close()"
  >
    <el-form :model="chapter" label-width="120px">
      <el-form-item label="章节标题">
        <el-input v-model="chapter.title" />
      </el-form-item>
      <el-form-item label="章节排序">
        <el-input-number v-model="chapter.sort" :min="0" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import chapterApi from '@/api/vod/chapter'
export default {
  data() {
    return {
      dialogVisible: false,
      chapter: {
        sort: 0
      }
    }
  },

  methods: {
    // 初始化
    open(chapterId) {
      this.dialogVisible = true
      if (chapterId) {
        chapterApi.getChapterById(chapterId).then(res => {
          this.chapter = res.data
        })
      }
    },

    // 关闭
    close() {
      this.dialogVisible = false
      // 重置表单
      this.resetForm()
    },

    // 重置表单
    resetForm() {
      this.chapter = {
        sort: 0
      }
    },

    // 保存或修改
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 保存数据
    saveData() {
      this.chapter.courseId = this.$parent.$parent.courseId
      chapterApi.addChapter(this.chapter).then(res => {
        this.$message.success(res.message)
        this.close()
        this.$parent.fetchNodeList()
      })
    },

    // 更新数据
    updateData() {
      chapterApi.updateChapter(this.chapter).then(res => {
        this.$message.success(res.message)
        this.close()
        this.$parent.fetchNodeList()
      })
    }
  }
}
</script>

<style scoped>

</style>
