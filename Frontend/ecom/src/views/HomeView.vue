<script setup>
    import Header from '../components/Header.vue'
    import Footer from '../components/Footer.vue'
    import Categories from '../components/HomePage/Categories.vue'
    import Content from '../components/HomePage/Content.vue'
</script>

<template>
    <Header id="header"/>
    <div class="catAndCont">
        <Categories id="categories"/>
        <Content id="content"/>
    </div>
    <div class=""></div>
    <Footer id="footer"/>
</template>å

<style scoped>
    .container{
        display: flex;
    }
    .catAndCont{
        display: flex;
        flex-flow: row wrap;
        justify-content: center;
        align-items: start;
        width: 100%;
        margin: 2em 0;
    }
    #header{
        z-index: 2;
    }
    #content{
        flex: 1;
        margin: 0 1em;
    }
</style>