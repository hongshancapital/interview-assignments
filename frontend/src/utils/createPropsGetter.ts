/**
 * React组件defaultProps辅助函数
 * @returns
 */
export const createPropsGetter = <DP extends object>() => {
  // 通过闭包的方式记住了defaultProps的类型DP
  return <P extends Partial<DP>>(props: P) => {
    // 实际就是取出P类型中必填的部分 - 必填属性类型
    type PropsExcludeDefaultProps = Pick<P, Exclude<keyof P, keyof DP>>
    // 将默认属性的类型（非可选）重组
    type RecomposedProps = DP & PropsExcludeDefaultProps

    return props as unknown as RecomposedProps
  }
}
