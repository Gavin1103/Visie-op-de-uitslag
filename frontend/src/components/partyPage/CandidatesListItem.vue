<script setup lang="ts">

import type { CandidateWithVotes } from '@/models/Candidate'

const props = defineProps<{
  candidate: CandidateWithVotes
}>()
</script>

<template>
  <div class="flex justify-between items-center">
    <span class="candidate-name font-medium text-lg">{{ candidate.fullName }}</span>
    <span class="candidate-votes text-gray-600">{{ candidate.votes }} stemmen</span>
  </div>
</template>

<style scoped>

</style>