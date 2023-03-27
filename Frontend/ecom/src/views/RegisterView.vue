<script setup>
    import Register from '../components/User/Register.vue';
    import Header from '../components/Header.vue'
    import Footer from '../components/Footer.vue'
</script>

<template>
    <Header />
    <Register />
    <Footer id="footer"/>
</template>

<style scoped>
    #footer{
        transform: translateY(2em)
    }
</style>