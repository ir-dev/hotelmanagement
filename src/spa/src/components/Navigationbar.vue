<template>
  <div class="tm-top-bar" id="tm-top-bar">
    <!-- Top Navbar -->
    <div class="container">
      <div class="row">
        <nav class="navbar navbar-expand-lg narbar-light">
          <a class="navbar-brand mr-auto" href="#">
            <img src="img/logo.png" alt="Site logo" />
            Level
          </a>
          <div id="mainNav" class="collapse navbar-collapse tm-bg-white">
            <ul class="navbar-nav ml-auto">
              <li class="nav-item">
                <a class="nav-link" href="#tm-section-1">StayDetails</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#tm-section-2">RoomAssignment</a>
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
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
