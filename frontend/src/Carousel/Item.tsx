import { ReactNode } from "react";

interface Props {
  imgSrc: string;
  title?: ReactNode;
  subTitle?: ReactNode;
}

export default function Index({ imgSrc, title, subTitle }: Props) {
  return (
    <div
      className="item"
      style={{
        backgroundImage: `url(${imgSrc})`,
        backgroundRepeat: "no-repeat",
        backgroundPosition: "bottom",
        backgroundSize: "cover",
      }}
    >
      <div className="item-text">
        {title}
        {subTitle}
      </div>
    </div>
  );
}
