<script>
import { useLanguageStore } from '../stores/languageStore.js'; 

export default {
  data() {
    return {
      selectedLanguage: this.$i18n.locale,
            windowWidth: window.innerWidth,
    };
  },
  created() {
    const languageStore = useLanguageStore();
    languageStore.loadLanguageFromSession();
    this.selectedLanguage = languageStore.selectedLanguage;
    this.$i18n.locale = this.selectedLanguage;
  },
  methods: {
    changeLanguage() {
      const languageStore = useLanguageStore();
      languageStore.changeLanguage(this.selectedLanguage);
      this.$i18n.locale = this.selectedLanguage;
    },
    },
    mounted() {
        window.onresize = () => {
            this.windowWidth = window.innerWidth;
        };
  },
};
</script>

<template>
     <div class="container">
        <div class="logo" v-if="windowWidth > 768">
            <img src="../assets/logo.png" alt="Logo">
        </div>
        <div class="copyright">
            <div>{{ $t("logoCreatedBy") }} <a href="https://www.designevo.com/" title="Free Online Logo Maker">DesignEvo logo maker</a></div>
        </div>
        <div class="selectContainer">
            <select v-model="selectedLanguage" @change="changeLanguage()">
                <option disabled value="">{{ $t("selectLanguage") }}</option>
                <option value="en" selected>English</option>
                <option value="nb">Norsk</option>
                <option value="es">Español</option>
                <option value="de">German</option>
                <option value="fr">French</option>
            </select>
        </div>
    </div>
</template>


<style scoped>
.container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: fit-content;
    padding: 1em;
    background-color: var(--color-blue);
}

.copyright{
    display: flex;
    color: white;
    font-size: 18px;
    width: 33%;
    height: 57%;
    display: flex;
    justify-content: center; 
    align-items: center;
}

.logo{
    width: 33%;
    display: flex; 
    height: 100%;
    margin-top: 1em;
    justify-content: center; 
    align-items: flex-start;
    flex-direction: column;
}

.logo img{
    height: 8rem;
}

.selectContainer{
    display: flex;
    justify-content: end;
    width: 33%;
}

select{
    width: 10em;
}

@media (max-width: 768px) {
    .logo img{
        height: 8rem;
    }

    .copyright{
        font-size: 13px;
    }
    .selectContainer{
        font-size: 13px;
    }
}
</style>
