import React from 'react'
import './index.css';

interface GoodsCardProps {
  readonly style?: React.CSSProperties
  goodsImg: string
  title: string | React.ReactNode
  desc: string | React.ReactNode
}

const GoodsCard: React.FC<GoodsCardProps> = (props) => {
  const { goodsImg, title, desc, style } = props

  const renderStringOrReactNode = (
    stringOrNode: string | React.ReactNode,
    className: string
  ) => {
    if (typeof stringOrNode === 'string') {
      return <div className={className}>{stringOrNode}</div>
    }
    return stringOrNode
  }

  return (
    <div className='goods-card__contain' style={style}>
      <img src={goodsImg} className='goods-card__img' alt='xx' />
      <div className='goods-card__info'>
        {renderStringOrReactNode(title, 'goods-card__title')}
        {renderStringOrReactNode(desc, 'goods-card__desc')}
      </div>
    </div>
  )
}

export default GoodsCard
