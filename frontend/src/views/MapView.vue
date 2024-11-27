<template>
  <div class="map-container">
    <!-- Hover Information -->
    <div class="hover-info">
      <h3>Province Info</h3>
      <p v-if="hoveredProvince">
        <strong>{{ hoveredProvince }}</strong><br />
        Winner: {{ hoveredWinner || 'No data available' }}
      </p>
      <p v-else>Hover over a province to see details</p>
    </div>

    <!-- Map -->
    <ProvinceMap
        @click="handleProvinceClick"
        @hover="handleProvinceHover"
        :provinceColors="provinceColors"
    />

    <!-- Popup -->
    <PopupMap
        v-if="showPopup"
        :title="selectedProvince"
        :votingData="aggregatedVotingData"
        @close="showPopup = false"
        @fetchVotingData="fetchVotingData"
    >
      <p>Details about {{ selectedProvince }} go here.</p>
    </PopupMap>
  </div>
</template>

<script>
import axios from 'axios';
import ProvinceMap from '@/components/map/ProvinceMap.vue';
import PopupMap from '@/components/map/PopupMap.vue';
import { kieskringenData } from '@/components/map/kieskringData';
import partyColors from '@/components/map/partyColors.js';

export default {
  name: 'NetherlandsMap',
  components: {
    ProvinceMap,
    PopupMap,
  },
  data() {
    return {
      showPopup: false,
      selectedProvince: '',
      hoveredProvince: '', // Tracks hovered province name
      hoveredWinner: '', // Tracks winner in the hovered province
      votingData: [], // Array to store fetched voting data for all provinces
      provinceColors: {}, // Stores colors for each province based on winning parties
      provinceWinner: {}, // Stores winner party for each province
    };
  },
  computed: {
    aggregatedVotingData() {
      if (!this.selectedProvince) return [];
      const constituencies = kieskringenData
          .filter(item => item.province === this.selectedProvince)
          .map(item => item.regionName);

      const votesByParty = {};
      this.votingData
          .filter(item => constituencies.includes(item.constituencyName))
          .forEach(item => {
            if (!votesByParty[item.partyId]) {
              votesByParty[item.partyId] = {
                partyName: item.partyName,
                totalVotes: 0,
              };
            }
            votesByParty[item.partyId].totalVotes += item.totalVotes;
          });

      return Object.values(votesByParty);
    },
  },
  created() {
    this.fetchVotingData();
    this.fetchWinnersByProvince();
  },
  methods: {
    handleProvinceClick(province) {
      if (typeof province === 'string') {
        this.selectedProvince = province;
        this.showPopup = true;
        this.fetchVotingData(kieskringenData[0].regionName);
      }
    },
    handleProvinceHover(province) {
      if (province) {
        this.hoveredProvince = province;
        this.hoveredWinner = this.getWinnerByProvince(province); // Get winner's party name
      } else {
        this.hoveredProvince = '';
        this.hoveredWinner = '';
      }
    },
    getWinnerByProvince(province) {
      // Get the winner party name by checking provinceWinner (returns null if no data)
      return this.provinceWinner[province] || 'No data available';
    },

    async fetchVotingData(kieskring) {
      try {
        const response = await axios.get(
            'http://localhost:8080/api/election/totalVotesByParty',
            {
              params: {constituencies: [kieskring]},
              paramsSerializer: params => {
                return params.constituencies
                    .map(c => `constituencies=${encodeURIComponent(c)}`)
                    .join('&');
              },
            }
        );
        const data = Array.isArray(response.data)
            ? response.data
            : response.data.results;

        this.votingData = data.map(item => ({
          partyId: item[0],
          partyName: item[1],
          constituencyName: item[2],
          totalVotes: item[3],
        }));
      } catch (error) {
        console.error('Error fetching voting data:', error);
      }
    },
    async fetchWinnersByProvince() {
      try {
        const response = await axios.get(
            'http://localhost:8080/api/election/winners-by-province'
        );
        this.provinceWinner = {};  // Clear previous data
        this.provinceColors = {};  // Clear previous color data

        // Populate provinceWinner with winner party names and provinceColors with party colors
        for (const [province, winner] of Object.entries(response.data)) {
          this.provinceWinner[province] = winner; // Store winner party name
          this.provinceColors[province] = partyColors(winner); // Apply color
        }
      } catch (error) {
        console.error('Error fetching winners by province:', error);
      }
    },
  },
};
</script>

<style scoped>
.map-container {
  display: flex;
  position: relative; /* Set relative position to position the hover-info inside it */
}

.hover-info {
  position: absolute; /* Position it absolutely relative to the map-container */
  top: 50%; /* Place it vertically in the middle */
  left: 80%; /* Move it to the right of the container (adjust the percentage to your liking) */
  transform: translate(-50%, -50%); /* Center it vertically and horizontally */
  width: 300px; /* Set the desired width */
  padding: 20px; /* Increase padding for a larger area */
  border-right: 2px solid #ddd; /* Border style */
  background-color: #f9f9f9; /* Background color */
  height: auto; /* Set height to auto, or you can set a fixed height */
  overflow-y: auto; /* Allow scrolling if the content overflows */
}

.hover-info h3 {
  margin-top: 0;
  font-size: 1.5em; /* Increase font size */
  color: #333; /* Set a dark color for the heading */
}

.hover-info p {
  font-size: 1.1em; /* Increase font size for the paragraph */
  color: #555; /* Set color for text */
  line-height: 1.5; /* Add line height for readability */
}
</style>

