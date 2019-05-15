module.exports = function(grunt) {
    grunt.initConfig({
        uglify: {
            core: {
                files: [{
                    expand: true,
                    cwd: 'app/assets',
                    src: 'js/*.js',
                    dest: 'app/assets',
                    ext: '.min.js'
                }]
            }
        },

    });
    grunt.loadNpmTasks('grunt-contrib-uglify-es');
    grunt.registerTask('default', ['uglify']);
};