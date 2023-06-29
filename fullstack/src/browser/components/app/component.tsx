import React, { useState } from "react";

import { fetchShortlink } from "../../services/index.js";

export function App() {
  const [shortlink, setShortlink] = useState<string | undefined>();

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.target as HTMLFormElement);

    const originLink = formData.get("originLink") as string;

    try {
      const shortlinkURL = await fetchShortlink(originLink);
      setShortlink(shortlinkURL);
    } catch (e: any) {
      alert(e.message);
    }
  };

  return (
    <div>
      Welcome to use Node short link!
      <form onSubmit={onSubmit} role="form">
        <input type="text" name="originLink" alt="original link input" />

        <button type="submit">提交</button>
      </form>
      {shortlink && <a href={shortlink}>{shortlink}</a>}
    </div>
  );
}
