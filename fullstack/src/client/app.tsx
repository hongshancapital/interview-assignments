import * as React from 'react';
import Header from "./header";
import InputByApi from "./inputByApi";

/* HOOK REACT EXAMPLE */
const App = (props: AppProps) => {
	return (
		<main className="container my-5">
			<Header />
			<InputByApi type="short" />
			<InputByApi type="long" />
		</main>
	);
};

interface AppProps {}

export default App;
