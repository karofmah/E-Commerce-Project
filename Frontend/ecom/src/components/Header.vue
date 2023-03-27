<template>
  <div class="header" :class="{ 'is-scrolled': scrollPosition > 125 }">
    <div class="logo">
      <a @click="changeRoute('Home')" href=""><img src="../assets/logo.png" alt="logo"></a>
      <img src="../assets/list.svg" alt="menu" class="menyImg" v-if="windowWidth <= 768" @click="linksVisibility()">
    </div>
    <div class="links" v-if="windowWidth > 768">
      <div class="link-pair">
        <img @click="handleItemClick('NewItem')" src="../assets/bag-plus.svg" class="icon" alt="New Ad">
        <a @click="handleItemClick('NewItem')" href="">{{ $t("addNew") }}</a>
      </div>

      <div class="link-pair">
        <img @click="handleItemClick('Message')" src="../assets/chat-left-dots.svg" class="icon" alt="Message">
        <a @click="handleItemClick('Message')" href="">{{ $t("messages") }}</a>
      </div>

      <div class="link-pair">
        <img @click="handleItemClick('Cart')" src="../assets/cart3.svg" class="icon" alt="Cart">
        <a @click="handleItemClick('Cart')" href="">{{ $t("cart") }}</a>
      </div>

      <div class="link-pair">
        <img @click="changeRoute('UserInfo')" src="../assets/person-fill.svg" class="icon" alt="Log in">
        <template v-if="tokenStore.jwtToken && tokenStore.loggedInUser">
          <a @click="changeRoute('UserInfo')" href="">{{ tokenStore.loggedInUser.firstName }}</a>
        </template>
        <template v-else>
          <a @click="changeRoute('Login')" href="">{{ $t("logIn") }}</a>
        </template>
      </div>

      <div v-if="tokenStore.loggedInUser && tokenStore.loggedInUser.role === 'ADMINISTRATOR'" class="link-pair">
        <img @click="changeRoute('admin')" src="../assets/person-fill.svg" class="icon" alt="Admin">
        <a @click="changeRoute('admin')" href="">Admin</a>
      </div>
    </div>
    <div class="links" v-if="links">
      <div class="link-pair">
        <img @click="handleItemClick('NewItem')" src="../assets/bag-plus.svg" class="icon" alt="New Ad">
        <a @click="handleItemClick('NewItem')" href="">{{ $t("addNew") }}</a>
      </div>

      <div class="link-pair">
        <img @click="handleItemClick('Message')" src="../assets/chat-left-dots.svg" class="icon" alt="Message">
        <a @click="handleItemClick('Message')" href="">{{ $t("messages") }}</a>
      </div>

      <div class="link-pair">
        <img @click="handleItemClick('Cart')" src="../assets/cart3.svg" class="icon" alt="Cart">
        <a @click="handleItemClick('Cart')" href="">{{ $t("cart") }}</a>
      </div>

      <div class="link-pair">
        <img @click="changeRoute('UserInfo')" src="../assets/person-fill.svg" class="icon" alt="Log in">
        <template v-if="tokenStore.jwtToken && tokenStore.loggedInUser">
          <a @click="changeRoute('UserInfo')" href="">{{ tokenStore.loggedInUser.firstName }}</a>
        </template>
        <template v-else>
          <a @click="changeRoute('Login')" href="">{{ $t("logIn") }}</a>
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
      scrollPosition: 0,
      windowWidth: window.innerWidth,
      links: false,
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
    linksVisibility(){
      this.links = !this.links
    }
  },
  async mounted() {
    window.addEventListener("scroll", this.updateScroll);
  },
  mounted() {
    window.onresize = () => {
      this.windowWidth = window.innerWidth
    }
  }
};
</script>


<style scoped>
.header {
  position: sticky;
  top: 0;
  background: var(--color-blue);
  display: flex;
  flex-flow: row wrap;
  place-items: center;
  justify-content: space-between;
  height: fit-content;
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

select{
  margin-right: 1em;
  outline: none;
}

select option:hover{
  background: var(--color-blue);
}

.links {
  display: flex;
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
  .header {
    flex-direction: row;
    padding: 5px;
  }

  .logo{
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
  }

  .logo img {
    height: 6rem;
  }

  .menyImg{
    width: 3.5em;
  }

  .links {
    height: fit-content;
    flex-direction: row;
    align-items: center;
    justify-content: space-around;
    width: 100%;
    margin: auto;
  }

  .link-pair {
    margin-bottom: 10px;
  }

  .icon {
    width: 2.5em;
    margin-right: 1em;
  }

  .links a {
    font-size: 1.6em;
    margin-right: 10px;
  }
}
</style>
