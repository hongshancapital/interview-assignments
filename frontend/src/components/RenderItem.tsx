import React from 'react'
import { CardItem } from './Carousel'
import styles from './RenderItem.module.css'

function RenderItem ({ item }: { item: CardItem }) {
    return (
        <div className={ styles['item-body'] }>
            <div className={ styles['con'] } style={{color: item.color}}>
                { item.title && <p className={ styles['title'] }>{ item.title }</p> }
                { item.subTitle && <p className={ styles['title'] }>{ item.subTitle }</p> }
                { item.text && <p className={ styles['text'] }>{ item.text }</p> }
                { item.desc && <p className={ styles['text'] }>{ item.desc }</p> }
            </div>
            <img src={ item.url } alt={ item.title } />
        </div>
    )
}

export default RenderItem