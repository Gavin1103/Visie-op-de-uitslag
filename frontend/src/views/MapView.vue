<template>
  <div class="map-container">
    <ProvinceMap @click="handleProvinceClick" />
    <PopupMap
        v-if="showPopup"
        :title="selectedProvince"
        :votingData="filteredVotingData"
    @close="showPopup = false"
    >
    <p>Details about {{ selectedProvince }} go here.</p>
    </PopupMap>
  </div>
</template>


<script>
import axios from 'axios';
import ProvinceMap from '@/components/map/ProvinceMap.vue';
import PopupMap from '@/components/map/PopupMap.vue';

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
    };
  },
  computed: {
    // Filter voting data for the selected province when popup opens
    filteredVotingData() {
      return this.votingData.filter(item => item.constituencyName === this.selectedProvince);
    },
  },
  created() {
    this.fetchVotingData(); // Fetch data when the component is created
  },
  methods: {
    async fetchVotingData() {
      try {
        const response = await axios.get('http://localhost:8080/api/election/totalVotesByParty');
        console.log("API Response:", response.data);

        // Process response data to ensure it's in the correct format
        const data = Array.isArray(response.data) ? response.data : response.data.results;

        // Map the response data into votingData array
        this.votingData = data.map(item => ({
          partyId: item[0],
          partyName: item[1],
          constituencyName: item[2],
          totalVotes: item[3],
        }));
      } catch (error) {
        console.error("Error fetching voting data:", error);
      }
    },
    handleProvinceClick(province) {
      if (typeof province === 'string') {
        this.selectedProvince = province;
        this.showPopup = true;
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
