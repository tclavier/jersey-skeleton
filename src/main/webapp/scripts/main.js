requirejs.config({
    baseUrl: "",
    paths: {
        "jquery": "libs/jquery",
        "libs/boostrap": "libs/bootstrap",
        "grid": "scripts/grid"
    },

    shim: {
        "libs/bootstrap": {
            deps: ["jquery"]
        }
    }
});

require(["jquery", "libs/bootstrap", "grid"], function ($) {
    var Grid = require("grid");

    var grid = new Grid(null, [[0, 0],[1, 0]], 100, 100);

    grid.render($("#grid")[0].getContext('2d'));


    console.log("main loaded");
});
