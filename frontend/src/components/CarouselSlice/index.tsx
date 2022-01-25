import React, {useState} from "react";
import "./index.scss";

/**
 * The carousel slice
 * @param {CarouselSliceProps} props  the carousel slice props
 */
function CarouselSlice(props: CarouselSliceProps): JSX.Element {
  const {
    lazy,
    children,
  } = props;

  const [rendered, setRendered] = useState<boolean>(false);
  let lazyRendering = lazy;
  if (rendered) {
    // does not lazy if it is already rendered
    lazyRendering = false;
  } else if (!lazyRendering) {
    // the rendered state can be changed only once
    setRendered(true);
  }

  return (
    <div className="carousel-slice">
      {lazyRendering ? null : children}
    </div>
  );
}

export default CarouselSlice;
