import * as React from 'react';
import { useState, useEffect } from 'react';
import Header from "./header";
import InputByApi from "./inputByApi";

/* HOOK REACT EXAMPLE */
const App = (props: AppProps) => {
	const [greeting, setGreeting] = useState<string>('');

	useEffect(() => {
		// async function getGreeting() {
		// 	try {
		// 		const res = await fetch('/api/hello');
		// 		const greeting = await res.json();
		// 		setGreeting(greeting);
		// 	} catch (error) {
		// 		console.log(error);
		// 	}
		// }
		// getGreeting();
	}, []);

	return (
		<main className="container my-5">
			<Header />
			<InputByApi type="short" />
			<InputByApi type="long" />
		</main>
	);
};

interface AppProps {}

/* CLASS REACT EXAMPLE */
// class App extends React.Component<IAppProps, IAppState> {
// 	constructor(props: IAppProps) {
// 		super(props);
// 		this.state = {
// 			name: null
// 		};
// 	}

// 	async componentDidMount() {
// 		try {
// 			let r = await fetch('/api/hello');
// 			let name = await r.json();
// 			this.setState({ name });
// 		} catch (error) {
// 			console.log(error);
// 		}
// 	}

// 	render() {
// 		return (
// 			<main className="container my-5">
// 				<h1 className="text-primary text-center">Hello {this.state.name}!</h1>
// 			</main>
// 		);
// 	}
// }

// export interface IAppProps {}

// export interface IAppState {
// 	name: string;
// }

export default App;
