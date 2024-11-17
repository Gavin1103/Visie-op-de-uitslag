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

      <!-- Container for Pie Chart and Party List -->
      <div class="flex items-start justify-between">
        <!-- Party List on the left -->
        <div class="party-list-container w-1/3 pr-4">
          <h3 class="text-lg font-semibold text-gray-800 mb-2">Party List:</h3>
          <div v-for="party in sortedParties" :key="party.name" class="flex items-center mb-2">
            <div
                :style="{ backgroundColor: party.color }"
                class="w-6 h-6 rounded-full mr-2"
            ></div>
            <span class="text-sm">{{ party.name }} - {{ party.votes }} votes</span>
          </div>
        </div>

        <!-- Pie Chart on the right, takes up remaining space -->
        <div class="pie-chart-container w-2/3">
          <div v-if="chartData.labels.length > 0" class="mb-4">
            <PieChart :data="chartData" />
          </div>
          <div v-else>
            <p>No data available for the selected kieskring.</p>
          </div>
        </div>
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
import getPartyColor from '@/components/map/partyColors'; // Import party color function

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
      allParties: [], // Will store the full list of parties and their votes
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
    sortedParties() {
      // Sort the parties based on the number of votes in descending order
      return this.allParties.sort((a, b) => b.votes - a.votes);
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
    votingData: {
      handler(newData) {
        if (newData && newData.length > 0) {
          this.updateChartData(newData); // Update chart when votingData changes
        } else {
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
      this.isLoading = true;
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
      this.chartData.datasets[0].hoverBackgroundColor = backgroundColor;

      // Build the full list of all parties (with their color and vote count)
      this.allParties = this.buildPartyList(votingData);

      this.isLoading = false;
    },
    resetChartData() {
      this.chartData.labels = [];
      this.chartData.datasets[0].data = [];
      this.chartData.datasets[0].backgroundColor = [];
      this.chartData.datasets[0].hoverBackgroundColor = [];
      this.isLoading = false;
    },
    buildPartyList(votingData) {
      // List of all possible parties
      const allPartyNames = [
        'VVD', 'CDA', 'D66', 'PVV (Partij voor de Vrijheid)', 'GROENLINKS / Partij van de Arbeid (PvdA)',
        'Partij voor de Dieren', 'Volt', 'BIJ1', 'DENK', 'Nieuw Sociaal Contract', 'Forum voor Democratie',
        'SP (Socialistische Partij)', 'BBB', 'Piratenpartij', 'ChristenUnie', 'Staatkundig Gereformeerde Partij (SGP)',
        'LEF - Voor de Nieuwe Generatie', 'VNL', '50PLUS', 'BVNL / Groep Van Haga', 'Nederland met een PLAN',
        'LP (Libertaire Partij)', 'Splinter', 'Partij voor de Sport', 'Politieke Partij voor Basisinkomen', 'Samen voor Nederland'
      ];

      // Generate the party list with color and vote data
      return allPartyNames.map(partyName => {
        const partyData = votingData.find(party => party.partyName === partyName) || { totalVotes: 0 };
        return {
          name: partyName,
          color: getPartyColor(partyName),
          votes: partyData.totalVotes,
        };
      });
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
  border-radius: 12px; /* Rounded corners */
  width: 900px; /* Wider pop-up */
  height: 800px; /* Adjust the height to make it shorter */
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Subtle shadow for a more polished look */
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

.party-list-container {
  width: 100%;
  max-height: 600px;
  overflow-y: auto;
}

.pie-chart-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

.pie-chart-container canvas {
  width: 600px !important; /* Adjust the width to make it larger */
  height: 600px !important; /* Adjust the height accordingly */
}

.flex {
  display: flex;
}

.party-list-container {
  padding-right: 20px;
}


</style>

