import * as React from "react";
import { useCarouselNavigate } from "./Carousel";
import { BaseComponent } from "./types";

interface PaperProps extends BaseComponent {
  renderFloatingLayer?: () => React.ReactNode;
}
const Paper: React.FC<PaperProps> = (props) => {
  const { renderFloatingLayer, children } = props;
  const { gotoPrev } = useCarouselNavigate();
  return (
    <div className="paper" onClick={gotoPrev}>
      {renderFloatingLayer && <div>{renderFloatingLayer()}</div>}
      <div>{children}</div>
    </div>
  );
};

Paper.displayName = "Paper";

export default Paper;
