<script setup>
    import Item from '../components/Item.vue';

    function getImage() {
        return ["bag-plus.svg", "chat-left-dots.svg", "person-fill.svg", "plus-square-fill.svg"];
    }
</script>

<template>
    <Item :imgs="getImage()"/>
</template>