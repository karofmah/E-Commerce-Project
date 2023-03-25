import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import EditView from '../views/EditView.vue';
import NewItemView from '../views/NewItemView.vue';
import ItemView from '../views/ItemView.vue';
import CartView from '../views/CartView.vue';
import UserView from '../views/UserView.vue';
import UpdateItemView from '../views/UpdateItemView.vue';
import MessageView from '../views/MessageView.vue';
import AdminView from '../views/AdminView.vue'
import PurchaseView from '../views/PurchaseView.vue'
import { useTokenStore } from '../stores/userToken';
import axios from 'axios';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
  },
  {
    path: '/item/:id?',
    name: 'Item',
    component: ItemView,
  },
  {
    path: '/item-favorite/:item_id?',
    name: 'ItemFavorite',
    component: ItemView,
  },
  {
    path: '/cart',
    name: 'Cart',
    component: CartView,
  },
  {
    path: '/purchase',
    name: 'Purchase',
    component: PurchaseView,
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView,
  },
  {
    path: '/edit',
    name: 'Edit',
    component: EditView,
  },
  {
    path: '/newItem',
    name: 'NewItem',
    component: NewItemView,
  },
  {
    path: '/userInfo',
    name: 'UserInfo',
    component: UserView,
  },
  {
    path: '/message',
    name: 'Message',
    component: MessageView
  },
  {
    path: '/update-item/:id',
    name: 'UpdateItem',
    component: UpdateItemView,
    beforeEnter: async (to, from, next) => {
      const tokenStore = useTokenStore();

      if (!tokenStore.loggedInUser) {
        next({ name: 'Home' });
        return;
      }

      const itemId = to.params.id;
      const loggedInUserEmail = tokenStore.loggedInUser.email;

      try {
        const response = await axios.get(`http://localhost:9090/api/items/${itemId}`);

        if (response.status === 200) {
          const itemSellerEmail = response.data.seller.email;

          if (loggedInUserEmail === itemSellerEmail) {
            next();
          } else {
            next({ name: 'Home' });
          }
        } else {
          next({ name: 'Home' });
        }
      } catch (error) {
        console.error('Error checking item ownership:', error);
        next({ name: 'Home' });
      }
    },
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminView,
    beforeEnter: async (to, from, next) => {
      const tokenStore = useTokenStore();
  
      if (!tokenStore.loggedInUser) {
        next({ name: 'Home' });
        return;
      }
  
      const loggedInUserRole = tokenStore.loggedInUser.role;
  
      if (loggedInUserRole === 'ADMINISTRATOR') {
        next();
      } else {
        next({ name: 'Home' });
      }
    },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;