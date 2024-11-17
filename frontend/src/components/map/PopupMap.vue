<template>
  <div class="popup-overlay" @click.self="closePopup">
    <div class="popup-content">
      <button class="close-btn" @click="closePopup">X</button>
      <h2 class="text-xl font-bold text-gray-800">{{ title }}</h2>

      <!-- Dropdown to select kieskring -->
      <div class="mb-4">
        <label for="kieskring" class="block text-sm font-semibold text-gray-700">Select Kieskring:</label>
        <select
            id="kieskring"
            v-model="selectedKieskring"
            @change="fetchVotingData"
            class="mt-2 p-3 border-2 border-blue-500 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 w-full"
        >
          <option v-for="region in filteredRegions" :key="region.regionNumber" :value="region.regionName">
            {{ region.regionName }}
          </option>
        </select>
      </div>

      <!-- Pie Chart displaying voting data -->
      <div v-if="chartData.labels.length > 0" class="mb-4">
        <PieChart :data="chartData" />
      </div>
      <div v-else>
        <p>No data available for the selected kieskring.</p>
      </div>

      <!-- Display the current kieskring -->
      <div v-if="currentKieskring" class="mb-4">
        <h3 class="text-lg font-semibold text-gray-800">{{ currentKieskring.regionName }}</h3>
      </div>

      <slot></slot>
    </div>
  </div>
</template>

<script>
import { Pie } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement } from 'chart.js';
import { kieskringenData } from '@/components/map/kieskringData';
// Import party colors from the external file
import getPartyColor from '@/components/map/partyColors'; // Adjust path if needed

ChartJS.register(Title, Tooltip, Legend, ArcElement);

export default {
  name: "PopupMap",
  components: {
    PieChart: Pie
  },
  props: {
    title: {
      type: String,
      required: true,
    },
    votingData: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      selectedKieskring: '', // Track the selected kieskring
      chartData: {
        labels: [],
        datasets: [{
          data: [],
          backgroundColor: [], // Will be populated with colors dynamically
          hoverBackgroundColor: [], // Will be populated with hover colors dynamically
        }],
      },
      isLoading: false, // To track loading state
    };
  },
  computed: {
    filteredRegions() {
      const normalizedTitle = this.normalizeString(this.title);
      return kieskringenData.filter(region => {
        const normalizedProvince = this.normalizeString(region.province);
        return normalizedProvince === normalizedTitle;
      });
    },
    currentKieskring() {
      return this.filteredRegions.find(region => region.regionName === this.selectedKieskring) || null;
    },
  },
  mounted() {
    // Automatically select the first kieskring in the filtered list when the component mounts
    if (this.filteredRegions.length > 0 && !this.selectedKieskring) {
      this.selectedKieskring = this.filteredRegions[0].regionName;
      this.fetchVotingData(); // Fetch data for the first kieskring
    }
  },
  watch: {
    // Watch for changes in votingData prop and update the chart accordingly
    votingData: {
      handler(newData) {
        if (newData && newData.length > 0) {
          this.updateChartData(newData); // Update chart when votingData changes
        } else {
          // If no data, reset the chart
          this.resetChartData();
        }
      },
      immediate: true, // Make sure it triggers immediately when the component mounts
    },
  },
  methods: {
    normalizeString(str) {
      return str
          .trim()
          .toLowerCase()
          .normalize('NFD')
          .replace(/[\u0300-\u036f]/g, "");
    },
    closePopup() {
      this.$emit('close');
    },
    fetchVotingData() {
      this.isLoading = true; // Start loading state
      // Trigger event to fetch data for the selected kieskring
      this.$emit('fetchVotingData', this.selectedKieskring);
    },
    updateChartData(votingData) {
      const labels = votingData.map(party => party.partyName);
      const data = votingData.map(party => party.totalVotes);

      // Map party names to their respective colors using getPartyColor
      const backgroundColor = votingData.map(party => getPartyColor(party.partyName));

      this.chartData.labels = labels;
      this.chartData.datasets[0].data = data;
      this.chartData.datasets[0].backgroundColor = backgroundColor;
      this.chartData.datasets[0].hoverBackgroundColor = backgroundColor; // Optional: matching hover color

      this.isLoading = false; // End loading state
    },
    resetChartData() {
      this.chartData.labels = [];
      this.chartData.datasets[0].data = [];
      this.chartData.datasets[0].backgroundColor = [];
      this.chartData.datasets[0].hoverBackgroundColor = [];
      this.isLoading = false; // End loading state
    }
  },
};
</script>

<style scoped>
.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.popup-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 400px;
  width: 100%;
  position: relative;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: transparent;
  border: none;
  font-size: 18px;
  cursor: pointer;
}

.mb-4 {
  margin-bottom: 1rem;
}
</style>
