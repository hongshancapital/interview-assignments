/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{ts,tsx}'],
  theme: {
    extend: {
      fontSize: {
        title: '3.5rem', // 56px
        content: '1.75rem', // 28px
      },
      colors: {
        charcoal: 'rgb(14, 14, 14)',
        offwhite: 'rgb(245, 245, 245)',
        ghostwhite: 'rgb(239, 239, 241)',
        silver: 'rgb(153, 153, 153)',
      },
    },
  },
  plugins: [],
};
