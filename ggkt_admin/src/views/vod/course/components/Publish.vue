<template>
  <div class="app-container">
    <!--课程预览-->
    <div class="ccInfo">
      <img :src="coursePublish.cover">
      <div class="main">
        <h2> {{ coursePublish.title }}</h2>
        <p class="gray"><span>共{{ coursePublish.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ coursePublish.subjectParentTitle }} - {{ coursePublish.subjectTitle }}</span></p>
        <p>课程讲师：{{ coursePublish.teacherName }}</p>
        <h3 class="red">￥{{ coursePublish.price }}</h3>
      </div>
    </div>

    <!-- 底部按钮 -->
    <div style="text-align:center">
      <el-button type="primary" @click="prev()">上一步</el-button>
      <el-button :disabled="publishBtnDisabled" type="primary" @click="publish()">发布课程</el-button>
    </div>
  </div>
</template>

<script>
import courseApi from '@/api/vod/course'

export default {
  data() {
    return {
      courseId: '',
      coursePublish: {
        cover: '',
        title: '',
        lessonNum: 0,
        subjectParentTitle: '',
        subjectTitle: '',
        teacherName: ''
      },
      publishBtnDisabled: false
    }
  },

  created() {
    this.courseId = this.$parent.courseId
    if (this.courseId) {
      this.fetchCoursePublishById(this.courseId)
    }
  },

  methods: {
    fetchCoursePublishById(id) {
      courseApi.getCoursePublishInfo(id).then(res => {
        this.coursePublish = res.data
      })
    },

    // 上一步
    prev() {
      this.$parent.active = 1
    },

    // 发布
    publish() {
      this.publishBtnDisabled = true
      courseApi.publishCourseInfo(this.courseId).then(res => {
        this.$parent.active = 3
        this.$message.success(res.message)
        this.$router.push({path: '/vodcourse/course/list'})
      })
    }
  }
}
</script>

<style scoped>

</style>
