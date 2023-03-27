<script setup>
   import Edit from '../components/User/Edit.vue';
   import Header from '../components/Header.vue'
   import Footer from '../components/Footer.vue'
</script>

<template>
   <Header />
   <Edit />
   <Footer id="footer"/>
</template>

<style scoped>
    #footer{
        transform: translateY(2em)
    }
</style>