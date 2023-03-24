<script setup>
import { ref, onMounted } from 'vue'

let items = ref([
  ['Item1', 100, 1],
  ['Item2', 200, 2],
  ['Item3', 300, 3],
  ['Item4', 400, 4],
  ['Item5', 500, 5],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
  ['Item6', 600, 6],
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
      <h1 id="totalPrice">{{ totalPrice }}</h1>
      <button>Pay</button>
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
