import { Slide } from './Carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

export const slides: Slide[] = [
    {
        textInfos: [
            {
                type: 'title',
                text: 'xPhone',
            },
            {
                type: 'desc',
                text: 'Lots to love. Less to spend',
            },
            {
                type: 'desc',
                text: 'Starting at $399',
            },
        ],
        backgroundImage: iphone,
        extStyles: {
            backgroundColor: '#111111',
            color: '#fff',
        },
    },
    {
        textInfos: [
            {
                type: 'title',
                text: 'Tablet',
            },
            {
                type: 'desc',
                text: 'Just the right amount of everything.',
            },
        ],
        backgroundImage: tablet,
        extStyles: {
            backgroundColor: '#fafafa',
        },
    },
    {
        textInfos: [
            {
                type: 'title',
                text: 'Buy a Tablet or xPhone for college.',
            },
            {
                type: 'title',
                text: 'Get arPods.',
            },
        ],
        backgroundImage: airpods,
        extStyles: {
            backgroundColor: '#f1f1f3',
        },
    },
];
