requirejs.config({
    baseUrl: "",
    paths: {
        "jquery": "libs/jquery",
        "libs/boostrap": "libs/bootstrap",
        "map": "scripts/map"
    },

    shim: {
        "libs/bootstrap": {
            deps: ["jquery"]
        }
    }
});

require(["jquery", "libs/bootstrap", "map"], function ($) {
    var Map = require("map");

    var map = new Map(null, [[0, 0],[1, 0]], 100, 100);

    map.render($("#map")[0].getContext('2d'));


    console.log("main loaded");
});
