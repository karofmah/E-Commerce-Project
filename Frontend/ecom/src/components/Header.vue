<template>
  <div class="header" :class="{ 'is-scrolled': scrollPosition > 125 }">
    <div class="logo">
      <a @click="changeRoute('Home')" href=""><img src="../assets/logo.png" alt=""></a>
    </div>
    <div class="links">
      <div class="link-pair">
        <img @click="handleItemClick('NewItem')" src="../assets/bag-plus.svg" class="icon" alt="New Ad">
        <a @click="handleItemClick('NewItem')" href="">Ny annonse</a>
      </div>
      <div class="link-pair">
        <img @click="handleItemClick('Message')" src="../assets/chat-left-dots.svg" class="icon" alt="Message">
        <a @click="handleItemClick('Message')" href="">Meldinger</a>
      </div>
      <div class="link-pair">
      <img @click="handleItemClick('Cart')" src="../assets/cart3.svg" class="icon" alt="Cart">
      <a @click="handleItemClick('Cart')" href="">Cart</a>
    </div>
      <div class="link-pair">
        <img @click="changeRoute('UserInfo')" src="../assets/person-fill.svg" class="icon" alt="Log in">
        <template v-if="tokenStore.jwtToken && tokenStore.jwtToken !== null">
          <a @click="changeRoute('UserInfo')" href="">{{ tokenStore.loggedInUser.firstName }}</a>
        </template>
        <template v-else>
          <a @click="changeRoute('Login')" href="">Logg inn</a>
        </template>
      </div>
      <div v-if="tokenStore.loggedInUser && tokenStore.loggedInUser.role === 'ADMINISTRATOR'" class="link-pair">
      <img @click="changeRoute('admin')" src="../assets/person-fill.svg" class="icon" alt="Admin">
      <a @click="changeRoute('admin')" href="">Admin</a>
    </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import { useTokenStore } from "../stores/userToken";
import { useRouter } from "vue-router";
import { watch } from 'vue';

export default {
  data() {
    return {
      scrollPosition: 0
    };
  },
  setup() {
    const tokenStore = useTokenStore();
    const router = useRouter();

    watch(
      () => tokenStore.jwtToken,
      () => {
        if (tokenStore.jwtToken === null) {
          // Redirect to the Login page if jwtToken becomes null
          router.push("Login");
        }
      }
    );

    return { tokenStore };
  },
  methods: {
    updateScroll() {
      this.scrollPosition = window.scrollY;
    },
    handleItemClick(routeName) {
      if (this.tokenStore.jwtToken !== null) {
        this.changeRoute(routeName);
      } else {
        this.changeRoute('Login');
      }
    },
    changeRoute(string) {
      this.$router.push({ name: string });
    },
  },
  async mounted() {
    window.addEventListener("scroll", this.updateScroll);
  },
};
</script>


<style scoped>




.header {
  position: sticky;
  top: 0;
  background: var(--color-blue);
  display: flex;
  flex-direction: row;
  place-items: center;
  justify-content: space-between;
  max-height: 125px;
  transition: opacity 0.5s ease;
  opacity: 1;
  width: 100%;
  z-index: 3;
}

.header.is-scrolled {
  opacity: 0.8;
}

.logo {
  color: var(--vt-c-white-mute);
}

.logo a {
  color: var(--vt-c-white-mute);
}

.logo h1{
  font-size: 60px;
}

.logo img {
  height: 10rem;
}

.links {
  display: flex;
  padding-right: 5%;
}

.link-pair{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.icon{
  width: 2em;
  margin-right: 1.7em;
  filter: invert(100%) sepia(0) saturate(0) hue-rotate(0deg);
}

.icon:hover {
  cursor: pointer;
}

.links a {
  color: var(--vt-c-white-soft);
  text-decoration: none;
  border-radius: 15px;
  margin-right: 25px;
  font-size: 20px;
}

.links a:hover {
  color: #f8fafc;
}

@media (max-width: 768px) {
  .links a {
    font-size: 1.2rem;
  }
  .logo h1{
    font-size: 30px;
  }
}
</style>
