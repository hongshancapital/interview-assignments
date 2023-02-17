import IPhonePic from '../assets/iphone.png';
import TabletPic from '../assets/tablet.png';
import AirPodsPic from '../assets/airpods.png';

export function getBannerInfo() {
  return [
    {
      id: 0,
      title: 'xPhone',
      text: 'Lots to love.Less to spend.\nStarting at $399.',
      img: IPhonePic,
      color: '#fff',
      backgroundColor: '#101010',
    },
    {
      id: 1,
      title: 'Tablet',
      text: 'Just the right amount of everything.',
      img: TabletPic,
      color: '#000',
      backgroundColor: '#fafafa',
    },
    {
      id: 2,
      title: 'Buy a Tablet or a xPhone for college.\nGet arPods.',
      img: AirPodsPic,
      color: '#000',
      backgroundColor: '#f2f2f4',
    },
  ];
}
