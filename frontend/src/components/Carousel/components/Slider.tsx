import React from 'react'

interface Props {
  count: number,
  current: number
}

const getClassName = (index: number, current: number) => {
  const names = ['slider-item']
  if (index + 1 === current) {
    names.push('active')
  }
  return names.join(' ')
}

function CarouselSlider(props: Props): React.ReactElement {
  const { count, current } = props
  return (
    <div className="carouse-slider">
      {
        Array(count).fill(0).map((item, index) => {
          return <i key={index} className={getClassName(index, current)} />
        })
      }
    </div>
  )
}

export default React.memo(CarouselSlider)
