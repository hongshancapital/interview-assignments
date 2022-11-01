import * as React from "react";
import { BaseComponent } from "./types";

interface PaperProps extends BaseComponent {
  renderFloatingLayer?: () => React.ReactNode;
}
const Paper: React.FC<PaperProps> = (props) => {
  const { renderFloatingLayer, children, className } = props;
  return (
    <div className={`paper ${className ?? ""}`}>
      {renderFloatingLayer && (
        <div className="floating-layer">{renderFloatingLayer()}</div>
      )}
      <div className="paper-content">{children}</div>
    </div>
  );
};

Paper.displayName = "Paper";

export default Paper;
