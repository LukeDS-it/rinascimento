import webpack = require('webpack');
import * as HtmlWebpackPlugin from 'html-webpack-plugin';

// noinspection JSUnusedLocalSymbols
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const devEnv = require('./dev');
const webpackMerge = require('webpack-merge');


const installerConfig:  webpack.Configuration = {
    plugins: [
        new HtmlWebpackPlugin({
            hash: false,
            title: 'Rinascimento administration',
            template: './src/main/webapp/index.html',
            chunks: ['vendor', 'polyfills', 'installer', 'global'],
            // chunksSortMode: 'dependency',
            filename: 'index.html'
        })
    ]
};

module.exports = webpackMerge(devEnv, installerConfig);