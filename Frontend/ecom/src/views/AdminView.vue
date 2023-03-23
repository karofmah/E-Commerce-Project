<template>

<div class="admin-container">
    <Header/>
    <Adminpage/>
    <Footer/>
</div>

</template>




<script setup>
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import Adminpage from '../components/Adminpage.vue';

</script>


<style scoped>

.admin-container{
    display: flex;
    flex-direction: column;
}


</style>