<script>
import Plot from "@/components/Plot.vue";
import PointForm from "@/components/PointForm.vue";
import PointTable from "@/components/PointTable.vue";
import ErrorMessage from "@/components/ErrorMessage.vue";
import {json} from "d3";

export default {
  name: "MainPage",
  components: {ErrorMessage, PointTable, PointForm, Plot},
  data() {
    return {
      points: [],
      currentR: 4
    }
  },
  mounted() {
    const token = localStorage.getItem("token");

    if (!token) {
      this.$router.push("/");
    }

    this.fetchPoints();
  },
  methods: {
    async fetchPoints() {
      const userId = localStorage.getItem("id");
      try {
        const response = await fetch(`http://localhost:8080/laba4/api/controller/results/${userId}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          }
        });

        if (response.ok) {
          const data = await response.json();
          this.points = [...data];

          await this.$refs.plot.drawPoints(this.points);
        } else {
          console.error("Error fetching points: ", response.error);
        }
      } catch (error) {
        console.error("Error fetching points: ", error);
      }
    },
    updateR(newR) {
      this.currentR = parseFloat(newR);
    },
    async sendPoint(point) {
      const userId = localStorage.getItem("id");

      if (!userId) {
        this.$refs.errorMessage.showMessage("Не удалось найти userId. Пожалуйста, авторизуйтесь.");
        return;
      }

      point.userId = userId;

      if (point.x < -4 || point.x > 4) {
        this.$refs.errorMessage.showMessage("Значение X должно быть в диапазоне от -4 до 4");
        return;
      }
      if (point.y < -3 || point.y > 5) {
        this.$refs.errorMessage.showMessage("Значение Y должно быть в диапазоне от -3 до 5");
        return;
      }
      if (point.r < -4 || point.r > 4) {
        this.$refs.errorMessage.showMessage("Значение R должно быть в диапазоне от -4 до 4");
        return;
      }
      let x = point.x;
      let y = point.y;
      let r = point.r;
      let data = {
        x: x,
        y: y,
        r: r,
        userId: userId,
      }
      try {
        const response = await fetch('http://localhost:8080/laba4/api/controller/results', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(data),
        });

        const newPoint = await response.json();
        await this.fetchPoints();

        await this.$refs.plot.drawPoints(this.points);

        return newPoint;

      } catch (error) {
        console.error('Error sending point:', error);
        throw error;
      }
    },
  }
}
</script>

<template>
  <div class="main-page">
    <div class="top-panel">
      <Plot ref="plot" :points="points" :current-r="currentR" :send-point="sendPoint" />
      <PointForm :send-point="sendPoint" @updateR="updateR"/>
    </div>
    <PointTable :points="points" />
  </div>

  <ErrorMessage ref="errorMessage"/>
</template>

<style scoped>
.main-page {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0;
}
.top-panel {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  height: 100%;
  align-items: center;
}
</style>