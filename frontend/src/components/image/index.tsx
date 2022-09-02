import React, { FC, useMemo } from "react";
import { type imageType } from 'src/interfaces';
import "./index.scss";

export interface Props {
  src: string,
  type: imageType;
}

const Image: FC<Props> = props => {
  const { src, type } = props;

  const style = useMemo(() => ({
    backgroundImage: `url(${src})`,
  }), [src]);

  return (
    <div
      className={`image-outter ${type}`}
      style={style}
      data-testid={type} />
  );
}

export default Image;
