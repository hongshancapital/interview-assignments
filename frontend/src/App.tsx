import React, { memo } from "react";
import "./App.css";
import { DURING, CONFIG } from "./constant";
import { IConfig } from "./typings";

const Card = memo(({
  title = "",
  content = [],
  fontColor = "#000",
  backgroundImage = "",
}: IConfig) => {
  console.log('backgroundImage', backgroundImage);

  const style: React.CSSProperties = {
    color: fontColor,
  }

  return (
    <section style={style}>
      <div className="title">{title}</div>
      <div className="content">
        {
          content.map((text, index) => <p key={`${index}-${text}`} className="text">{text}</p>)
        }
      </div>
    </section>
  );
});

function App() {
  return (
    <div className="App">
      {
        CONFIG.map((config, index) => {
          const { title, content, fontColor, backgroundImage} = config;
          return <Card
            key={`${index}-${title}`}
            title={title}
            content={content}
            fontColor={fontColor}
            backgroundImage={backgroundImage}
          />
        })
      }
    </div>
  );
}

export default App;
