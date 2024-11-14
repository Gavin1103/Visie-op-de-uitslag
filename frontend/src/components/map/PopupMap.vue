<template>
  <div class="popup-overlay" @click.self="closePopup">
    <div class="popup-content">
      <button class="close-btn" @click="closePopup">X</button>
      <h2>{{ title }}</h2>
      <ul>
        <li v-for="region in filteredRegions" :key="region.regionNumber">
          {{ region.regionName }}
        </li>
      </ul>
      <ul>
        <li v-for="party in votingData" :key="party.partyId">
          {{ party.partyName }}: {{ party.totalVotes }}
        </li>
      </ul>
      <slot></slot>
    </div>
  </div>
</template>

<script>
import {kieskringenData} from '@/components/map/kieskringData';

export default {
  name: "PopupMap",
  props: {
    title: {
      type: String,
      required: true,
    },
    votingData: {
      type: Array,
      required: true,
    },
  },
  computed: {
    filteredRegions() {
      const normalizedTitle = this.normalizeString(this.title);

      return kieskringenData.filter(region => {
        const normalizedProvince = this.normalizeString(region.province);
        return normalizedProvince === normalizedTitle;
      });
    },
  },
  methods: {
    normalizeString(str) {
      return str
          .trim()
          .toLowerCase()
          .normalize('NFD')
          .replace(/[\u0300-\u036f]/g, "");
    },
    closePopup() {
      this.$emit('close');
    },
  },
};
</script>

<style scoped>
.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.popup-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 400px;
  width: 100%;
  position: relative;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: transparent;
  border: none;
  font-size: 18px;
  cursor: pointer;
}

ul {
  padding-left: 20px;
}

li {
  margin-bottom: 5px;
}
</style>
