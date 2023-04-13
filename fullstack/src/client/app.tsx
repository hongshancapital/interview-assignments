import React, { useState } from "react";
import axios from "axios";

const App = () => {
    const [longUrl, setLongUrl] = useState("");
    const [shortUrl, setShortUrl] = useState("");

    const handleLongUrlChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setLongUrl(event.target.value);
    };

    const handleSubmit = async (event: any) => {
        debugger;
        event.preventDefault();
        const response = await axios.post("/api/shortUrls", { longUrl });
        setShortUrl(response.data.shortUrl);
    };

    return (
        <div>
            <label>
                Long URL:
                <input type="text" value={longUrl} onChange={handleLongUrlChange} />
            </label>
            <br />
            <button onClick={(event)=>handleSubmit(event)} type="submit">Shorten URL</button>
            {shortUrl && (
                <div>
                    Short URL: <a href={shortUrl}>{shortUrl}</a>
                </div>
            )}
        </div>
    );
};

export default App;
