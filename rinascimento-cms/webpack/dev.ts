import * as webpack from 'webpack';
import * as BrowserSyncPlugin from 'browser-sync-webpack-plugin';

const commons = require('./common');
const webpackMerge = require('webpack-merge');

const devEnv: webpack.Configuration = {
    devtool: 'inline-source-map',
    devServer: {
        contentBase: './build/www',
        proxy: [{
            context: ['/api'],
            target: 'http://127.0.0.1:8080',
            secure: false
        }]
    },
    plugins: [
        new BrowserSyncPlugin({
            host: 'localhost',
            port: 4200,
            proxy: 'http://localhost:4260'
        }, {
            reload: false
        })
    ]
};

module.exports = webpackMerge(commons, devEnv);
