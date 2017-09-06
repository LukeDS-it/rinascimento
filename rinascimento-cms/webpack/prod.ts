import * as webpack from 'webpack';

const commons = require('./common');
const webpackMerge = require('webpack-merge');

const prodEnv: webpack.Configuration = {

};

module.exports = webpackMerge(commons, prodEnv);
