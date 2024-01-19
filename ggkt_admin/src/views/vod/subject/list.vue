<template>
  <div class="app-container">
    <el-table
      :data="list"
      style="width: 100%"
      row-key="id"
      border
      lazy
      :load="load"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        prop="title"
        label="名称"
        width="180"
      />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="180"
      />
    </el-table>

    <div class="el-toolbar">
      <div class="el-toolbar-body" style="justify-content: flex-start;">
        <el-button type="text" @click="exportData()"><i class="fa fa-download" /> 导出</el-button>
        <el-button type="text" @click="importData()"><i class="fa fa-upload" /> 导入</el-button>
      </div>
    </div>

    <!-- 导入弹出层 -->
    <el-dialog title="导入" :visible.sync="dialogImportVisible" width="480px">
      <el-form label-position="right" label-width="170px">
        <el-form-item label="文件">
          <el-upload
            ref="upload"
            class="upload-demo"
            :multiple="false"
            :action="BASE_API + '/admin/vod/subject/importData'"
            :on-success="onUploadSuccess"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogImportVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import subjectApi from '@/api/vod/subject'

export default {
  data() {
    return {
      BASE_API: 'http://localhost:8301',
      list: [],
      dialogImportVisible: false
    }
  },

  created() {
    this.getSubList(0)
  },

  methods: {
    getSubList(id) {
      subjectApi.getChildList(id).then(res => {
        this.list = res.data
      })
    },

    load(tree, treeNode, resolve) {
      subjectApi.getChildList(tree.id).then(res => {
        resolve(res.data)
      })
    },

    // 导出数据
    exportData() {
      window.open(`${this.BASE_API}/admin/vod/subject/exportData`)
    },

    // 导入数据
    importData() {
      this.dialogImportVisible = true
    },

    // 导入数据成功回调
    onUploadSuccess(response, file, fileList) {
      this.$message.success('上传成功')
      // 清空文件
      this.$refs.upload.clearFiles()
      this.dialogImportVisible = false
      this.getSubList(0)
    }
  }
}
</script>

<style scoped>

</style>
