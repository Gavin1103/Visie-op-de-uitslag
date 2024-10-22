<script lang="ts">
import { onMounted, ref } from 'vue'
import {Party} from '@/models/Party'
import PartyCard from '@/components/PartyCard.vue'
import { ElectionService } from '@/services/ElectionService'

export default {
  components: { PartyCard },
  setup(){
    const parties = ref<Party[]>([]);
    const electionService = new ElectionService();

    const fetchParties = async () => {
       try{
         parties.value = await electionService.getParties();
       }
       catch (error){
         console.error("Error fetching parties:", error)
       }
    }
    onMounted(() => {
      fetchParties();
    });
    return {
      parties
    }
  }
}
</script>

<template>
  <div class="flex justify-center my-14">
  <div class="w-10/12">
    <div class="flex flex-wrap gap-6">
      <PartyCard v-for="party in parties" :key="party.id" :party="party" />
    </div>
  </div>
  </div>
</template>

<style scoped>

</style>