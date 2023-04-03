import { useState } from "react";
import { SERVICE_URL } from "./config";

export const LongToShort = () => {
  const [URL, setURL] = useState("");
  const [shortLink, setShortLink] = useState("");
  function handleClick() {
    fetch(SERVICE_URL, {
      method: "POST",
      body: JSON.stringify({ url: URL }),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.text())
      .then(setShortLink);
  }
  return (
    <div>
      <h2>长链接转短链接</h2>
      <input type="text" onChange={(e) => setURL(e.target.value)} value={URL} />
      <button onClick={handleClick}>转换</button>
      <p>结果：{shortLink}</p>
    </div>
  );
};
