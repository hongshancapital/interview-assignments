import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';

export interface CarouselInfo{
    carouselTitle: Array<String>;
    carouselContent?: Array<String>;
    carouselImgUrl: string;
    fontColor: string;
}

// 模拟后台返回数据
export const carouselData = [
    {
        carouselTitle: ['xPhone'],
        carouselContent: ['Lots to love.Less to spend.','Starting at $399.'],
        carouselImgUrl: iphone,
        fontColor: 'white',
    },
    {
        carouselTitle: ['Tablet'],
        carouselContent: ['Just the right amount of everything.'],
        carouselImgUrl: tablet,
        fontColor: 'black',
    },
    {
        carouselTitle: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
        carouselContent: [],
        carouselImgUrl: airpods,
        fontColor: 'black',
    }
]