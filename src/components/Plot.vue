<script>
import * as d3 from "d3";

export default {
  name: "Plot",
  props: {
    points: {
      type: Array
    },
    currentR: {
      type: Number
    },
    sendPoint: {
      type: Function,
      required: true
    }
  },
  mounted() {
    this.drawPlot();
    this.drawPoints(this.points);
  },
  watch: {
    currentR() {
      d3.select("#plot svg").remove();
      this.drawPlot();
      this.drawPoints(this.points);
    }
  },
  methods: {
    async drawPoints(points) {
      points.forEach((point) => {
        this.drawPoint(point.x, point.y, point.r, point.hit);
      });
    },
    drawPoint(x, y, r, isIn) {
      const currentR = parseFloat(this.currentR) || 4;

      const width = 400;
      const height = 400;
      const padding = 30;
      const xScale = d3.scaleLinear().domain([-4, 4]).range([padding, width - padding]);
      const yScale = d3.scaleLinear().domain([-4, 4]).range([height - padding, padding]);

      const svg = d3.select("#plot svg");
      const hit = checkPoint(x, y, currentR);

      function checkPoint(x, y, currentR) {
        let r = currentR;
        const inRectangle = (x >= 0) && (y >= 0) && (y <= r) && (x <= r/2);
        const inTriangle = (x <= 0) && (y >= 0) && (y <= x + r);
        const inCircle = (x >= 0) && (y <= 0) && (x * x + y * y <= (r / 2) * (r / 2));
        return inRectangle || inTriangle || inCircle;
      }

      svg
          .append("circle")
          .attr("cx", xScale(x))
          .attr("cy", yScale(y))
          .attr("r", 5)
          .attr("fill", hit ? "blue" : "red")
          .attr("opacity", 0.8);
    },
    submitPoint(x, y, r) {
      return this.sendPoint({x, y, r});
    },
    drawPlot() {
      const width = 400;
      const height = 400;
      const pad = 30;
      const r = parseFloat(this.currentR) || 4;
      const svg = d3
          .select("#plot")
          .append("svg")
          .attr("width", width)
          .attr("height", height)
          .style("background-color", "none");

      const xScale = d3.scaleLinear().domain([-4, 4]).range([pad, width - pad])
      const yScale = d3.scaleLinear().domain([-4, 4]).range([height - pad, pad])

      const axisColor = "white";

      // ось по X
      svg
          .append("g")
          .attr("transform", `translate(0, ${height / 2})`)
          .call(d3.axisBottom(xScale).ticks(10).tickSizeOuter(0).tickFormat(d3.format(".1f")))
          .attr("color", axisColor);

      // ось по Y
      svg
          .append("g")
          .attr("transform", `translate(${width / 2},0)`)
          .call(d3.axisLeft(yScale).ticks(10).tickSizeOuter(0).tickFormat(d3.format(".1f")))
          .attr("color", axisColor);

      if (this.currentR >= 0 && !isNaN(this.currentR)) {
        const pathData = d3
            .path();
        pathData
            .moveTo(xScale(0), yScale(0));
        pathData
            .arc(xScale(0), yScale(0), 42.5*(r/2), 0, Math.PI / 2);
        pathData
            .lineTo(xScale(0), yScale(0));
        pathData
            .lineTo(xScale(-r), yScale(0));
        pathData
            .lineTo(xScale(0), yScale(r));
        pathData
            .lineTo(xScale(r/2), yScale(r));
        pathData
            .lineTo(xScale(r / 2), yScale(0));
        pathData
            .lineTo(xScale(0), yScale(0));
        pathData
            .closePath();

        svg
            .append("path")
            .attr("d", pathData)
            .attr("fill", "#7f7fff")  // Теперь используется просто светло-фиолетовый цвет
            .attr("opacity", 0.7);
      }

      // чтобы не обрезался svg
      svg.attr("viewBox", `0 0 ${width} ${height}`).attr("preserveAspectRatio", "xMidYMid meet");

      svg
          .on("click", (e) => {
            const coords = d3.pointer(e);
            const x = ((coords[0] - 200) / 42.5).toFixed(2);
            const y = -((coords[1] - 200) / 42.5).toFixed(2);
            let resp = this.submitPoint(x, y, r);
            this.drawPoint(resp.x, resp.y, resp.r, resp.isIn);
            console.log({x, y, r});
          })
    }
  }

}
</script>

<template>
  <div class="default-panel">
    <div id="plot"></div>
  </div>
</template>

<style scoped>
.default-panel {
  margin: 40px auto;
}

#plot {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px;
  border-radius: 10px;
  overflow: visible;
}
</style>