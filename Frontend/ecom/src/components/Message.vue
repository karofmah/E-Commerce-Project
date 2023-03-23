<script setup>
import { ref, computed, onUpdated, nextTick, onMounted } from 'vue'
import axios from 'axios';
import { useTokenStore } from "../stores/userToken";

let contacts = ref([])
let currentChat = ref(0)
let chat = ref([])
let chatInput = ref("")
const tokenStore = useTokenStore();



function openChat(contactValue){
    const index = contacts.value.findIndex(c => c === contactValue)
    currentChat.value = index
		chatInput.value = ""
}

let searchText = ref('')

const filteredContacts = computed(() => {
  if (!searchText.value) {
    return contacts.value
  }

  return contacts.value.filter((contact) => {
    return `Contact${contact} name`.toLowerCase().includes(searchText.value.toLowerCase())
  })
})

function getLastMessage(contact){
	//let lastMessage = chat.value[contact][chat.value[contact].length - 1][0]
	//return (lastMessage.length > 35) ? lastMessage.substring(0, 35) + "..." : lastMessage;
  return "last message";
}

async function addChat(input) {
	if (input != "") {
    let message = {
      "toEmail": contacts.value[currentChat.value].email,
      "fromEmail": tokenStore.loggedInUser.email,
      "messageContent": input
    } 
    await axios.post("http://localhost:9090/api/messages/sendMessage", message)

		chat.value[currentChat.value].push([input, 0])
		chatInput.value = ""
		scrollToBottom()
	}
}

function scrollToBottom() {
  nextTick(() => {
    let chatInstances = document.querySelector(".chatInstances");
    let lastChatInstance = chatInstances.lastElementChild;
      
    if (lastChatInstance) {
      lastChatInstance.scrollIntoView({ behavior: "smooth", block: "end" });
    }
  });
}

async function getContacts() {
  contacts.value = (await axios.get("http://localhost:9090/api/messages/" + tokenStore.loggedInUser.email + "/contacts")).data

  console.log(contacts.value)
}

async function getMessages() {
  
  console.log("Getting messages...")
  console.log(contacts.value.length)

  chat.value = []
  for (let i = 0; i < contacts.value.length; i++) {

    console.log(i)

    chat.value.push([])
    console.log('http://localhost:9090/api/messages/' + tokenStore.loggedInUser.email + "/" + contacts.value[i].email);
    const response = await axios.get('http://localhost:9090/api/messages/' + tokenStore.loggedInUser.email + "/" + contacts.value[i].email);


    console.log(response.data)
    let tempMessages = response.data
    
    for (const message in tempMessages) {
  
      let content = tempMessages[message].messageContent
      let sender = 1
      if (tempMessages[message].fromEmail === tokenStore.loggedInUser.email) {
        sender = 0
      }
  
      console.log(content, sender)
  
      chat.value[i].push([content, sender])
    }
  }
}

onUpdated(() => {
	scrollToBottom()
})

onMounted(() => {
  initialize()
})

async function initialize() {
    await getContacts()
    await getMessages()

    console.log(chat.value)
}

</script>

<template>
  <div class="container">
    <div class="chats">
      <div class="header">
        <img src="../assets/chat-dots-fill.svg" id="chatLogo" alt="Chat" />
        <h1>Chats</h1>
      </div>
      <hr />
      <input type="text" id="search" placeholder="Search" v-model="searchText" />
      <div class="contacts">
        <div class="contact" v-for="contact in filteredContacts" @click="openChat(contact)">
            <img src="../assets/person-fill.svg" alt="Person img">
            <div>
                <h2> {{ contact.firstName + " " + contact.lastName }} </h2>
                <h3>{{ getLastMessage(contact) }}</h3>
            </div>
        </div>
    </div>
    </div>

    <div class="chat">
      <div class="header">
        <img src="../assets/person-fill.svg" alt="Person img" />
        <h1 v-if="contacts[currentChat]"> {{ contacts[currentChat].firstName + " " + contacts[currentChat].lastName }} </h1>
      </div>
      <hr />
      <div class="chatInstances">
        <div
          id="chatInstance"
          v-for="chatInstance in chat[currentChat]"
          :class="{ chatSend: chatInstance[1] === 0, chatReceive: chatInstance[1] === 1 }"
        >
          {{ chatInstance[0] }}
        </div>
      </div>
	  <input type="text" id="chatInput" placeholder="Send a Chat" autocomplete="off" v-model="chatInput" @keydown.enter="addChat(chatInput)"/>
    </div>
  </div>
</template>

<style>
.container {
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  margin: auto;
  align-items: center;
  height: 85vh;
  width: 90%;
  padding: 1em;
}

.chats {
  height: 100%;
  width: 50%;
  padding: 2em;
  border-radius: 15px 0 0 15px;
  box-shadow: 2px 5px 15px 4px rgba(0, 0, 0, 0.2);
}

.header {
  display: flex;
  justify-content: start;
  align-items: center;
}

#search {
  background: url('../assets/search.svg') no-repeat 1.5% 50%;
  background-size: 20px;
  color: var(--color-blue);
  padding-left: 2.5em;
  width: 93%;
  margin: 1em;
}

#search::-webkit-input-placeholder {
  color: var(--color-blue);
}

#chatLogo {
  height: 2em;
}

.contacts {
  overflow-y: scroll;
  height: 75%;
}

.contacts::-webkit-scrollbar {
  display: none;
}

.contact {
  display: flex;
  flex-flow: row wrap;
  gap: 1em;
  width: 99%;
  align-items: center;
  margin-top: 1em;
  border-radius: 15px;
  border: 2px solid transparent;
  box-shadow: 2px 3px 4px 2px rgba(0, 0, 0, 0.2);
}

.contact:hover {
  border: 2px solid var(--color-blue-light);
  cursor: pointer;
}

.contact img {
  height: 4em;
  margin: 0.5em 0 0.5em 0.5em;
  border-radius: 50px;
  background-color: var(--color-blue-light);
}

.chat img {
  height: 2em;
  margin: 0.5em 0 0.5em 0.5em;
  border-radius: 50px;
  background-color: var(--color-blue-light);
}

.chat {
	display: flex;
	flex-direction: column;
	width: 100%;
  height: 100%;
  flex: 1;
  justify-content: space-between;
  padding: 2em;
  border-radius: 0 15px 15px 0;
  box-shadow: 2px 5px 15px 4px rgba(0, 0, 0, 0.2);
	overflow: hidden;
}

.chatInstances {
  display: flex;
  flex-direction: column;
	justify-content: start;
  line-height: 1;
  padding: 0.5rem 0.875rem;
  position: relative;
  word-wrap: break-word;
	height: 100%;
  overflow-y: auto;
}

#chatInstance {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: fit-content;
  width: fit-content;
  max-width: 55%;
  padding: .7em 2em;
	font-size: medium;
  border-radius: 50px;
  margin-top: 1em;
}

.chatSend {
  margin-left: auto;
  background-color: var(--color-blue-light);
  color: #fff;
}

/* .chatSend ~ .chatSend:last-child {
  margin-bottom: .5em;
} */

.chatReceive {
  margin-right: auto;
	background-color: #e5e5ea;
	color: #000;
}

#chatInput {
  background: url('../assets/send.svg') no-repeat 97% 55%;
  background-size: 20px;
  color: var(--color-blue);
	margin-top: 1em;
  padding-left: 2em;
  width: 100%;
	animation: 1s linear;
}

#chatInput::-webkit-input-placeholder {
  color: var(--color-blue);
}
</style>
