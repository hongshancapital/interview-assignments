import React from "react";
import "./progress.css";

type ComponentProps = React.PropsWithChildren<{
  backgroundColor: string;
  foregroundColor: string;
  percent: number;
}> &
  React.DetailedHTMLProps<React.HTMLAttributes<HTMLDivElement>, HTMLDivElement>;

export const Progress: React.FC<ComponentProps> = ({
  backgroundColor,
  foregroundColor,
  percent,
}) => {
  const bgStyle: React.CSSProperties = {
    backgroundColor: backgroundColor,
  };

  const fgStyle: React.CSSProperties = {
    backgroundColor: foregroundColor,
    width: `${Math.min(100, Math.max(0, percent))}%`,
  };
  return (
    <div className="progress-container" style={bgStyle}>
      <div className="progress" style={fgStyle}></div>
    </div>
  );
};
