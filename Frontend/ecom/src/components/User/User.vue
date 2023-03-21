<script setup>
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router';

let user = defineProps({
    ID: Number
});

let email = ref("email@email.com-" + user.ID);
let username = ref("Username-" + user.ID);
let firstname = ref("Firstname-" + user.ID);
let lastname = ref("Lastname-" + user.ID);

let favorites = ref([1,2,3,4,5,6,7,8,9,10]);
let myItems = ref([10,9,8,7,6,5,4,3,2,1]);
let favOrMyBool = ref(true);

function favOrMy(key) {
    switch (key) {
        case 0:
            favOrMyBool.value = true;
            console.log("case0" + favOrMyBool.value);
            break;
        case 1:
            favOrMyBool.value = false;
            console.log("case1" + favOrMyBool.value);
            break;
        default:
            throw new Error('Something went wrong');
    }
}


const itemsToDisplay = computed(() => {
    if (favOrMyBool.value) {
        return favorites.value;
    } else {
        return myItems.value;
    }
});

</script>

<template>
    <div class="container">
        <div class="user">
            <img src="../../assets/person-fill.svg" id="userImg" alt="Person">
            <h1>{{ username }}</h1>
            <div class="userFields">
                <h3>{{ firstname }} {{ lastname }}</h3>
                <hr>
                <h3>{{ email }}</h3>
                <hr>
            </div>
            <br>
            <div class="userButtons">
                <RouterLink to="/edit"><button id="edit">Edit</button></RouterLink>
                <button id="logOut">Log Out</button>
            </div>
        </div>
        <div class="contentWrapper">
            <nav>
                <a href="#0" @click="favOrMy(0)">Favorites</a>
                <a href="#0" @click="favOrMy(1)">My Items</a>
            </nav>
            <div class="content">
                <div class="favoritesWrapper" :hidden="!favOrMyBool" v-for="favorite in itemsToDisplay">
                    <div class="item">{{ favorite }}</div>
                </div>
                <div class="myItemsWrapper" :hidden="favOrMyBool" v-for="item in itemsToDisplay">
                    <div class="item">{{ item }}</div>
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