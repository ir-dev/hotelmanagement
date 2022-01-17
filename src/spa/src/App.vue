<template>
  <form>
    <StayDetails></StayDetails>
    <RoomAssignment></RoomAssignment>
  </form>
</template>

<script>

import StayDetails from "@/components/StayDetails";
import RoomAssignment from "@/components/RoomAssignment";

export default {
  name: 'App',
  components: {
    RoomAssignment,
    StayDetails
  }
}
</script>

<style>
@import './assets/css/bootstrap.min.css';
@import './assets/css/datepicker.css';
@import './assets/css/tooplate-style.css';
@import './assets/font-awesome-4.7.0/css/font-awesome.css';
@import './assets/font-awesome-4.7.0/css/font-awesome.min.css';
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
