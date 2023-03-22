import { defineStore } from 'pinia';

export const useCategoryStore = defineStore('categoryStore', {
  state: () => {
    const storedCategory = sessionStorage.getItem('selectedCategory');
    return {
      selectedCategory: storedCategory ? JSON.parse(storedCategory) : null,
    };
  },
  actions: {
    selectCategory(category) {
      this.selectedCategory = category;
      sessionStorage.setItem('selectedCategory', JSON.stringify(category));
    },
  },
})