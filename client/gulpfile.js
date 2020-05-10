const { src, dest, watch, parallel } = require("gulp");
const rename = require("gulp-rename");

const ts = require("gulp-typescript");
const tsProject = ts.createProject("tsconfig.json");
const terser = require('gulp-terser');

const sass = require("gulp-sass");
sass.compiler = require("node-sass");

const ejs = require("gulp-ejs");

const compileTypescript = () => {
  return tsProject.src().pipe(tsProject()).js.pipe(terser()).pipe(dest("dist"));
};

const compileTypescriptLive = () => {
  watch("src/js/**/*.ts", { ignoreInitial: false }, compileTypescript);
};

const compileSass = () => {
  return src("src/css/**/*.scss")
    .pipe(sass({outputStyle: 'compressed'}).on("error", sass.logError))
    .pipe(dest("dist"));
};

const compileSassLive = () => {
  watch("src/css/**/*.scss", { ignoreInitial: false }, compileSass);
};

const compileEjs = () => {
  return src("src/html/*.ejs")
    .pipe(ejs())
    .pipe(rename({ extname: ".html" }))
    .pipe(dest("dist"));
};

const compileEjsLive = () => {
  watch("src/html/**/*.ejs", { ignoreInitial: false }, compileEjs);
};

const copyStatic = () => {
  return src("public/**/*", { nodir: true })
    .pipe(rename({ dirname: "" }))
    .pipe(dest("dist"));
};

const copyStaticLive = () => {
  watch("public/**/*", { ignoreInitial: false }, copyStatic);
};

exports.build = parallel(
  compileTypescript,
  compileSass,
  compileEjs,
  copyStatic
);

exports.dev = parallel(
  compileTypescriptLive,
  compileSassLive,
  compileEjsLive,
  copyStaticLive
);

exports.default = exports.build;
