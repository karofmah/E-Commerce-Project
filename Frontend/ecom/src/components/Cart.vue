<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router';
import { useTokenStore } from '../stores/userToken';
import { useCartStore } from '../stores/cartStore';
import axios from 'axios';

const cartStore = useCartStore();
const tokenStore = useTokenStore();
const router = useRouter();

const items = computed(() => cartStore.items);

let totalPrice = ref(0)


function calcTotalPrice() {
  totalPrice.value = 0;
  if (items.value && items.value.length > 0) {
    for (let i = 0; i < items.value.length; i++) {
      let item = items.value[i]
      if (item && item.id && item.price) {
        totalPrice.value += item.price
      } else {
        console.error(`Invalid item: ${item}`)
      }
    }
  }
}

async function buyItem(itemId) {
  const itemIndex = items.value.findIndex(item => item.id === itemId);
  const item = items.value[itemIndex];

  // Contact the seller
  if (tokenStore.loggedInUser) {
    const config = {
      headers: {
        "Content-type": "application/json",
        "Authorization" : "Bearer " + tokenStore.jwtToken
      },
    };

    let content = tokenStore.loggedInUser.firstName + " " + tokenStore.loggedInUser.lastName 
    + " har kjøpt " + item.briefDescription + " for " + item.price + "."

    let message = {
      "toEmail": item.seller.email,
      "fromEmail": tokenStore.loggedInUser.email,
      "messageContent": content
    }
  

    await axios.post("http://localhost:9090/api/messages/sendMessage", message, config);

  } else {
    router.push({ name: "Home" });
    return;
  }

  const deleteConfig = {
    headers: {
      "Content-type": "application/json",
      "Authorization" : "Bearer " + tokenStore.jwtToken
    }
  };
  await axios.delete(`http://localhost:9090/api/items/delete/${item.id}`, deleteConfig)
    .then(response => {
      if (response.status === 204) {
        cartStore.removeItem(item.id);
        calcTotalPrice();
      }
    })
    .catch(error => {
      console.log(error);
    });

  router.push({ name: "Home" });
}


function deleteItem(itemId) {
  cartStore.removeItem(itemId);
  calcTotalPrice();
}

onMounted(async () => {
  calcTotalPrice()
})
</script>

<template>
  <div class="container">
    <div class="cart">
      <h1>Shopping Cart</h1>
      <br>
      <div v-for="item in items" :key="item.id">
        <h2>Product: {{ item.briefDescription }}, Price: {{ item.price }}</h2>
        <button @click="deleteItem(item.id)">Delete</button>
        <button @click="buyItem(item.id)">Buy</button>
        <hr>
      </div>
      <br>
    </div>
    <div class="pay">
      <h1>Check Out</h1>
      <h2>Total Price:</h2>
      <h1 id="totalPrice">{{ totalPrice }}</h1>
    </div>
  </div>
</template>




<style scoped>
.container {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  align-items: start;
  margin: 3em auto;
  width: 90%;
  height: fit-content;
}

.cart,
.pay {
  display: flex;
  flex-flow: column wrap;
  justify-content: space-evenly;
  align-items: center;
  min-height: 25em;
  border-radius: 2em;
  box-shadow: 2px 5px 15px 4px rgba(0, 0, 0, 0.2);
}

.cart {
  width: 45em;
  margin-right: 3em;
  float: left;
}

.pay {
  height: 25em;
  /* margin: 48em 48em; */
  background-color: var(--color-blue);
  color: var(--vt-c-white-mute);
  flex: 1;
}

#totalPrice{
  background: linear-gradient(to right, var(--blue-light-complementary), var(--blue-complementary));
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

hr {
  width: 40em;
}

@media(max-width: 768px){
    .pay{
        position: relative;
        margin: 3em;
        margin-left: 5em;
        flex: 1;
    }
}
</style>
