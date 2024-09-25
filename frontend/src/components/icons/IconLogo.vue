<script setup lang="ts">
const props = defineProps<{
  width: string;
  height: string;
}>();
</script>

<template>
  <section :class="`${width} ${height} flex items-center`">
    <img alt="logo" src='../../../public/img.png'>
  </section>

</template>

<style scoped>

</style>