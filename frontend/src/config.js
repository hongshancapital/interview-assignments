import AirPodsImg from './assets/airpods.png';
import IPhoneImg from './assets/iphone.png';
import TabletImg from './assets/tablet.png';

/* 轮播的文案和图片配置 */
const content = [
    {
        name: 'iphone',
        src: IPhoneImg,
        description: {
            title: [
                'xPhone',
            ],
            comment: [
                'Lots to Love. Less to spend.',
                'Starting at $399.'
            ],
            theme: 'light'
        },
    },
    {
        name: 'tablet',
        src: TabletImg,
        description: {
            title: [
                'Tablet',
            ],
            comment: [
                'Just the right amount of everything.',
            ],
        },
    },
    {
        name: 'airpods',
        src: AirPodsImg,
        description: {
            title: [
                'Buy a Tablet or xPhone for collage.',
                'Get airPods.'
            ],
            comment: [],
        },
    },
];

/* 轮播组件的使用配置 */
const config = {
    interval: 3000,
    loop: true,
    auto: true,
    progressBgColor: '#ddd',
    progressBarColor: '#fff',
    progressPosition: '20px',
    containerHeight: '400px',
};

export {
    content,
    config,
}; 



