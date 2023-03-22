<script setup>
import { ref, computed } from 'vue'

let contacts = ref([1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
let currentChat = ref(0)
let chat = ref([])

chat.value = [
  [
    ['Hello', 0],
    ['Hi', 1],
    ["I'm interested in your product.", 0],
    ['Great! What would you like to know?', 1],
    ["What's the price?", 0],
    ["It's non-negotiable.", 1]
  ],
  [
    ['Hi there!', 1],
    ['Hello!', 0],
    ['I saw your product listing online.', 1],
    ['Yes, how can I help you?', 0],
    ['Is it still available?', 1],
    ['Yes, it is.', 0]
  ],
  [
    ['Hey!', 0],
    ['Hello!', 1],
    ['About your product, can you tell me more?', 0],
    ["Sure! It's a high-quality item with great features.", 1],
    ['How long is the warranty?', 0],
    ['It comes with a 2-year warranty.', 1]
  ],
  [
    ['Hello there', 1],
    ['Hi', 0],
    ['I saw your advertisement.', 1],
    ['Great! Do you have any questions?', 0],
    ['Can you explain the product features?', 1],
    ['Certainly! It has a powerful processor and a long-lasting battery.', 0],
    ['What about the camera?', 1],
    ['It has a 12MP camera.', 0]
  ],
  [
    ['Hello', 0],
    ['Hi', 1],
    ['I found your product online.', 0],
    ['Awesome! How can I help you?', 1],
    ['Is it compatible with other devices?', 0],
    ['Yes, it is compatible with a wide range of devices.', 1]
  ],
  [
    ['Hey', 1],
    ['Hello', 0],
    ["I'm looking for more information about your product.", 1],
    ["Of course! It's an excellent choice for your needs.", 0],
    ['What are the dimensions?', 1],
    ['It measures 5.5 x 3.5 x 2 inches.', 0]
  ],
  [
    ['Hi', 0],
    ['Hello', 1],
    ['Can I get a discount on your product?', 0],
    ['Sorry, the price is non-negotiable.', 1]
  ],
  [
    ['Hello', 1],
    ['Hi', 0],
    ["I'd like to know more about your product.", 1],
    ["Sure, it's a top-of-the-line device with great performance.", 0],
    ["What's the storage capacity?", 1],
    ['It comes with 128GB of storage.', 0],
    ['Is it expandable?', 1],
    ['Yes, you can expand it up to 512GB with a microSD card.', 0]
  ],
  [
    ['Hey', 0],
    ['Hello', 1],
    ['I have a question about your product.', 0],
    ["Sure, what's your question?", 1],
    ['Is there any damage on it. The picture is abit unclear', 0],
    ['No there is no damage', 1],
    ['You are free to check it out before buying', 1],
    ['Thanks i will do that', 0]
  ]
]

function openChat(contactValue){
    const index = contacts.value.findIndex(c => c === contactValue);
    currentChat.value = index;
}

let searchText = ref('')

const filteredContacts = computed(() => {
  if (!searchText.value) {
    return contacts.value
  }

  return contacts.value.filter((contact) => {
    return `Contact${contact - 1} name`.toLowerCase().includes(searchText.value.toLowerCase())
  })
})
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
                <h2>Contact{{ contact - 1 }} name</h2>
                <h3>Last message between you and contact{{ contact }}</h3>
            </div>
        </div>
    </div>
    </div>

    <div class="chat">
      <div class="header">
        <img src="../assets/person-fill.svg" alt="Person img" />
        <h1>Contact{{ currentChat }} name</h1>
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
  background: url('../assets/search.svg') no-repeat left;
  background-size: 20px;
  color: var(--color-blue);
  padding-left: 2em;
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
  background-color: var(--blue-complementary);
}

.chat img {
  height: 2em;
  margin: 0.5em 0 0.5em 0.5em;
  border-radius: 50px;
  background-color: var(--blue-complementary);
}

.chat {
  height: 100%;
  flex: 1;
  padding: 2em;
  border-radius: 0 15px 15px 0;
  box-shadow: 2px 5px 15px 4px rgba(0, 0, 0, 0.2);
}

.chatInstances {
  display: flex;
  flex-direction: column;
  line-height: 1.25;
  padding: 0.5rem 0.875rem;
  position: relative;
  word-wrap: break-word;
  height: 95%;
  overflow-y: auto;
}

#chatInstance {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  width: fit-content;
  max-width: 55%;
  min-height: fit-content;
  padding: 0 2em;
  border-radius: 50px;
  margin-top: 1em;
}

#chatInstance::before,
#chatInstance::after {
  bottom: -0.1rem;
  content: '';
  height: 1rem;
  position: absolute;
}

.chatSend {
  margin-left: auto;
  background-color: var(--color-blue-light);
  color: #fff;
}

.chatSend::before {
  border-bottom-left-radius: 0.8rem 0.7rem;
  border-right: 1rem solid var(--color-blue-light);
  right: -0.35rem;
  transform: translate(0, -0.1rem);
}

.chatSend::after {
  background-color: #fff;
  border-bottom-left-radius: 0.5rem;
  right: -38.1px;
  transform: translate(-30px, -2px);
  width: 10px;
}

.chatSend ~ .chatSend:last-child {
  margin-bottom: 0.5rem;
}

.chatReceive {
  margin-right: auto;
}

.chatReceive {
  background-color: #e5e5ea;
  color: #000;
}

.chatReceive:before {
  border-bottom-right-radius: 0.8rem 0.7rem;
  border-left: 1rem solid #e5e5ea;
  left: -0.35rem;
  transform: translate(0, -0.1rem);
}

.chatReceive::after {
  background-color: #fff;
  border-bottom-right-radius: 0.5rem;
  left: 20px;
  transform: translate(-30px, -2px);
  width: 10px;
}
</style>
