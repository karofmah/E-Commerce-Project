<script setup>
import { useTokenStore } from '@/stores/userToken';
import {onMounted, watch} from "vue";
const store = useTokenStore();


watch(() => store.loggedInUser, refreshToken);
onMounted(() => {
  if (store.loggedInUser) {
    refreshToken()
  }
});
async function refreshToken() {
  setInterval(async () => {
    if(store.loggedInUser) {
      console.log("refreshing token")
      return await store.getTokenAndSaveInStore(store.loggedInUser.email, store.loggedInUser.password)
    }
  }, 1000 * 60 * 5)
}


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
