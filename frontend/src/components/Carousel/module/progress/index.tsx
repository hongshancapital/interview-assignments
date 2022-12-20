import style from './index.module.scss'

export default function Progress({
  max,
  value,
  color
}: {
  max: number
  value: number
  color?: string
}) {
  // return <progress value={value} max={max} />
  return (
    <div className={style.progress}>
      <span
        style={{
          width: `${(value / max) * 100}%`,
          background: color || '#fff'
        }}
      />
    </div>
  )
}
