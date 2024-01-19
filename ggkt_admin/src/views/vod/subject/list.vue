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
  </div>
</template>

<script>
import subjectApi from '@/api/vod/subject'
export default {
  data() {
    return {
      list: []
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
    }
  }
}
</script>

<style scoped>

</style>
