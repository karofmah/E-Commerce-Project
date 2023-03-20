<script setup>
    import Item from '../components/Item.vue';
    import Header from '../components/Header.vue'
    import Footer from '../components/Footer.vue'

    function getImage() {
        return ["bag-plus.svg", "chat-left-dots.svg", "person-fill.svg", "plus-square-fill.svg"];
    }
</script>

<template>
    <Header />
    <Item :imgs="getImage()"/>
    <Footer />
</template>