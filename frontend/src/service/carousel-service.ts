import {CarouselConfig} from '../interface/carousel-interface'

export function getCarouselData():CarouselConfig {
    return {
        slides: [
            {
                slideId: 1,
                bgColor: 'black',
                descContent: [
                    {
                        content: 'xPhone',
                        style: {
                            color: 'white',
                            fontSize: '40px',
                            fontWeight: 'bold',
                            lineHeight: '60px',
                        }
                    },
                    {
                        content: 'Lots to love.Less to spend.',
                        style: {
                            color: 'white',
                            fontSize: '30px',
                            fontWeight: 'normal',
                            lineHeight: '40px',
                            marginTop: '15px',
                        }
                    },
                    {
                        content: 'Starting at $399.',
                        style: {
                            color: 'white',
                            fontSize: '30px',
                            fontWeight: 'normal',
                            lineHeight: '40px',
                        }
                    },
                ],
                pic: {
                    url: require('../assets/iphone.png'),
                    width: '100px',
                    height: '100px',
                }
            },
            {
                slideId: 2,
                bgColor: 'white',
                descContent: [
                    {
                        content: 'Tablet',
                        style: {
                            color: 'black',
                            fontSize: '40px',
                            fontWeight: 'bold',
                            lineHeight: '60px',
                        }
                    },
                    {
                        content: 'Just the right amount of everything.',
                        style: {
                            color: 'black',
                            fontSize: '30px',
                            fontWeight: 'normal',
                            lineHeight: '40px',
                            marginTop: '15px',
                        }
                    },
                ],
                pic: {
                    url: require('../assets/tablet.png'),
                    width: '100px',
                    height: '100px',
                }
            },
            {
                slideId: 3,
                bgColor: 'white',
                descContent: [
                    {
                        content: 'Buy a Tablet or xPhone for college.',
                        style: {
                            color: 'black',
                            fontSize: '40px',
                            fontWeight: 'bold',
                            lineHeight: '60px',
                        }
                    },
                    {
                        content: 'Get arPods.',
                        style: {
                            color: 'black',
                            fontSize: '40px',
                            fontWeight: 'bold',
                            lineHeight: '60px',
                        }
                    },
                ],
                pic: {
                    url: require('../assets/airpods.png'),
                    width: '100px',
                    height: '100px',
                }
            },
        ]
    }
}