<template>
    <div>
      <div class="header">
        <h1>Admin page, press X to delete things</h1>
      </div>
      <div class="container-admin">
        <div class="user-section">
          <h1>Users</h1>
          <ul>
            <li v-for="(user, index) in users" :key="index">
              {{ user.email }} <span class="delete" @click="deleteUser(index)">X</span>
            </li>
          </ul>
        </div>
        <div class="category-section">
          <h1>Categories</h1>
          <ul>
            <li v-for="(category, index) in categories" :key="index">
              {{ category.categoryName }} <span class="delete" @click="deleteCategory(index)">X</span>
            </li>
          </ul>
          <form @submit.prevent="addCategory">
            <input type="text" v-model="newCategory">
            <button type="submit">Add Category</button>
          </form>
        </div>
        <div class="item-section">
          <h1>Items</h1>
          <ul>
            <li v-for="(item, index) in items" :key="index">
              {{ "Seller: " + item.seller.email + " Title: " + item.briefDescription}} <span class="delete" @click="deleteItem(index)">X</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { useTokenStore } from "../stores/userToken";

  export default {
    data() {
      return {
        users: [],
        categories: [],
        items: [],
        newCategory: ''
      }
    },
    setup(){
        const tokenStore = useTokenStore();
        return { tokenStore };
    },
    mounted() {
      this.loadData();
    },
    methods: {
      async loadData() {

            const config = {
            headers: {
                "Content-type": "application/json",
                "Authorization" : "Bearer " + this.tokenStore.jwtToken
            },
        };

        await axios.get('http://localhost:9090/api/users/getAllUsers',config)
        .then(response => {
          this.users = response.data;
          console.log(this.users + "hello")
        })
        .catch(error => {
          console.log(error);
        });
        await axios.get('http://localhost:9090/api/categories/getCategories')
          .then(response => {
            this.categories = response.data;
          })
          .catch(error => {
            console.log(error);
          });
        await axios.get('http://localhost:9090/api/items/getItems')
          .then(response => {
            this.items = response.data;
          })
          .catch(error => {
            console.log(error);
          });
      },
        async deleteUser(index) {
            const user = this.users[index];
            const config = {
                headers: {
                "Content-type": "application/json",
                "Authorization" : "Bearer " + this.tokenStore.jwtToken
                }
            };
            await axios.delete(`http://localhost:9090/api/users/deleteUser/${user.email}`, config)
                .then(response => {
                if (response.status === 204) {
                    this.users.splice(index, 1);
                }
                })
                .catch(error => {
                console.log(error);
                });
        },
        async deleteCategory(index) {
            const category = this.categories[index];
            const config = {
                headers: {
                "Content-type": "application/json",
                "Authorization" : "Bearer " + this.tokenStore.jwtToken
                }
            };
            await axios.delete(`http://localhost:9090/api/categories/deleteCategory/${category.categoryName}`, config)
                .then(response => {
                if (response.status === 204) {
                    this.categories.splice(index, 1);
                }
                })
                .catch(error => {
                console.log(error);
                });
        },
        async deleteItem(index) {
            const item = this.items[index];
            const config = {
                headers: {
                "Content-type": "application/json",
                "Authorization" : "Bearer " + this.tokenStore.jwtToken
                }
            };
            await axios.delete(`http://localhost:9090/api/items/delete/${item.id}`, config)
                .then(response => {
                if (response.status === 204) {
                    this.items.splice(index, 1);
                }
                })
                .catch(error => {
                console.log(error);
                });
        },
        async addCategory() {
            const config = {
            headers: {
            "Content-type": "application/json",
            "Authorization" : "Bearer " + this.tokenStore.jwtToken
            }
        };
        await axios.post('http://localhost:9090/api/categories/addCategory', { categoryName: this.newCategory }, config)
            .then(response => {
            if (response.status === 201) {
                this.categories.push(response.data);
                this.newCategory = '';
            }
            })
            .catch(error => {
            console.log(error);
            });
        }
    }
  }
  </script>
  
  <style scoped>
  .container-admin {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: start;
    margin-bottom: 2em;
  }


.header {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80px;
  background-color: #eee;
}

.header h1 {
  margin: 0;
  font-size: 24px;
}

.user-section,
.category-section,
.item-section {
  width: 30%;
  margin: 0 2%;
}

.user-section h1,
.category-section h1,
.item-section h1 {
  margin-top: 0;
}

.user-section ul,
.category-section ul,
.item-section ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.user-section li,
.category-section li,
.item-section li {
  position: relative;
  padding: 10px;
  margin-bottom: 10px;
  background-color: #f5f5f5;
  border-radius: 5px;
}

.user-section li:hover,
.category-section li:hover,
.item-section li:hover {
  background-color: #ddd;
}

.user-section .delete,
.category-section .delete,
.item-section .delete {
  position: absolute;
  top: 5px;
  right: 5px;
  font-size: 14px;
  color: #999;
}

.user-section input[type="text"],
.category-section input[type="text"] {
  width: 80%;
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-bottom: 10px;
}

.user-section button[type="submit"],
.category-section button[type="submit"] {
  padding: 8px;
  border-radius: 5px;
  border: none;
  background-color: #4CAF50;
  color: #fff;
  cursor: pointer;
}
  .delete {
    cursor: pointer;
    position: absolute;
    top: 0;
    right: 0;
  }
  </style>