<script setup>
    import NewItem from '../components/User/NewItem.vue';
    import Header from '../components/Header.vue'
    import Footer from '../components/Footer.vue'
</script>

<template>
    <Header />
    <NewItem />
    <Footer id="footer"/>
</template>

<style scoped>
    #footer{
        transform: translateY(2em)
    }
</style>

