<template>
     <div class="wrapperbottom">
        <div class="logo">
            <h1>hello</h1>
        </div>
        <div class="opphavsrett">
            <p>hello</p>
        </div>
        <div class="info-box">
            <p>hello</p>
            
        </div>
    </div>
</template>


<style scoped>
.wrapperbottom{
    background-color: #191f2b;
    display: flex;
    flex-direction: row;
    justify-content: space-between; /* added to space out elements */
    align-items: center; /* added to vertically center elements */
    width: 100%;
    height: 40%;
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

.opphavsrett{
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
