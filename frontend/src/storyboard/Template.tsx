import * as React from "react";
import classNames from "classnames";

import "./Template.css";

interface TemplateProps {
  className?: string;
  title?: string[];
  titleClassName?: string;
  content?: string[];
  contentClassName?: string;
  imgClassName?: string;
  imgSrc?: string;
}

export const Template: React.FC<TemplateProps> = ({
  imgSrc,
  imgClassName,
  className,
  title,
  titleClassName,
  content,
  contentClassName,
}) => {
  const imgRef = React.useRef<HTMLImageElement | null>(null);
  const canvasRef = React.useRef<HTMLCanvasElement | null>(null);
  const [boxStyle, setBoxStyle] = React.useState<React.CSSProperties>({});

  React.useEffect(() => {
    const calcRgba = () => {
      if (canvasRef.current && imgRef.current) {
        canvasRef.current.width = imgRef.current.width;
        canvasRef.current.height = imgRef.current.height;
        const ctx = canvasRef.current.getContext("2d");
        imgRef.current.onload = () => {
          if(canvasRef.current && imgRef.current){
            ctx?.drawImage(
              imgRef.current,
              0,
              0,
              canvasRef.current.width,
              canvasRef.current.height
            );
            const imgData = ctx?.getImageData(
              0,
              0,
              canvasRef.current.width,
              canvasRef.current.height
            );
            const backgroundColor = `rgba(${imgData?.data
              .slice(0, 4)
              .join(",")})`;
            setBoxStyle({ backgroundColor });
          }
        };
      }
    };
    calcRgba();
  }, []);

  return (
    <div className={classNames("template-box", className)} style={boxStyle}>
      <div className="template-header">
        {title?.map((item: string) => (
          <div
            key={item}
            className={classNames("template-header-title", titleClassName)}
          >
            {item}
          </div>
        ))}
        {content?.map((item: string) => (
          <div
            key={item}
            className={classNames("template-header-content", contentClassName)}
          >
            {item}
          </div>
        ))}
      </div>
      <img
        src={imgSrc}
        alt={imgSrc}
        className={classNames("template-img", imgClassName)}
        ref={imgRef}
      />
      <canvas
        width="100%"
        height="100%"
        style={{ display: "none" }}
        ref={canvasRef}
      />
    </div>
  );
};
