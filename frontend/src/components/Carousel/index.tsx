import React, { CSSProperties } from 'react'
import './index.css'

type CarouselType = {
  className?: string
  loopGapDelay?: number
  style?: CSSProperties
  contents: React.ReactNode[]
}
let timer: any = null
/**
 * @param props CarouselType
 */
function Index(props: CarouselType) {
  const { className, contents, style, loopGapDelay } = props

  const [currentIndex, setCurrentIndex] = React.useState<number>(0)

  React.useEffect(() => {
    if (timer) {
      clearTimeout(timer)
      timer = null
    }
    timer = setTimeout(() => {
      clearTimeout(timer)
      timer = null
      if (currentIndex + 1 >= contents.length) {
        setCurrentIndex(0)
      } else {
        setCurrentIndex(currentIndex + 1)
      }
    }, loopGapDelay || 3000)
  }, [currentIndex])

  const renderContent = (datas: React.ReactNode[]) => (
    <div
      className="custom-carousel-contents-container"
      data-testid="custom-carousel-container"
      style={{
        width: `${datas.length * 100}%`,
        transform: `translateX(-${(currentIndex * 100) / contents.length}%)`,
      }}
    >
      {datas.map((data, index: number) => (
        <div
          className="custom-carousel-single-container"
          key={`custom-carousel-cell-${index}`}
        >
          {data}
        </div>
      ))}
    </div>
  )

  const renderBottomButtons = (datas: React.ReactNode[]) => {
    return (
      <div className={'custom-carousel-cells-container'}>
        {datas.map((nil: any, index: number) => {
          return (
            <div
              key={`custom-carousel-dot-${index}`}
              className={[
                'custom-carousel-cell-container',
                index === currentIndex ? 'active-dot' : '',
              ].join(' ')}
              onClick={() => setCurrentIndex(index)}
              data-testid={`test-carousel-dot-${index}`}
            ></div>
          )
        })}
      </div>
    )
  }

  const summaryClassNames: string = [
    'custom-carousel-container',
    className || '',
  ].join(' ')
  return (
    <div className={summaryClassNames} style={style || {}}>
      {renderContent(contents)}
      {renderBottomButtons(contents)}
    </div>
  )
}

export default Index
