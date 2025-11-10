<script>
export default {
  name: "PointTable",
  props: {
    points: {
      type: Array,
      required: true
    }
  }
}
</script>

<template>
  <div class="default-panel">
    <table>
      <thead>
      <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Попал?</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(point, index) in points" :key="index">
        <td>{{ point.x }}</td>
        <td>{{ point.y }}</td>
        <td>{{ point.r }}</td>
        <td>{{ point.hit ? 'Да' : 'Нет' }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.default-panel {
  width: 100%;
  min-width: 720px;
}

table {
  width: 100%;
  border-collapse: collapse;
  color: #333;
}

th {
  padding: 12px;
  background: #a6a4ff;
  color: #fff;
  text-transform: uppercase;
  font-weight: bold;
  text-align: center;
  border: 1px solid #ccc;
  font-size: 13px;
}

td {
  padding: 12px;
  text-align: center;
  border: 1px solid #ddd;
  background-color: #f8f6ff;
  color: #333;
}

tr:nth-child(even) {
  background-color: #f1f0f7;
}

tr:hover {
  background: linear-gradient(145deg, #9a8cff, #6a49ff);
  color: #fff;
}
</style>