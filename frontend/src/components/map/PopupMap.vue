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

      <!-- Display the current kieskring -->
      <div v-if="currentKieskring" class="mb-4">
        <h3 class="text-lg font-semibold text-gray-800">{{ currentKieskring.regionName }}</h3>
      </div>

      <!-- Voting Data -->
      <ul class="list-disc pl-6">
        <li v-for="party in votingData" :key="party.partyId" class="text-gray-800">
          <span class="font-medium">{{ party.partyName }}:</span> {{ party.totalVotes }}
        </li>
      </ul>

      <slot></slot>
    </div>
  </div>
</template>

<script>
import { kieskringenData } from '@/components/map/kieskringData';

export default {
  name: "PopupMap",
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
      // Trigger event to fetch data for the selected kieskring
      this.$emit('fetchVotingData', this.selectedKieskring);
    },
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
</style>
