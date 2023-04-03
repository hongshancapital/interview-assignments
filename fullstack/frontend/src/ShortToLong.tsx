import { useState } from "react";
import { SERVICE_URL } from "./config";

export const ShortToLong = () => {
  const [slug, setSlug] = useState("");
  const [URL, setURL] = useState("");
  function handleClick() {
    fetch(`${SERVICE_URL}/full/${slug}`, {
      method: "GET",
    })
      .then((res) => res.text())
      .then(setURL);
  }
  return (
    <div>
      <h2>短链接转长链接</h2>
      <input
        type="text"
        onChange={(e) => setSlug(e.target.value)}
        value={slug}
      />
      <button onClick={handleClick}>转换</button>
      <p>结果：{URL}</p>
    </div>
  );
};
