<template>
  <div class="map-container">
    <ProvinceMap
        @click="handleProvinceClick"
        :provinceColors="provinceColors"
    />
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
import {kieskringenData} from '@/components/map/kieskringData';
import partyColors from "@/components/map/partyColors.js";

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
      votingData: [], // Array to store fetched voting data for all provinces
      provinceColors: {}, // Stores colors for each province based on winning parties
    };
  },
  computed: {
    // Filter and aggregate votes for the selected province and its constituencies
    aggregatedVotingData() {
      if (!this.selectedProvince) return [];

      // Get constituencies within the selected province
      const constituencies = kieskringenData
          .filter(item => item.province === this.selectedProvince)
          .map(item => item.regionName);

      // Filter votes data for these constituencies and aggregate by party
      const votesByParty = {};
      this.votingData
          .filter(item => constituencies.includes(item.constituencyName))
          .forEach(item => {
            if (!votesByParty[item.partyId]) {
              votesByParty[item.partyId] = {partyName: item.partyName, totalVotes: 0};
            }
            votesByParty[item.partyId].totalVotes += item.totalVotes;
          });

      // Convert to an array format suitable for the PopupMap component
      return Object.values(votesByParty);
    },
  },
  created() {
    this.fetchVotingData(); // Fetch data when the component is created
    this.fetchWinnersByProvince(); // Call the function to fetch winners when the page loads
  },
  methods: {
    async fetchVotingData(kieskring) {
      console.log("Selected Kieskring:", kieskring);

      try {
        const response = await axios.get('http://localhost:8080/api/election/totalVotesByParty', {
          params: {constituencies: [kieskring]},
          paramsSerializer: params => {
            return params.constituencies.map(c => `constituencies=${encodeURIComponent(c)}`).join('&');
          },
        });

        console.log("API Response:", response.data);

        const data = Array.isArray(response.data) ? response.data : response.data.results;

        this.votingData = data.map(item => ({
          partyId: item[0],
          partyName: item[1],
          constituencyName: item[2],
          totalVotes: item[3],
        }));

        console.log("Processed Voting Data for Kieskringen:", this.votingData);
      } catch (error) {
        console.error("Error fetching voting data:", error);
      }
    },
    handleProvinceClick(province) {
      if (typeof province === 'string') {
        console.log("Province Clicked:", province);

        this.selectedProvince = province;
        this.showPopup = true;

        // Fetch data for constituencies in the clicked province
        this.fetchVotingData(kieskringenData[0].regionName); // Initial kieskring when province is selected
      } else {
        console.warn("Invalid province data:", province);
      }
    },
    async fetchWinnersByProvince() {
      try {
        const response = await axios.get('http://localhost:8080/api/election/winners-by-province');
        console.log("Winners by Province:", response.data);

        // Update provinceColors with the color of the winning party
        this.provinceColors = {};
        for (const [province, winner] of Object.entries(response.data)) {
          this.provinceColors[province] = partyColors(winner);  // Get color for the winning party
        }

        console.log("Province Colors:", this.provinceColors);
      } catch (error) {
        console.error("Error fetching winners by province:", error);
      }
    },
  },
};
</script>

<style scoped>
.map-container {
  max-width: 100%;
}
</style>
