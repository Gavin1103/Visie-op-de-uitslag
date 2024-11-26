<script setup lang="ts">

import {RatingTypeEnum} from "@/models/enum/rating/RatingTypeEnum";
import type {Rating} from "@/models/ratings/Rating";
import {RatingService} from "@/services/RatingService";
import {onMounted, ref} from "vue";

const ratingService: RatingService = new RatingService();

const rating = ref<Rating | null>(null); // Initialize with null

const props = defineProps({
  ratingTypeId: Number,
  likes: Number,
  dislikes: Number,
  ratingType: RatingTypeEnum
});

const fetchData = async () => {
  try {
    rating.value = await ratingService.hasRating(props.ratingTypeId, props.ratingType);
    console.log("Fetched Rating:", rating.value);
  } catch (error) {
    console.error("Error fetching rating:", error);
  }
};

onMounted(() => {
  fetchData();
});
</script>

<template>
  <section v-if="!rating">
    Loading...
  </section>

  <section v-else class="flex py-1 items-center">
    <img
        class="h-4"
        :src="rating.rating === true
        ? '../../../public/rating/green-thumbs-up.png'
        : '../../../public/rating/like-icon-trans.png'"
        alt="like-icon"
    />
    <p class="ml-1.5">{{ likes }}</p>

    <img
        class="h-4 ml-1.5"
        :src="rating.rating === false
        ? '../../../public/rating/thumbs-down.png'
        : '../../../public/rating/dislike-icon-trans.png'"
        alt="dislike-icon"
    />
    <p class="ml-1.5">{{ dislikes }}</p>

  </section>
</template>






