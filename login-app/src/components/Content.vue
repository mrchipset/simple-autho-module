<template>
  <div class="Content">
    <el-card>
      <h1>信息</h1>
      <p><span>{{username}}</span></p>
      <p><span>{{token}}</span></p>
    </el-card>
  </div>
</template>

<script>

export default {
  name: 'Content',
  components: {
  },
  props: [
    'username',
    'token'
  ],
}
</script>