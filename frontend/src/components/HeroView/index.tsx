import React from "react";
import "./index.css";

interface Props {
  title: string;
  subtitle?: string;
  backgroundImageURL: string;
  theme?: "light" | "dark";
}

const HeroView = ({
  title,
  subtitle,
  backgroundImageURL,
  theme = "light",
}: Props) => {
  const color = theme === "dark" ? "white" : "black";

  return (
    <section
      className="hero-view"
      style={{ backgroundImage: `url(${backgroundImageURL})` }}
    >
      <h1 style={{ color }}>{title}</h1>
      <h3 style={{ color }}>{subtitle}</h3>
    </section>
  );
};

export default HeroView;
