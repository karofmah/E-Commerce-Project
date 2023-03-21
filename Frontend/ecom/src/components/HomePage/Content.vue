<script setup>
import { ref } from 'vue';
import ContentItemTemplate from './ContentItemTemplate.vue';

let contents = ref([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]);
</script>

<template>
    <div class="container">
        <ContentItemTemplate v-for="content in contents" :ID="content" :displayStyle="0"/>
    </div>
</template>

<style scoped>
    .container{
        display: flex;
        flex-flow: row wrap;
        justify-content: flex-start;
        align-items: center;
        /* width: 85%; */
        background-color: rgba(176, 176, 176, .2);
        border-radius: 8px;
        padding-left: 2em;
        gap: 10px;
    }
</style>
