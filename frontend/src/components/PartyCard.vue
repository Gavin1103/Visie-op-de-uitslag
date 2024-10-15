<script setup lang="ts">
import type { Party } from '@/models/Party'
import router from '@/router'

const props = defineProps<{
  party: Party;
}>();

const ClickCard = (id: number) => {
  router.push({name:`party`, params: {id: id}})
}
</script>

<template>
  <div @click="ClickCard(party.partyId)" class="party-card">
      <img :src="party.image" alt="Party Logo" class="party-logo" />
      <div class="flex items-center flex-1 justify-center">
        <h3 class="text-xl font-semibold">{{ party.name }}</h3>
      </div>
  </div>
</template>

<style scoped>
.party-card {
  margin: 10px;
  width: 150px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.party-card img {
  height: 80px;
  width: 130px;
  object-fit: cover;
  background-color: #0056b3;
}

.party-card h3 {
  margin: 5px 0;

}

.party-card button {
  margin-top: 10px;
  padding: 8px 16px;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.party-card:hover {
  cursor: pointer;
  background-color: #aaa5a5;
}
</style>