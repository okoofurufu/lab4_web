<script>
import LogoutButton from "@/components/LogoutButton.vue";

export default {
  name: "Header",
  components: {LogoutButton},
  methods: {
    checkAuth() {
      return localStorage.getItem("token") !== null;
    }
  }
}
</script>

<template>
  <header class="header">
    <div class="h-wrap">
      <h2>Смирнова Дарья</h2>
      <h3>P3225 Вар 609</h3>
    </div>
    <div class="h-wrap">
      <h1>WEB LAB 4</h1>
    </div>
    <div class="h-wrap">
      <LogoutButton v-if="checkAuth"/>
    </div>
  </header>
</template>

<style scoped>
.header {
  display: flex;
  position: fixed;
  width: 100%;
  top: 0;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background: #c0aaff; /* Однородный светло-фиолетовый цвет */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  color: #ffffff;
}

.header .h-wrap:nth-child(2) {
  flex-grow: 1;
  text-align: center;
}

.h-wrap {
  flex: 1;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

h1 {
  font-size: 1.6em;
  font-weight: 600;
  margin: 0;
  color: #fff;
}

h1, h2, h3 {
  margin: 5px 0;
}
</style>