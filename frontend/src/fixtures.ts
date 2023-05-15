import iphoneUrl from './assets/iphone.png';
import tabletUrl from './assets/tablet.png';
import airdropUrl from './assets/airdrop.png';

const slides = [
  {
    title: 'xPhone',
    theme: 'bg-charcoal text-white',
    content: 'Lots to love. Less to spend.\nStarting at $399.',
    icon: {
      src: iphoneUrl,
      alt: 'iphone-poster',
    },
  },
  {
    title: 'Tablet',
    theme: 'bg-offwhite text-black',
    content: 'Just the right amount of everything.',
    icon: {
      src: tabletUrl,
      alt: 'tablet-poster',
    },
  },
  {
    title: 'Buy a Tablet or xPhone for collet.\nGet arPods.',
    theme: 'bg-ghostwhite text-black',
    icon: {
      src: airdropUrl,
      alt: 'airdrop-poster',
    },
  },
];

export { slides };
