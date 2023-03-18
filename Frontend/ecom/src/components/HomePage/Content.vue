<script setup>
import { ref } from 'vue';

let contents = ref([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]);
</script>

<template>
    <div class="container">
        <div class="content" v-for="content in contents">{{ content }}</div>
    </div>
</template>

<style scoped>
    .container{
        display: flex;
        flex-flow: row wrap;
        margin: auto;
        width: 80%;
        background-color: rgba(176, 176, 176, .2);
        border-radius: 8px;
    }

    .content{
        display: block;
        height: 35vh;
        max-width: 15vw;
        margin: 10px;
        background-color: rgba(176, 176, 176, .5);
        border-radius: 8px;
        text-align: center;
        font-size: 8rem;
    }
</style>
