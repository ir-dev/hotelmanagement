<template>
  <div class="tm-top-bar" id="tm-top-bar">
    <div class="container">
      <div class="row">
        <nav class="navbar navbar-expand-lg navbar-light">
          <img src="../assets/img/hotelschwarz.png" alt="logo" id="img"/>
          <h1 id="nav">HOTEL SCHWARZ</h1>
          <button
              id="nav-toggle"
              class="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#mainNav"
              aria-expanded="true"
              aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div id="mainNav" class="navbar-collapse tm-bg-white collapse show" style="">
            <ul class="navbar-nav ml-auto">
              <li class="nav-item">
                <a class="nav-link" href="#tm-section-1">StayDetails</a>
                <span class="sr-only">(current)</span>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#tm-section-2">RoomDetails</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#tm-section-3">GuestDetails</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#tm-section-4">PaymentDetails</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Navigationbar",
};
</script>

<style>
#nav {
  color: #0062cc;
  font-family: Dubai;
  font-size: 35px;
  font-weight: bold;
}

#img {
  height: 80px;
  margin-bottom: 10px;
  margin-right: 25px;
}

#app {
  font-family: Dubai;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #0062cc;
  margin-top: 60px;
}
</style>
