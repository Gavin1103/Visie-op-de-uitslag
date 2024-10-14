<script  lang="ts">

import { useRoute } from 'vue-router'

export default {
  setup() {
    const route = useRoute();
    const id = route.params.id;

    return {
      id
    }
  }
}
</script>

<template>
  <div class="text-7xl">{{id}}</div>
</template>

<style scoped>

</style>