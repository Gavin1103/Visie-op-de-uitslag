<template>
  <div class="unauthorized-container">
    <h1>401 - Unauthorized</h1>
    <p>You do not have access to this page.</p>
    <p>Redirecting to the home page in 3 seconds...</p>
  </div>
</template>

<script>
export default {
  name: 'Unauthorized',
  mounted() {
    setTimeout(() => {
      this.$router.push('/');
    }, 3000);
  }
}
</script>

<style scoped>
.unauthorized-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 50vh;
  color: #495057;
  text-align: center;
}

h1 {
  font-size: 3rem;
  color: #dc3545;
}

p {
  font-size: 1.2rem;
  margin-top: 10px;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.unauthorized-container {
  animation: fadeIn 1s ease-in;
}
</style>
