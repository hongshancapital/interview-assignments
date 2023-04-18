/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}"
  ],
  theme: {
    extend: {
      backgroundImage: {
        'airpods': "url('./assets/airpods.png')",
        'iphone': "url('./assets/iphone.png')",
        'tablet': "url('./assets/tablet.png')",
      }
    },
  },
  plugins: [],
}

