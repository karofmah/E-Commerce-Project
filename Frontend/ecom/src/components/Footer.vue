<template>
     <div class="container">
        <div class="logo">
            <img src="../assets/plus-square-fill.svg" alt="Logo">
        </div>
        <div class="copyright">
            <p>This is copyright© info</p>
        </div>
        <div class="info-box">
            <p>This is info about program</p>
        </div>
    </div>
</template>


<style scoped>
.container{
    background-color: #191f2b;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 40%;
    padding: 1em;
}

.info-box{
    color: white;
    font-size: 18px;
    width: 33%;
    display: flex; 
    flex-direction: column;
    justify-content: center; 
    align-items: center; 
}

.copyright{
    color: white;
    font-size: 18px;
    width: 33%;
    display: flex;
    justify-content: center; 
    align-items: flex-end;
}

.logo{
    width: 33%;
    display: flex; 
    justify-content: flex-start; 
    align-items: flex-start;
    flex-direction: column;
}

.logo img{
    height: 12rem;
}



@media (max-width: 768px) {
    .logo img{
        height: 8rem;
    }
}

@media (max-width: 768px) {
    .opphavsrett{
        font-size: 13px;
    }
}

@media (max-width: 768px) {
    .info-box{
        font-size: 13px;
    }
}



</style>
