import * as React from 'react';
import { Typography, Col, Row, ColProps } from 'antd';
import Form from './AppForm';
import Detail from './AppDetail';
import { ShortUrl } from '../models/shortUrl';
import { Card } from 'antd';

interface AppProps { }

interface AppState {
	data: ShortUrl | null
}

export default class extends React.Component<AppProps, AppState>{
	constructor(props: AppProps) {
		super(props);
		this.state = {
			data: null,
		};
		this.afterCreate = this.afterCreate.bind(this)
	}

	afterCreate(data: ShortUrl): void {
		this.setState({ data })
	}

	render() {
		const ColSizeXs: ColProps = { span: 22, offset: 1 }
		const ColSizeSm: ColProps = { span: 20, offset: 2 }
		const ColSizeMd: ColProps = { span: 18, offset: 3 }
		const ColSizeLg: ColProps = { span: 16, offset: 4 }
		const ColSizeXl: ColProps = { span: 14, offset: 5 }
		return (
			<main className="container my-5">
				<Row>
					<Col
						xs={ColSizeXs}
						sm={ColSizeSm}
						md={ColSizeMd}
						lg={ColSizeLg}
						xl={ColSizeXl}
					>
						<Card title="短链接试一下~" bordered={false}>
							<Form afterCreate={this.afterCreate} />
							<Detail data={this.state.data} />
						</Card>
					</Col>
				</Row>
			</main>
		);
	}
}
