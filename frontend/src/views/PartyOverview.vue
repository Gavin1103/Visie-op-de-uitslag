<script lang="ts">
import { onMounted, ref, computed } from 'vue'
import { Party } from '@/models/Party'
import PartyCard from '@/components/PartyCard.vue'
import { ElectionService } from '@/services/ElectionService'

export default {
  components: { PartyCard },
  setup() {
    const parties = ref<Party[]>([]);
    const searchQuery = ref<string>('');  // Added for search functionality
    const electionService = new ElectionService();

    const fetchParties = async () => {
      try {
        parties.value = await electionService.getParties();
      } catch (error) {
        console.error("Error fetching parties:", error);
      }
    };

    onMounted(() => {
      fetchParties();
    });

    // Computed property to filter and sort parties
    const filteredParties = computed(() => {
      return parties.value
          .filter(party => party.name.toLowerCase().includes(searchQuery.value.toLowerCase()))
          .sort((a, b) => a.name.localeCompare(b.name));
    });

    return {
      parties,
      searchQuery,
      filteredParties,  // Exposing filtered parties
    };
  }
};
</script>

<template>
  <div class="flex justify-center my-14">
    <div class="w-10/12">

      <!-- Search Bar -->
      <div class="flex justify-between items-center mb-6">
        <input
            v-model="searchQuery"
            type="text"
            placeholder="Search party..."
            class="border border-gray-400 p-3 rounded-lg w-full bg-gray-50 shadow-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <!-- Party Cards -->
      <div class="flex flex-wrap gap-6">
        <PartyCard v-for="party in filteredParties" :key="party.id" :party="party" />
      </div>
    </div>
  </div>
</template>

<style>

</style>