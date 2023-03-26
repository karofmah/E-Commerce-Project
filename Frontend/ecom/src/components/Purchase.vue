<template>
    <div class="centered">
      <h1>{{ t("thanksForThePurchase") }}</h1>
      <button @click="goToStart">{{ t("returnToStart") }}</button>
    </div>
  </template>
  
<script setup>
    import { useRouter } from 'vue-router';
    import { useI18n } from 'vue-i18n';

    const { t } = useI18n();
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
  