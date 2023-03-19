<template>
    <div class="header" :class="{ 'is-scrolled': scrollPosition > 125 }">
      <div class="logo">
        <h1>LOGO</h1>
      </div>

        <div class="links">
          <a @click="changeRoute('NewItem')" href="">Ny annonse</a>
          <a href="">Meldinger</a>
          <a @click="changeRoute('Login')" href="">Logg inn</a>
        </div>
    </div>
</template>

<script>
export default {
  data() {
    return {
      scrollPosition: 0
    };
  },
  methods: {
    updateScroll() {
      this.scrollPosition = window.scrollY;
    },
    changeRoute(string){
      this.$router.push({name:string})
    },
  },
  mounted() {
    window.addEventListener("scroll", this.updateScroll);
  }
};
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  background: radial-gradient(#f7fee7,#84cc16);
  display: flex;
  flex-direction: row;
  place-items: center;
  justify-content: space-between;
  max-height: 125px;
  transition: opacity 0.5s ease;
  opacity: 1;
  width: 100%;
  height: 100vh;
}

.header.is-scrolled {
  opacity: 0.8;
}

.logo {
    color: #0f172a;
}

.logo h1{
    font-size: 60px;
}

.logo img {
  height: 15rem;
}

.links {
  padding-right: 5%;
}

.links a {
  color: #0f172a;
  text-decoration: none;
  border-radius: 15px;
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  margin-right: 25px;
  font-size: 30px;
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
