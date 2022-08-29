import React, { FC } from "react";
import { imageType } from "../../interfaces";
import "./index.scss";

export interface Props {
    imageType: imageType,
}

const Image: FC<Props> = props => {
    const { imageType } = props;
   return <div className={`image-outter ${imageType}`}></div>
}

export default Image;
