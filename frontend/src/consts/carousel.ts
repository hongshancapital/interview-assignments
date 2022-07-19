import iphone from '../assets/iphone.png'
import tablet from '../assets/tablet.png'
import airpods from '../assets/airpods.png'

export const HOME_CAROUSEL_DATA = [
    {
        id: 1,
        title: {
            text: 'xPhone',
            style: {
                color: 'white'
            }
        },
        subTitle: {
            text: 'Lots to love.Less to spend.\nStarting at $399',
            style: {
                color: 'white'
            }
        },
        img: iphone
    },
    {
        id: 2,
        title: {
            text: 'Tablet',
            style: {
                color: 'black'
            }
        },
        subTitle: {
            text: 'Just the right amount of everything',
            style: {
                color: 'black'
            }
        },
        img: tablet
    },
    {
        id: 3,
        title: {
            text: 'Buy a Tabelt or xPhone for college.',
            style: {
                color: 'black'
            }
        },
        subTitle: {
            text: 'Get arPods.',
            style: {
                color: 'black',
                fontSize: '50px',
                fontWeight: 500
            }
        },
        img: airpods
    }
];
