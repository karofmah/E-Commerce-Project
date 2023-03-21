<script setup>

</script>

<template>
  <router-view v-slot="{ Component }">
    <Transition name="fade">
      <component :is="Component" />
    </Transition>
  </router-view>
</template>


<style style>
  .fade-enter-from, .fade-leave-to{
    opacity: 0;
  }

  .fade-enter-to, .fade-leave-from{
    opacity: 1;
  }

  .fade-enter-active, .fade-leave-active{
    transition: opacity 0.5s ease-out;
  }

</style>
