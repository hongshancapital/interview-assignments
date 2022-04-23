const path = require('path');
const CracoLessPlugin = require('craco-less');
module.exports = {
	plugins: [
		{
			plugin: CracoLessPlugin,
			options: {},
		},
	],
	webpack: {
		alias: {
			'@': path.resolve(__dirname, 'src'),
		},
	},
};
