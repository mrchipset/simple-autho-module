<template>
  <el-container>
    <div class="container">
      <router-view/>
    </div>
  </el-container>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  align-items: center;
  display: flex;
  justify-content: center;
}

.container {
  width: 100%;
  align-items: center;
  display: flex;
  justify-content: center;
}
</style>
