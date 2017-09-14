const StringReplacePlugin = require('string-replace-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

import * as webpack from 'webpack';
import * as path from 'path';

const commonConfig: webpack.Configuration = {
    output: {
        path: path.resolve('build/www'),
        filename: 'resources/admin/[name].bundle.js',
        chunkFilename: 'resources/admin/[id].chunk.js'
    },
    entry: {
        admin: './src/main/webapp/app/admin/main.ts',
        installer: './src/main/webapp/app/install/main.ts',
        global: './src/main/webapp/app/global.scss',
        vendor: './src/main/webapp/app/vendor.ts',
        polyfills: './src/main/webapp/app/polyfills.ts'
    },
    resolve: {
        extensions: ['.ts', '.js'],
        modules: ['node_modules']
    },
    module: {
        rules: [
            {
                test: /\.ts$/, loaders: ['angular2-template-loader', 'awesome-typescript-loader']
            },
            {
                test: /\.html$/, loader: 'raw-loader', exclude: ['./src/main/webapp/index.html']
            },
            {
                test: /index\.html/,
                loader: StringReplacePlugin.replace({
                    replacements: [
                        {
                            pattern: /spinner\.gif/,
                            replacement: function () {
                                return '/resources/admin/spinner.gif';
                            }
                        }
                    ]
                })
            },
            {
                test: /\.(jpe?g|png|gif|svg|woff2?|ttf|eot)$/,
                use: [{loader: 'file-loader?name=resources/admin/[name].[ext]'}]
            },
            {
                test: /\.scss/,
                loaders: ['to-string-loader', 'css-loader', 'sass-loader'],
                exclude: /global\.scss/
            },
            {
                test: /\.css/,
                loaders: ['to-string-loader', 'css-loader'],
                exclude: /vendor\.css/
            },
            {
                test: /global\.scss/,
                loaders: ['style-loader', 'css-loader', 'sass-loader']
            },
            {
                test: /vendor\.css/,
                loaders: ['style-loader', 'css-loader']
            }
        ]
    },
    plugins: [
        new webpack.ContextReplacementPlugin(/angular(\\|\/)core(\\|\/)@angular/, './src', {}),
        // new webpack.optimize.CommonsChunkPlugin({names: ['admin', 'installer', 'vendor', 'polyfills']}),
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery'
        }),
        new StringReplacePlugin(),
        new CopyWebpackPlugin([{from: 'src/main/webapp/spinner.gif', to: 'resources/admin/spinner.gif'}])
    ]
};

module.exports = commonConfig;