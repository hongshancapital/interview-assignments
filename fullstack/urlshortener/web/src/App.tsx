import './App.css';
import React, { FormEvent } from 'react';
import axios from 'axios';

interface AppState {
  originalUrl?: string;
  shortUrl?: string;
}

const SHORTEN_URL = `http://localhost/v1/urls`;

async function shorten(originalUrl: string): Promise<{ shortUrl: string; originalUrl: string; }> {
  return (await axios.post(SHORTEN_URL, { originalUrl })).data as { shortUrl: string; originalUrl: string; };
}

export class App extends React.Component<unknown, AppState> {

  public constructor(props: unknown) {
    super(props);
    this.state = {};
  }

  public render() {
    return (
      <div className="App">
        <form onSubmit={e => this.handleSubmit(e)}>
          <input
            type="text"
            name="originalUrl"
            value={this.state.originalUrl || ''}
            onChange={e => this.handleOriginalUrlChanged(e.target.value)}
          />
          <button type="submit">Generate</button>
        </form>
        {
          this.state.shortUrl &&
          <a href={this.state.shortUrl} target="_blank">{this.state.shortUrl}</a>
        }
      </div>
    );
  }

  private handleOriginalUrlChanged(value: string): void {
    this.setState({
      originalUrl: value,
    });
  }

  private async handleSubmit(e: FormEvent<HTMLFormElement>): Promise<void> {
    e.preventDefault();
    if (this.state.originalUrl) {
      this.setState({
        shortUrl: (await shorten(this.state.originalUrl)).shortUrl,
      });
    }
  }
}
