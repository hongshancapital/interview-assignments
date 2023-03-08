/// <reference types="react-scripts" />
declare module '*.png' {
  const src: string
  export default src
}

declare type ValidReactChild = (
    | string
    | number
    | React.ReactElement
    | React.ReactFragment
    | React.ReactPortal
  )
