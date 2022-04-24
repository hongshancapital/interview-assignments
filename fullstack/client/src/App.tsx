import React, { useEffect, useState } from "react";
import "./App.css";

const serverUrl = "http://localhost:3003/";
function App() {
  const generateUrl = async () => {
    const res = await fetch(`${serverUrl}api/generate`, {
      method: "POST",
      headers: {
        "content-type": "application/json;charset=UTF-8",
      },
      mode: "cors",
      body: JSON.stringify({
        longUrl,
      }),
    });
    const { code } = await res.json();
    setTinyUrl(`${serverUrl}${code}`);
  };
  const [tinyUrl, setTinyUrl] = useState("");
  const [longUrl, setLongUrl] = useState("");
  useEffect(() => {});
  return (
    <div className="App">
      <textarea
        rows={6}
        style={{ width: "300px" }}
        onChange={(e) => {
          setLongUrl(e.target.value);
        }}
      />
      <button
        onClick={() => {
          generateUrl();
        }}
      >
        生成
      </button>

      <a target={"_blank"} href={tinyUrl} rel="noreferrer">
        {tinyUrl}
      </a>
    </div>
  );
}

export default App;
