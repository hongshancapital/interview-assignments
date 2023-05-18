import classNames from 'classnames'

export interface IPageIndicatorProps {
  activeIndex: number
  interval: number
  index: number
  onClick?: () => void
}

export default function PageIndicator(props: IPageIndicatorProps) {
  const { interval, activeIndex, index, onClick } = props
  return (
    <div className="indicator" onClick={onClick}>
      <div
        className={classNames('indicator-counter', {
          'indicator-counter-anime': activeIndex === index,
        })}
        style={{
          animationDuration: `${interval}s`,
        }}
      ></div>
    </div>
  )
}
