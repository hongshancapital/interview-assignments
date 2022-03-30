import React from "react";
import classNames from "classnames";

import styles from "./styles.module.scss";

export function Page({ theme, className, ...hostProps }: PageProps) {
  return (
    <div
      className={classNames([
        className,
        styles.page,
        theme === "light" ? styles.isLight : null
      ])}
      {...hostProps}
    />
  );
}

export type PageProps = JSX.IntrinsicElements["div"] & {
  /** 主题，默认 dark */
  theme?: "light" | "dark";
};
