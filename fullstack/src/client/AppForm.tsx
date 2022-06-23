import * as React from 'react';
import { Button, Form, Input, Checkbox, FormInstance } from 'antd';
import { GlobalOutlined } from '@ant-design/icons';
import { ShortUrl } from '../models/shortUrl';
import { createShortUrl } from './requests';

interface AppFormProps {
	afterCreate(data: ShortUrl): void
}

interface AppFormState {
	loading: boolean
}

export default class extends React.Component<AppFormProps, AppFormState> {
	constructor(props: AppFormProps) {
		super(props);
		this.onFinish = this.onFinish.bind(this);
		this.state = {
			loading: false,
		};
	}

	async onFinish(values: ShortUrl) {
		const { long_url } = values;
		this.setState({loading: true})
		const res = await createShortUrl(long_url);
		this.props.afterCreate(res)
		this.setState({loading: false})
	};

	render() {
		return (
			<Form
				name="short_url_form"
				labelCol={{ span: 4 }}
				wrapperCol={{ span: 20 }}
				initialValues={{ remember: true }}
				onFinish={this.onFinish}
				autoComplete="off"
			>
				<Form.Item
					label="长网址"
					name="long_url"
					rules={[{ required: true, message: '长网址为空或不是合法的URL', pattern: /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/ }]}
				>
					<Input
						size="large"
						prefix={<GlobalOutlined />}
					/>
				</Form.Item>

				<Form.Item wrapperCol={{ offset: 4, span: 16 }}>
					<Button type="primary" htmlType="submit" size='large' loading={this.state.loading}>
						生成
					</Button>
				</Form.Item>
			</Form>
		);
	}
}
