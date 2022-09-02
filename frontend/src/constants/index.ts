import { PageItem, imageEnum } from "src/interfaces";
import iphonePng from 'src/assets/iphone.png';
import tabletPng from 'src/assets/tablet.png';
import airpodsPng from 'src/assets/airpods.png';

export const CarouselList: PageItem[] = [{
    title: ["xPhone"],
    subtitle: ["Lots to love. Less to spend.", "Starting at $399."],
    icon: imageEnum.iphone,
    imageSrc: iphonePng,
    fontColor: '#FFF',
    backgroundColor: '#000',
}, {
    title: ["Tablet"],
    subtitle: ["Just the right amount of everything."],
    icon: imageEnum.tablet,
    imageSrc: tabletPng,
    fontColor: '#000',
    backgroundColor: '#FFF',
}, {
    title: ["Buy a Tablet or xPhone for college.", "Get airPods."],
    icon: imageEnum.airpods,
    imageSrc: airpodsPng,
    fontColor: '#000',
    backgroundColor: '#EEE',
}];

export const defaultActiveIndex = 0;
export const defaultAnimationDuration = '3s';
export const defaultAnimationMoveDuration = '0.5s';

export const animationProps = {
  animationDuration: defaultAnimationDuration,
  animationMoveDuration: defaultAnimationMoveDuration,
  activeIndex: defaultActiveIndex,
}
