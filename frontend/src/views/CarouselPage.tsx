import React from 'react'
import Carousel, { CardItem } from '../components/Carousel'
import RenderItem from '../components/RenderItem'
import iphone from '../assets/iphone.png'
import tablet from '../assets/tablet.png'
import airpods from '../assets/airpods.png'

const cardsData: CardItem[] = [
    {
        key: 1,
        url: iphone,
        title: 'xPhone',
        color: '#fff',
        subTitle: '',
        text: 'Lots to love.Less to spend.',
        desc: 'Starting at $339.'
    },
    {
        key: 2,
        url: tablet,
        title: 'Tablet',
        color: '#000',
        text: 'Just the right amount of everything.'
    },
    {
        key: 3,
        url: airpods,
        title: 'Buy a Tablet or xPhone for college',
        color: '#000',
        subTitle: 'Get arpods.'
    },
]

function CarouselPage () {
    return (
        <Carousel>
            {
                cardsData.map((item: CardItem) => (
                    <RenderItem item={ item } key={ item.key }/>
                ))
            }
        </Carousel>
    )
}

export default CarouselPage