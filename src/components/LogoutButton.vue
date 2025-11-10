<script>
export default {
  name: "LogoutButton",
  methods: {
    async handleLogout() {
      try {
        const resp = await fetch("http://localhost:8080/laba4/api/controller/logout", {
          method: "POST",
          headers: {
            "Authorization": "Bearer " + localStorage.getItem("token"),
            "Content-Type": "application/json"
          }
        });

        if (resp.ok) {
          localStorage.removeItem("token");
          this.$router.push("/");
        } else {
          const errorData = await resp.json();
          this.$refs.errorMessage.showMessage(errorData.message || "Ошибка на сервере");
        }
      } catch (error) {
        console.error("Ошибка при выходе:", error);
        this.$refs.errorMessage.showMessage("Ошибка при выходе");
      }
    }
  }
}
</script>

<template>
  <button class="btn-secondary" @click="handleLogout">
    Выйти
  </button>
</template>

<style scoped>
.btn-secondary {
  font-family: 'Montserrat', sans-serif;
  width: 30%;
}
</style>