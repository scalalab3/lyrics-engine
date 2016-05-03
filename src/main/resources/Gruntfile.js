module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        watch: {
            react: {
                files: 'js/react_components/*.jsx',
                tasks: ['browserify']
            }
        },

        browserify: {
            options: {
                transform: [ require('grunt-react').browserify ]
            },
            client: {
                src: ['js/react_components/*.jsx', 'js/react_components/modules/Home.jsx'],
                dest: 'js/scripts/home.js'
            },
            js: {
                src: ['js/react_components/*.jsx', 'js/react_components/modules/Recomend.jsx'],
                dest: 'js/scripts/recomend.js'
            }
        }
    });

    grunt.loadNpmTasks('grunt-browserify');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('default', [
        'browserify'
    ]);
};
