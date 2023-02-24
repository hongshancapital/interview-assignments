import iPhone from '../assets/image/iphone.png'
import tablet from '../assets/image/tablet.png'
import airpods from '../assets/image/airpods.png'
export function getBannerList() {
  return [
    { title: 'xPhone', desc: 'Lots to love. Less to spend.\r\nStarting at $399.', url: iPhone, light: true },
    { title: 'Tablet', desc: 'Just the right amount of everything.', url: tablet, light: false },
    { title: 'Buy a Tablet or xPhone for college.\r\nGet airpods.', desc: '', url: airpods, light: false },
  ]
}
