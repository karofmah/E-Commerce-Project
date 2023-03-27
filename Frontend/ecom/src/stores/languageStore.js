import { defineStore } from 'pinia';

export const useLanguageStore = defineStore('language', {
  state: () => ({
    selectedLanguage: window.navigator.language.split("-")[0],
  }),
  actions: {
    changeLanguage(newLanguage) {
      this.selectedLanguage = newLanguage;
      sessionStorage.setItem('selectedLanguage', newLanguage);
    },
    loadLanguageFromSession() {
      const languageFromSession = sessionStorage.getItem('selectedLanguage');
      if (languageFromSession) {
        this.selectedLanguage = languageFromSession;
      }
    },
  },
});
