module.exports = {
    publicPath: '/',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/api':{
                target:'http://localhost:8090/',
                changeOrigin:true,
                pathRewrite:{
                    '/api/':''
                }
            }
        }
    }
}
