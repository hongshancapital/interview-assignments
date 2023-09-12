import classNames from 'classnames'

export interface IndicatorProps {
  count: number
  active: number
  duration: number
}

export function Indicator({ count, active, duration }: IndicatorProps) {
  return (
    <ul className='carousel-indicator-list'>
      {Array(count)
        .fill('')
        .map((_, index) => {
          return (
            <li
              key={index}
              className={classNames('carousel-indicator-item', {
                'carousel-indicator-item-filled': index < active,
                'carousel-indicator-item-active': active === index,
              })}
            >
              <div
                className='carousel-indicator-item-active-inner'
                style={{ animationDuration: `${duration}ms` }}
              />
            </li>
          )
        })}
    </ul>
  )
}
