import React from 'react';
import ReactDOM from 'react-dom/client';

function CreateShortUrl(props) {
    const [longUrl, setLongUrl] = React.useState("");
    const [isWorking, setIsWorking] = React.useState(false);
    const [shortUrl, setShortUrl] = React.useState<string | null>(null);
    const [remoteError, setRemoteError] = React.useState<string | null>(null);

    const createShortUrl = async () => {
        setIsWorking(true);
        try {
            const response = await fetch("/api/create", {
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({longUrl}),
            });
            const json = await response.json();
            if (json.shortUrl) {
                setShortUrl(`${location.origin}/go/${json.shortUrl}`);
                setRemoteError(null);
            } else {
                setShortUrl(null);
                setRemoteError(json.message);
            }
        } finally {
            setIsWorking(false);
        }
    }

    return (
        <div>
            <h1>Create your short url</h1>

            <label>
                Original URL: <input type="text"
                                     value={longUrl}
                                     onChange={e => setLongUrl(e.target.value.trim())}
                                     placeholder="e.g. https://google.com/" />
            </label>

            <div>
                <input type="button" value="Get me one" disabled={isWorking} onClick={createShortUrl} />
            </div>

            { remoteError !== null ? (
                <span style={{color: "red"}}>{ remoteError }</span>
            ) : null}

            { shortUrl !== null ? (
                <div>
                    Your short URL: <a href={shortUrl} target="_blank">{shortUrl}</a>
                </div>
            ) : null}
        </div>
    );
}

const container = document.body;
const root = ReactDOM.createRoot(container);
root.render(<CreateShortUrl />);
