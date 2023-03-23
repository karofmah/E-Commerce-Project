<script setup>
import { ref, computed, onMounted } from 'vue'
import { RouterLink, useRouter } from 'vue-router';
import { useTokenStore } from '../../stores/userToken';
import axios from 'axios';

const tokenStore = useTokenStore()
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
  console.log(myItems.value)
}

async function getMyFavorites(){
    const userEmail = tokenStore.loggedInUser.email;

    const config = {
    headers: {
      "Content-type": "application/json",
      Authorization: "Bearer " + tokenStore.jwtToken,
    },
  };

  const user = {
            username: tokenStore.loggedInUser.username,
            password: tokenStore.loggedInUser.password,
            email: tokenStore.loggedInUser.email,
            firstName: tokenStore.loggedInUser.firstName,
            lastName: tokenStore.loggedInUser.lastName,
            role: tokenStore.loggedInUser.role
        }

  favorites.value = await axios.get("http://localhost:9090/api/bookmark/get")

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
  router.push("Login");
}

function handleItemClick(itemId) {
  router.push({ name: 'Item', params: { id: itemId } });
}

const itemsToDisplay = computed(() => {
    if (favOrMyBool.value) {
        return favorites.value;
    } else {
        return myItems.value;
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
            <h1>{{ tokenStore.loggedInUser.email }}</h1>
            <div class="userFields">
                <h3>{{ "Fornavn: " +  tokenStore.loggedInUser.firstName }}</h3>
                <hr>
                <h3>{{ "Etternavn: " + tokenStore.loggedInUser.lastName }}</h3>
                <hr>
            </div>
            <br>
            <div class="userButtons">
                <RouterLink to="/edit"><button id="edit">Edit</button></RouterLink>
                <button @click="logOut()" id="logOut">Log Out</button>
            </div>
        </div>
        <div class="contentWrapper">
            <nav>
                <a href="#0" @click="favOrMy(0)">Favorites</a>
                <a href="#0" @click="favOrMy(1)">My Items</a>
            </nav>
            <div class="content">
                <div class="favoritesWrapper" :hidden="!favOrMyBool" v-for="item in itemsToDisplay" :key="item.id">
                <div class="item" @click="handleItemClick(item.id)">
                    <img :src="item.images?.[0]" alt="Item image" />
                    <h3>{{ item.briefDescription }}</h3>
                    <h4>{{ item.price }}</h4>
                </div>
                </div>
                <div class="myItemsWrapper" :hidden="favOrMyBool" v-for="item in itemsToDisplay" :key="item.id">
                <div class="item" @click="handleItemClick(item.id)">
                    <img :src="item.images?.[0]" alt="Item image" />
                    <h3>{{ item.briefDescription }}</h3>
                    <h4>{{ item.price }}</h4>
                </div>
                </div>
            </div>
        </div>
    </div>
</template>


<style scoped>
    .container{
        display: flex;
        flex-flow: row wrap;
        width: 90%;
        min-height: 33rem;
        margin: 3em auto;
    }

    .user{
        position: relative;
        display: flex;
        flex-flow: column wrap;
        align-items: center;
        background-color: var(--color-blue);
        color: var(--vt-c-white-mute);
        height: 35em;
        width: 40%;
        margin: 0 3em 3em 0em;
        padding: 1em 3em;
        box-shadow: 2px 5px 10px 2px rgba(0, 0, 0, 0.2);
        border-radius: 15px;
    }

    .item {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin: 10px;
    height: 6em; /* Adjust this value to make the images smaller */
    width: 6em; /* Adjust this value to make the images smaller */
    background-color: var(--color-blue);
    border-radius: 15px;
    }

    .item img {
    max-height: 100%;
    max-width: 100%;
    }

    .item:hover{
        cursor: pointer;
    }

    #userImg{
        height: 12em;
        width: 12em;
        padding: 5px;
        background-color: var(--blue-complementary);
        border-radius: 50%;
        margin: 1em;
    }

    .userFields{
        display: flex;
        flex-flow: column wrap;
        align-items: start;
        width: 100%;
    }

    .userButtons{
        display: flex;
        justify-content: space-evenly;
        width: 60%;
        margin: 1em;
    }

    #edit, #logOut{
        border-radius: 50px;
    }

    #logOut{
        background-color: var(--blue-complementary);
    }

    .contentWrapper{
        min-height: 30em;
        min-width: 40em;
        flex: 1;
        /* margin: 0 38em; */
        padding: 1em 3em;
        box-shadow: 2px 5px 10px 2px rgba(0, 0, 0, 0.2);
        border-radius: 15px;
    }

    nav{
        display: flex;
        flex-direction: row;
    }

    .content{
        display: flex;
        flex-flow: row wrap;
        box-shadow: 2px 5px 10px 2px rgba(0, 0, 0, 0.2) inset;
        border-radius: 15px;
    }

    .item{
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 3px;
        height: 10em;
        width: 10em;
        background-color: var(--color-blue);
        border-radius: 15px;
    }

    .favoritesWrapper, .myItemsWrapper{
        padding-left: 10px;
    }

    hr {
        width: 90%;
    }

    h3{
        line-height: 3;
    }

    @media(max-width: 768px){
        .user{
            position: relative;
        }
        .contentWrapper{
            min-width: 0em;
            margin: 0;
        }

    }
</style>