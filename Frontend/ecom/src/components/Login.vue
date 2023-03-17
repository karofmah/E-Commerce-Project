<template>
<div class=" wrapper">
    <h1>WELCOME TO (.....)</h1>
    <div class="main-container">
        <div class="username">
            <input placeholder="Your username" type="text">
        </div>
        <div class="password">
            <input placeholder="Your password" type="password">
        </div>
        <div class="submit">
            <button>Log in</button>
        </div>
        <div class="register">
            <p>Do you want to register a new user?</p>
            <p>Press here</p>
            <button>Register</button>
        </div>
    </div>
</div>


</template>




<style scoped>

.wrapper{
    display: flex;
    flex-direction: column;
    place-items: center;
    justify-content: center;
    align-items: center;
    width: 100vw; 
    height: 100vh;
}

.main-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #cbd5e1;
  width: 65%;
  height: 50%;
  border-radius: 15px;
}


</style>




