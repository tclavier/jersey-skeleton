requirejs.config({
    baseUrl: "libs",
    paths: {
        "jquery": "jquery",
        "boostrap": "bootstrap"
    },

    shim: {
        "bootstrap": {
            deps: ["jquery"]
        }
    }
});

require(["jquery", "bootstrap"], function ($) {
    console.log("main loaded");
});
