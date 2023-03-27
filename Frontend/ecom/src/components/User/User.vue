<script setup>
import { ref, computed, onMounted } from 'vue'
import { RouterLink, useRouter } from 'vue-router';
import { useTokenStore } from '../../stores/userToken';
import { useCartStore } from '../../stores/cartStore';
import axios from 'axios';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();

const tokenStore = useTokenStore()
const cartStore = useCartStore();
let router = useRouter();

let favorites = ref([]);
let myItems = ref([]);
let favOrMyBool = ref(true);

async function getMyItems() {
  const userEmail = tokenStore.loggedInUser.email;

  const config = {
    headers: {
      "Content-type": "application/json",
      Authorization: "Bearer " + tokenStore.jwtToken,
    },
  };

  myItems.value = await axios.get("http://localhost:9090/api/items?email="+ userEmail,config).then(res => res.data);
}

async function getMyFavorites() {
    const config = {
        headers: {
            "Content-type": "application/json",
            Authorization: "Bearer " + tokenStore.jwtToken,
        },
    };

    const userEmail = tokenStore.loggedInUser.email;

    favorites.value = await axios.get("http://localhost:9090/api/bookmark/get?email=" + userEmail, config).then(res => res.data);
}

function favOrMy(key) {
    switch (key) {
        case 0:
            favOrMyBool.value = true;
            break;
        case 1:
            favOrMyBool.value = false;
            break;
        default:
            throw new Error('Something went wrong');
    }
}

function logOut() {
  tokenStore.logOut();
  cartStore.logOut();
  router.push("Login");
}

function handleItemClick(itemId) {
  router.push({ name: 'Item', params: { id: itemId } });
}

function handleFavoriteItemClick(item_id) {
  router.push({ name: 'ItemFavorite', params: { item_id } });
}

const favoritesToDisplay = computed(() => {
  if (favOrMyBool.value) {
    return favorites.value;
  } else {
    return [];
  }
});

const myItemsToDisplay = computed(() => {
  if (!favOrMyBool.value) {
    return myItems.value;
  } else {
    return [];
  }
});

onMounted(async () => {
  await getMyItems();
  await getMyFavorites();
});
</script>

<template>
    <div class="container">
      <div class="user">
        <img src="../../assets/person-fill.svg" id="userImg" alt="Person">
        <h2>{{ tokenStore.loggedInUser ? tokenStore.loggedInUser.username : '' }}</h2>
        <div class="userFields">
          <h3>{{ t("firstName") + ": " + (tokenStore.loggedInUser ? tokenStore.loggedInUser.firstName : '') }}</h3>
          <hr>
          <h3>{{ t("lastName") + ": " + (tokenStore.loggedInUser ? tokenStore.loggedInUser.lastName : '') }}</h3>
          <hr>
          <h3>{{ t("email") + ": " + (tokenStore.loggedInUser ? tokenStore.loggedInUser.email : '') }}</h3>
        </div>
        <br>
        <div class="userButtons">
          <RouterLink to="/edit"><button id="edit">{{ t("edit") }}</button></RouterLink>
          <button @click="logOut()" id="logOut">{{ t("logOut") }}</button>
        </div>
      </div>
      <div class="contentWrapper">
        <nav>
          <a href="#0" @click="favOrMy(0)">{{ t("favorites") }}</a>
          <a href="#0" @click="favOrMy(1)">{{ t("myItems") }}</a>
        </nav>
        <div class="content">
          <div class="favoritesWrapper" :hidden="!favOrMyBool" v-for="favorite in favoritesToDisplay" :key="favorite.id">
            <div class="item" @click="handleItemClick(favorite.item.id)">
              <img :src="favorite.item.images?.[0]" alt="Item image" />
              <h3>{{ (favorite.item.briefDescription > 35) ? favorite.item.briefDescription.substring(0,33) + ".." : favorite.item.briefDescription}}</h3>
            </div>
          </div>
          <div class="myItemsWrapper" :hidden="favOrMyBool" v-for="item in myItemsToDisplay" :key="item.id">
            <div class="item" @click="handleItemClick(item.id)">
              <img :src="item.images?.[0]" alt="Item image" />
              <h3>{{ (item.briefDescription > 35) ? item.briefDescription.substring(0,33) + ".." : item.briefDescription }}</h3>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
 
  
<style scoped>
    .container {
        display: flex;
        flex-flow: row wrap;
        align-items: start;
        width: 90%;
        height: fit-content;
        margin: 3em auto;
    }

    .user {
        position: relative;
        display: flex;
        flex-flow: column wrap;
        align-items: center;
        background-color: var(--color-blue);
        color: var(--vt-c-white-mute);
        height: fit-content;
        width: 40%;
        margin: 0 3em 3em 0em;
        padding: 1em 3em;
        box-shadow: 2px 5px 10px 2px rgba(0, 0, 0, 0.2);
        border-radius: 15px;
    }

    .contentWrapper {
        min-height: 35em;
        min-width: 40em;
        flex: 1;
        /* margin: 0 38em; */
        padding: 1em 3em;
        box-shadow: 2px 5px 10px 2px rgba(0, 0, 0, 0.2);
        border-radius: 15px;
    }

    .favoritesWrapper,
    .myItemsWrapper {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        gap: 20px;
        margin: 20px 0;
        padding: 0 .5em;
    }

    .item {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100%;
        /* min-width: calc(33.33% - 20px); */
        background-color: var(--color-blue-light);
        border-radius: 15px;
    }

    .item img {
        width: 170px;
        height: 170px;
        border-radius: 15px 15px 0px 0px;
        object-fit: cover;
        object-position: center;
    }

    .item:hover {
        cursor: pointer;
    }

    #userImg {
        height: 12em;
        width: 12em;
        padding: 5px;
        background-color: var(--blue-complementary);
        border-radius: 50%;
        margin: 1em;
    }

    .userFields {
        display: flex;
        flex-flow: column wrap;
        align-items: start;
        width: 100%;
    }

    .userButtons {
        display: flex;
        justify-content: space-evenly;
        width: 60%;
        margin: 1em;
    }

    #edit,
    #logOut {
        border-radius: 50px;
    }

    #logOut {
        background-color: var(--blue-complementary);
    }

    nav {
        display: flex;
        flex-direction: row;
    }

    .content {
        display: flex;
        flex-flow: row wrap;
        box-shadow: 2px 5px 10px 2px rgba(0, 0, 0, 0.2) inset;
        border-radius: 15px;
    }

    hr {
        width: 90%;
    }

    h3 {
        line-height: 3;
    }


    @media(max-width: 768px){
        .container{
            flex-direction: column;
        }

        .user{
            width: 100%;
        }

        .contentWrapper{
            width: 100%;
        }

        .favoritesWrapper, .myItemsWrapper{
            margin: 1em auto;
        }

        .item{
            width: 100%;
            margin: 0 3em;
        }
    }
</style>