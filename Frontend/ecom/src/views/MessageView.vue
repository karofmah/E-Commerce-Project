<script setup>
    import Message from '../components/Message.vue';
    import Header from '../components/Header.vue'
    import Footer from '../components/Footer.vue'
</script>

<template>
    <Header />
    <Message />
    <Footer id="footer"/>
</template>

<style scoped>
    #footer{
        transform: translateY(2em)
    }
</style>