import ExtractTextPlugin = require('extract-text-webpack-plugin');
import * as webpack from 'webpack';
import * as HtmlWebpackPlugin from 'html-webpack-plugin';
import * as path from 'path';

const commonConfig: webpack.Configuration = {
    output: {
        path: path.resolve('build/www'),
        filename: 'resources/admin/[name].bundle.js'
    },
    entry: {
        admin: './src/main/webapp/app/admin/main.ts',
        installer: './src/main/webapp/app/install/main.ts'
    },
    resolve: {
        extensions: ['.ts', '.js']
    },
    module: {
        loaders: [
            {test: /\.ts$/, loader: 'ts-loader', exclude: '/node-modules'},
            {test: /\.html$/, loader: 'raw-loader', exclude: ['./src/main/webapp/index.html']},
            {
                test: /\.scss/, use: ExtractTextPlugin.extract({
                use: [
                    {loader: 'css-loader'}, {loader: 'sass-loader'}
                ]
            })
            },
            {
                test: /\.(png|jpe?g|svg)$/, use: [{loader: 'file-loader?name=[path]/[name].[ext]'}]
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            hash: false,
            title: 'Rinascimento installation',
            template: './src/main/webapp/index.html',
            chunks: ['installer'],
            filename: 'cms-install.html'
        }),
        new HtmlWebpackPlugin({
            hash: false,
            title: 'Rinascimento administration',
            template: './src/main/webapp/index.html',
            chunks: ['admin'],
            filename: 'cms-admin.html'
        })
    ]
};

module.exports = commonConfig;