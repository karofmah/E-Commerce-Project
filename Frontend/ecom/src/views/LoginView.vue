<script setup>
    import Login from '../components/User/Login.vue';
    import Header from '../components/Header.vue'
    import Footer from '../components/Footer.vue'
</script>

<template>
    <Header />
    <Login/>
    <Footer id="footer"/>
</template>
  
<style scoped>
    #footer{
        transform: translateY(2em)
    }
</style>