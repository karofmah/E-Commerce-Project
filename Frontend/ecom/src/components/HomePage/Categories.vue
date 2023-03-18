<script setup>
import { ref } from 'vue';

let categories = ref(["cat1","cat2","cat3","cat4","cat5","cat6","cat7","cat8", "cat9"]);

</script>

<template>
    <div class="container">
        <div class="categories" v-for="categorie in categories">
            <img src="" alt="{{ categorie }}">
            <h3>{{ categorie }}</h3>
        </div>
    </div>
</template>

<style scoped>
    .container{
        display: flex;
        flex-flow: column wrap;
        background-color: rgba(176, 176, 176, .2);
        border-radius: 8px;
        margin: 0 0 0 0;
        width: 18%;
    }
    .categories{
        display: flex;
        flex-direction: column;
        height: 20vh;
        width: 10vw;
        margin: 5px;
        background-color: rgba(176, 176, 176, .8);
        border-radius: 8px;
        text-align: center;
    }
</style>