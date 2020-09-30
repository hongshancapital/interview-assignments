declare module "@~/utils" {
  import * as React from "react";
  export type ElementType<P = any> = {
    [K in keyof JSX.IntrinsicElements]: JSX.IntrinsicElements[K] extends P ? K : never
  }[keyof JSX.IntrinsicElements] | React.ComponentType<P>;
}

declare module "@testing-library/jest-dom/extend-expect" {
}
