import React, { FC, useMemo } from "react";
import { PageItem } from "src/interfaces";
import Image from "src/components/image";
import "./index.scss";

const Page: FC<PageItem> = props => {
  const {
    title,
    subtitle,
    icon,
    fontColor,
    backgroundColor,
    imageSrc,
  } = props;

  const style = useMemo(() => ({
    backgroundColor
  }), [backgroundColor]);

  const titleStyle = useMemo(() => ({
    color: fontColor
  }), [fontColor]);

  const subtitleStyle = useMemo(() => ({
    color: fontColor
  }), [fontColor])

  const TitleComponent = useMemo(() => {
    return <div className="page-title">
        {title?.map((el, i) => <div key={i} className="page-title-item" style={titleStyle}>{el}</div>)}
      </div>

  }, [titleStyle, title]);


  const SubTitleComponent = useMemo(() => {
    return subtitle?.map((el, i) => <div key={i} className="page-subtitle" style={subtitleStyle}>{el}</div>);
  }, [subtitle, subtitleStyle]);

  return <div className="page-item" style={style}>
    {TitleComponent}
    {SubTitleComponent}
    <Image src={imageSrc} type={icon} />
  </div>;
}

export default Page;
