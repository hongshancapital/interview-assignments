import React, {ReactElement} from "react";

interface Props {
  width: string
  height: string
  children: ReactElement
}

export function ItemWrapper(props: Props) {
  const { width, height, children } = props
  return <div className="item" style={{ width, height }} data-testid="item">
    { children }
  </div>
}
