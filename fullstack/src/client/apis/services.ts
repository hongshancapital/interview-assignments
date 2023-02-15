/**
 * url shortening function, use to shorten specify url
*/
export async function shortenUrl(
  url: string,
  body: Record<string, unknown>,
  headers: Record<string, unknown> = {}
): Promise<any> {
  try {
    const resp: Response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        ...headers,
      },
      body: JSON.stringify(body),
    });
    return resp.json();
  } catch (e) {
    throw new Error(e);
  }
}

export async function getRawUrl(
  url: string,
  headers: Record<string, unknown> = {}
): Promise<any> {
  try {
    const resp: Response = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        ...headers,
      },
    });
    return resp.json();
  } catch (e) {
    throw new Error(e);
  }
}
