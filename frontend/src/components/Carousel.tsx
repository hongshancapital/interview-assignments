import React from 'react'

const Carousel = (props: any) => {
  // active 当前轮播激活的索引
  const [active, setActive] = React.useState(0)
  const style: any = {
    carousel: {
      position: 'relative'
    },
    carouselItem: {
      position: 'absolute',
      width: '100%',
      height: '100vh',
      visibility: 'hidden'
    },
    visible: {
      visibility: 'visible'
    }
  }
  React.useEffect(() => {
    // 将 active 的值更新为下一个项的索引
    setTimeout(() => {
      const { carouselItems } = props
      // 因为 active 在 render 中使用了， active 改变会影响视图而重新渲染，所以也会再次触发 useEffect
      setActive((active + 1) % carouselItems.length)
    }, 3000)
  })
  const { carouselItems, ...rest } = props
  return (
    <div style={style.carousel}>
      {carouselItems.map((item: any, index: any) => {
        // 激活就显示，否则隐藏
        const activeStyle = active === index ? style.visible : {}
        // 克隆出一个组件来渲染
        return React.cloneElement(item, {
          ...rest,
          style: {
            ...style.carouselItem,
            ...activeStyle
          },
          key: index
        })
      })}
    </div>
  )
}

export default Carousel
