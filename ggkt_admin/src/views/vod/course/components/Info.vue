<template>
  <div class="app-container">
    <!-- 课程信息表单 -->
    <el-form label-width="120px">

      <!-- 课程标题 -->
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择"
        >
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <!-- 所属分类：级联下拉列表 -->
      <el-form-item label="课程类别">
        <!-- 一级分类 -->
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="请选择"
          @change="subjectChanged"
        >
          <el-option
            v-for="subject in subjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
        <!-- 二级分类 -->
        <el-select
          v-model="courseInfo.subjectId"
          placeholder="请选择"
        >
          <el-option
            v-for="subject in subjectLevelTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>

      <!-- 总课时 -->
      <el-form-item label="总课时">
        <el-input-number
          v-model="courseInfo.lessonNum"
          :min="0"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <el-input
          v-model="courseInfo.description"
          type="textarea"
          rows="5"
        />
      </el-form-item>

      <!-- 课程封面 -->
      <el-form-item label="课程封面">
        <el-upload
          class="cover-uploader"
          :show-file-list="false"
          :action="BASE_API+'/admin/vod/file/upload'"
          :before-upload="beforeCoverUpload"
          :on-success="handleCoverSuccess"
          :on-error="handleCoverError"
        >
          <img v-if="courseInfo.cover" :src="courseInfo.cover">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>

      <!-- 课程价格 -->
      <el-form-item label="课程价格">
        <el-input-number
          v-model="courseInfo.price"
          :min="0"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        /> 元
      </el-form-item>
    </el-form>

    <div style="text-align:center">
      <el-button
        :disabled="saveBtnDisabled"
        type="primary"
        @click="saveAndNext()"
      >保存并下一步</el-button>
    </div>
  </div>
</template>
<script>
import courseApi from '@/api/vod/course'
import teacherApi from '@/api/vod/teacher'
import subjectApi from '@/api/vod/subject'

export default {
  data() {
    return {
      BASE_API: process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false, // 按钮是否禁用
      courseInfo: { // 表单数据
        price: 0,
        lessonNum: 0,
        // 以下解决表单数据不全时insert语句非空校验
        teacherId: '',
        subjectId: '',
        subjectParentId: '',
        cover: '',
        description: ''
      },
      teacherList: [], // 讲师列表
      subjectList: [], // 一级分类列表
      subjectLevelTwoList: []// 二级分类列表
    }
  },

  created() {
    if (this.$parent.courseId) {
      this.fetchCourseInfoById(this.$parent.courseId)
    } else {
      // 初始化分类列表
      this.initSubjectList()
    }
    // 初始化讲师列表
    this.initTeacherList()
  },

  methods: {
    // 根据课程id查询课程信息
    fetchCourseInfoById(id) {
      // 获取课程信息
      courseApi.getCourseInfoById(id).then(res => {
        this.courseInfo = res.data
        // 获取一级分类列表
        subjectApi.getChildList(0).then(res => {
          this.subjectList = res.data
          this.subjectList.forEach(subject => {
            if (subject.id === this.courseInfo.subjectParentId) {
              debugger
              // 获取二级分类列表
              subjectApi.getChildList(subject.id).then(res => {
                this.subjectLevelTwoList = res.data
              })
            }
          })
        })
      })
    },

    // 初始化分类列表
    initSubjectList() {
      subjectApi.getChildList(0).then(res => {
        this.subjectList = res.data
      })
    },

    // 初始化讲师列表
    initTeacherList() {
      teacherApi.findAll().then(res => {
        this.teacherList = res.data
      })
    },

    // 一级二级分类联动
    subjectChanged(value) {
      subjectApi.getChildList(value).then(res => {
        this.subjectLevelTwoList = res.data
        this.courseInfo.subjectId = ''
      })
    },

    // 上传校验
    beforeCoverUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },

    // 上传成功
    handleCoverSuccess(res, file) {
      this.courseInfo.cover = res.data
      this.$message.success('上传成功')
    },

    // 上传失败
    handleCoverError() {
      this.$message.success('上传失败')
    },

    // 保存并下一步
    saveAndNext() {
      this.saveBtnDisabled = true
      if (!this.$parent.courseId) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 保存数据
    saveData() {
      courseApi.saveCourseInfo(this.courseInfo).then(res => {
        this.$message.success(res.message)
        this.$parent.courseId = res.data
        this.$parent.active = 1
      })
    },

    // 更新数据
    updateData() {
      courseApi.updateCourseInfo(this.courseInfo).then(res => {
        this.$message.success(res.message)
        this.$parent.active = 1
      })
    }
  }
}
</script>
<style scoped>
.tinymce-container {
  position: relative;
  line-height: normal;
}
.cover-uploader .avatar-uploader-icon {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;

  font-size: 28px;
  color: #8c939d;
  width: 640px;
  height: 357px;
  line-height: 357px;
  text-align: center;
}
.cover-uploader .avatar-uploader-icon:hover {
  border-color: #409EFF;
}
.cover-uploader img {
  width: 640px;
  height: 357px;
  display: block;
}
</style>
