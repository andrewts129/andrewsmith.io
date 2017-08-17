module.exports = function(grunt) {
    grunt.initConfig({
        uglify: {
            core: {
                files: [{
                    expand: true,
                    cwd: 'src/main/resources',
                    src: 'static/js/*.js',
                    dest: "target/classes",
                    ext: '.js'
                }]
            }
        },

        cssmin: {
            core: {
                files: [{
                    expand: true,
                    cwd: 'src/main/resources',
                    src: 'static/css/*.css',
                    dest: "target/classes",
                    ext: '.css'
                }]
            }
        }

    });
    grunt.loadNpmTasks('grunt-contrib-uglify', 'grunt-contrib-cssmin');
    grunt.registerTask('default', ['uglify', 'cssmin']);
};
