export type FetchShorlinkResponse = string;

export const fetchShortlink = async (originLink: string) => {
  const res = await fetch("/api/createshortLink", {
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify({ originLink }),
    method: "POST",
  });

  switch (res.status) {
    case 200:
      return (await res.text()) as FetchShorlinkResponse;

    default:
      throw new Error("An error occur while fetch short link.");
  }
};
