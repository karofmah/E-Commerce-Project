<template>
    <div class="centered">
      <h1>TAKK FOR KJØPET</h1>
      <button @click="goToStart">Ta meg tilbake til start</button>
    </div>
  </template>
  
  <script setup>
    import { useRouter } from 'vue-router';
    const router = useRouter();

    function goToStart(){
        router.push({ name: "Home" });
    }


  </script>
  
  <style>
  .centered {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  </style>
  