<script setup>
  import UserInfo from '../components/User/User.vue'
  import Header from '../components/Header.vue'
  import Footer from '../components/Footer.vue'
</script>

<template>
  <Header />
  <UserInfo :ID="1"/>
  <Footer />
</template>

<style scoped>

</style>