module.exports = {
  lintOnSave: false,
  publicPath: './',
  devServer: {
      proxy: {
          "/api": {
              // target: "http://172.10.204.39:8080",
              // target: "http://172.10.206.53:8080",
              // target: "http://192.168.1.50:8080",
              target: "http://192.168.11.211:8088",
              // target: "http://data-sync.dev.app.yyuap.com",
              changeOrigin: true, //开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样服务端和服务端进行数据的交互就不会有跨域问题
              pathRewrite: {
                  "^/api": "/",
              },
          },
      },
  },
  chainWebpack: config => {
    config
      .plugin('html')
      .tap(args => {
        args[0].title = '账单清算系统'
        return args
      })
  },
}