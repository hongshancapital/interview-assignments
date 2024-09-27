module.export{
	resolve:{
		extensions:['.js','.jsx','.json']  //表示这几个文件的后缀名可以省略不写
	}
  output: {
    path: path.resolve(__dirname, 'dist') 
    // filename: 'js/[name].[hash].js'
    // publicPath: "./" 
  }
}