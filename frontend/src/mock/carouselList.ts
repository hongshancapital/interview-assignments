
import { CarouselList } from '../@types' 
import img1 from '../assets/iphone.png';
import img2 from '../assets/tablet.png';
import img3 from '../assets/airpods.png';
export const carouselList: CarouselList  = [
    {
        
        bgUrl: img1,
        data: [
            {
                contentType: 'p',
                className: 'title color-white',
                contentList: ['xPhone']
            },
            {
                contentType: 'div',
                className: 'text color-white',
                contentList: [
                    'Lots to love. Less to spend.',
                    'Starting at $399.'
                ]
            },
        ]
    },
    {
        bgUrl: img2,
        data: [
            {
                contentType: 'p',
                className: 'title',
                contentList: ['Tablet']
            },
            {
                contentType: 'div',
                className: 'text',
                contentList: [
                    'Just the right amount of everything.',
                ]
            },
        ],

    },
    {
        bgUrl: img3,
        data: [
            {
                contentType: 'p',
                className: 'title',
                contentList: ['Buy a Tablet or xPhone for college.', 'Get arPods.']
            },
        ]
    }

]
