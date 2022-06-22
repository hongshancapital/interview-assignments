import { useRef } from 'react'

export const noop = () => {}

// 配合 useMemo/Effect 作为依赖，适用于运行时访问的变量，只关注其他依赖变化，不用关注 Temporary 内部变化
export const useTemporary = <D>(initData: D): [D, (d: Partial<D>) => void] => {
  const ref = useRef(initData)
  return [
    ref.current,
    (d) => {
      Object.assign(ref.current, d)
    }
  ]
}
