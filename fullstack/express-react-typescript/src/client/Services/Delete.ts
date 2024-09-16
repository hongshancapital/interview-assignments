export default async function Delete(
    url: string,
    body: Record<string, unknown>,
    headers:Record<string, unknown> = {}
): Promise<any>{
    try {
        const response: Response = await fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                ...headers
            },
            body: JSON.stringify(body)
        });
        return response.json();
    } catch (e) {
        throw new Error(e);
    }
}
