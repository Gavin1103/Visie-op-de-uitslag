<script setup lang="ts">

import {RatingTypeEnum} from "@/models/enum/rating/RatingTypeEnum";
import type {Rating} from "@/models/ratings/Rating";
import {RatingService} from "@/services/RatingService";
import {onMounted, ref, onBeforeMount, type PropType} from "vue";
import type {AmountOfRatings} from "@/models/ratings/AmountOfRatings";
import {CookieService} from "@/services/CookieService";
import {UserService} from "@/services/UserService";
import {useToast} from "primevue/usetoast";

const ratingService: RatingService = new RatingService();
const cookieService = new CookieService();
const userService: UserService = new UserService();

const existingRating = ref<Rating>();
const amountOfRatings = ref<AmountOfRatings>();
const isUserLoggedIn = ref<boolean | null>(null)
const toast = useToast()

const props = defineProps({
  ratingTypeId: Number,
  ratingType: {
    type: Object as PropType<RatingTypeEnum>,
    required: true
  }
});

onBeforeMount(async () => {
  isUserLoggedIn.value = cookieService.tokenExists()
})

const fetchAmountOfRating = async () => {
  try {
    amountOfRatings.value = await ratingService.getAmountOfRatings(props.ratingTypeId!, props.ratingType!)
    await getExistingRating();
  } catch (error) {
    console.error("Error fetching rating:", error);
  }
};

const getExistingRating = async () => {
  if (isUserLoggedIn.value) {
    existingRating.value = await ratingService.hasRating(props.ratingTypeId!, props.ratingType!);
  }
}

onMounted(() => {
  fetchAmountOfRating();
});

const sendRating = async (ratingType: RatingTypeEnum, rating: boolean) => {
  if (!isUserLoggedIn.value) {
    toast.add({
      severity: 'info',
      summary: 'Log in to like or dislike',
      life: 5000
    })
  } else {
    const newRating: Rating = {
      ratingTypeId: props.ratingTypeId!,
      rating: rating,
    };
    await ratingService.rate(newRating, props.ratingType).then()
    {
      await fetchAmountOfRating();
    }
  }
}

</script>
<template>
  <section class="flex py-1 items-center">
    <img
        @click="sendRating(props.ratingType, true)"
        class="h-4 cursor-pointer hover:scale-110 transition-transform"
        :src="!existingRating
    ? '../../../public/rating/like-icon-trans.png'
    : existingRating.rating === true
    ? '../../../public/rating/green-thumbs-up.png'
    : '../../../public/rating/like-icon-trans.png'"
        alt="like-icon"
    />
    <p class="ml-1.5">{{ amountOfRatings?.likes }}</p>
    <img
        @click="sendRating(props.ratingType, false)"
        class="h-4 ml-1.5 cursor-pointer hover:scale-110 transition-transform"
        :src="!existingRating
    ? '../../../public/rating/dislike-icon-trans.png'
    : existingRating.rating === false
    ? '../../../public/rating/thumbs-down.png'
    : '../../../public/rating/dislike-icon-trans.png'"
        alt="dislike-icon"
    />
    <p class="ml-1.5">{{ amountOfRatings?.dislikes }}</p>
  </section>
</template>






