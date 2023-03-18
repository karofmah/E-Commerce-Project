<script setup>
    import Header from '../components/Header.vue';
    import Categories from '../components/HomePage/Categories.vue'
    import Content from '../components/HomePage/Content.vue'
</script>

<template>
    <Header/>
    <div class="catAndCont">
        <Categories id="categories"/>
        <Content id="content"/>
    </div>
</template>

<style scoped>
    .catAndCont{
        display: flex;
        flex-flow: column wrap;
        width: 100%;
    }
    #categories{
        position: absolute;
    }
    #content{
        margin: 0 20%;
    }
</style>