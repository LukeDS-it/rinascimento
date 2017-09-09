import * as webpack from 'webpack';
import HtmlWebpackPlugin = require('html-webpack-plugin');

const commons = require('./common');
const webpackMerge = require('webpack-merge');

const prodEnv: webpack.Configuration = {
    plugins: [
        new HtmlWebpackPlugin({
            hash: false,
            title: 'Rinascimento installation',
            template: './src/main/webapp/index.html',
            chunks: ['vendor', 'polyfills', 'installer', 'global'],
            // chunksSortMode: 'dependency',
            filename: 'cms-install.html'
        }),
        new HtmlWebpackPlugin({
            hash: false,
            title: 'Rinascimento administration',
            template: './src/main/webapp/index.html',
            chunks: ['vendor', 'polyfills', 'admin', 'global'],
            // chunksSortMode: 'dependency',
            filename: 'cms-admin.html'
        })
    ]
};

module.exports = webpackMerge(commons, prodEnv);
