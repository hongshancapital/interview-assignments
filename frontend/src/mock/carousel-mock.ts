import {CarouselConfig} from '../interface/carousel-interface'

export const mockData:CarouselConfig = {
    slides: [
        {
            bgColor: 'black',
            descContent: [
                {
                    content: 'xPhone',
                    type: 'title'
                },
                {
                    content: 'Lots to love.Less to spend.',
                    type: 'desc'
                },
                {
                    content: 'Starting at $399.',
                    type: 'desc'
                },
            ],
            pic: {
                url: require('../assets/iphone.png'),
            }
        },
        {
            bgColor: 'white',
            descContent: [
                {
                    content: 'Tablet',
                    type: 'title'
                },
                {
                    content: 'Just the right amount of everything.',
                    type: 'desc'
                },
            ],
            pic: {
                url: require('../assets/tablet.png'),
            }
        },
        {
            bgColor: 'white',
            descContent: [
                {
                    content: 'Buy a Tablet or xPhone for college.',
                    type: 'desc'
                },
                {
                    content: 'Get arPods.',
                    type: 'desc'
                },
            ],
            pic: {
                url: require('../assets/airpods.png'),
            }
        },
    ]
}