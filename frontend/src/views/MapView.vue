<!-- NetherlandsMap.vue -->
<template>
  <div class="map-container">
    <ProvinceMap @click="handleProvinceClick" />
    <PopupMap
        v-if="showPopup"
        :title="selectedProvince"
        @close="showPopup = false"
    >
      <p>Details about {{ selectedProvince }} go here.</p>
    </PopupMap>
  </div>
</template>

<script>
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
    };
  },
  methods: {
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
