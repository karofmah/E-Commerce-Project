<script setup>
    import Header from '../components/Header.vue';
    import Categories from '../components/HomePage/Categories.vue'
    import Content from '../components/HomePage/Content.vue'
    import Foooter from '../components/Footer.vue'
</script>

<template>
    <Header id="header"/>
    <div class="catAndCont">
        <Categories id="categories"/>
        <Content id="content"/>
    </div>
    <div class=""></div>
    <Foooter id="footer"/>
</template>

<style scoped>
    .container{
        display: flex;
    }
    .catAndCont{
        display: flex;
        flex-flow: column wrap;
        width: 100%;
        transform: translateY(10em);
    }
    #header{
        z-index: 2;
    }
    #categories{
        position: absolute;
    }
    #content{
        margin: 0 20%;
    }
    #footer{
        transform: translateY(10em);
    }
</style>