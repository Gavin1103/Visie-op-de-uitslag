<script setup lang="ts">
import type { Party } from '@/models/Party'

const props = defineProps<{
  party: Party;
}>();
</script>

<template>
  <div class="party-card w-52">
    <div class="card">
      <img :src="party.logoUrl" alt="Party Logo" class="party-logo" />
      <h3>{{ party.name }}</h3>
      <p>{{ party.description }}</p>
      <button @click="viewDetails(party.id)">View Details</button>
    </div>
  </div>
</template>

<style scoped>
.party-card {
  margin: 10px;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.party-card img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 50%;
}

.party-card h3 {
  margin: 10px 0;
}

.party-card p {
  color: #666;
  font-size: 0.9rem;
}

.party-card button {
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.party-card button:hover {
  background-color: #0056b3;
}
</style>