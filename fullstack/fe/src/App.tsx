import { useState, useCallback, useRef, useMemo } from "react";

import { useApi } from "./hooks";
import * as apis from "./apis";

import "./App.css";

export function App() {
  const [long, set_long] = useState("");
  const long_to_short = useApi(apis.long_to_short);
  const short = long_to_short.res?.data.data.short;
  const short_url = window.location.origin + "/" + short;
  return (
    <div className="App">
      <h1>ShortURL</h1>
      <div>
        <p>
          <input
            type="text"
            placeholder="输入长链接"
            value={long}
            onChange={(e) => set_long(e.target.value)}
          />
        </p>
        <button onClick={() => long_to_short.fn({ long })}>转为短链</button>
        {short && (
          <p>
            <code>{short_url}</code>{" "}
            <button
              onClick={() => window.navigator.clipboard.writeText(short_url)}
            >
              复制到粘贴板
            </button>
          </p>
        )}
      </div>
    </div>
  );
}
