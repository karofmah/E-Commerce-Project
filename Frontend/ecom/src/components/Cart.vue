<script setup>
import { ref, onMounted } from 'vue'

let items = ref([
  ['Item1', 100, 1],
  ['Item2', 200, 2],
  ['Item3', 300, 3],
  ['Item4', 400, 4],
  ['Item5', 500, 5],
  ['Item6', 600, 6],

])

let totalPrice = ref(0)
function calcTotalPrice() {
  totalPrice.value = 0;
  for (let i = 0; i < items.value.length; i++) {
    let item = items.value[i]
    if (item && item[1] && item[2]) {
      totalPrice.value += item[1] * item[2]
    } else {
      console.error(`Invalid item: ${item}`)
    }
  }
}

onMounted(() => {
  calcTotalPrice()
})
</script>

<template>
  <div class="container">
    <div class="cart">
      <h1>Shopping Cart</h1>
      <br>
      <div v-for="item in items">
        <h2>Product: {{ item[0] }}, Price: {{ item[1] }}, Amount: {{ item[2] }}</h2>
        <hr>
      </div>
      <br>
    </div>
    <div class="pay">
      <h1>Check Out</h1>
      <h2>Total Price:</h2>
      <h1>{{ totalPrice }}</h1>
      <button @click="calcTotalPrice()">Pay</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  align-items: center;
  margin: 3em auto;
  width: 85%;
  min-height: 32em;
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
}

.pay {
  position: fixed;
  height: 25em;
  margin: 48em;
  width: 30em;
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
