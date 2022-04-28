// react-unstable-attributes.d.ts
import "react";

declare module "react" {
  interface ImgHTMLAttributes extends HTMLAttributes {
    fetchpriority?: "high" | "low" | "auto";
  }
}
