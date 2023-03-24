import { defineStore } from 'pinia';

export const useCartStore = defineStore({
  id: 'cart',
  state: () => {
    const storedState = sessionStorage.getItem('cartState');
    return storedState
      ? JSON.parse(storedState)
      : { items: [] };
  },
  actions: {
    addItem(item) {
      this.items.push(item);
      this._saveState();
    },
    removeItem(itemId) {
      this.items = this.items.filter(item => item.id !== itemId);
      this._saveState();
    },
    clearItems() {
      this.items = [];
      this._saveState();
    },
    logOut() {
      this.items = [];
      this._saveState();
      sessionStorage.removeItem('cartState');
    },
    _saveState() {
      sessionStorage.setItem('cartState', JSON.stringify(this.$state));
    }
  }
});
