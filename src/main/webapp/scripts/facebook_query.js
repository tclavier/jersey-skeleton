function initFacebookSdk() {

    window.fbAsyncInit = function () {
        FB.init({
            appId: '1550153965266129',
            xfbml: true,
            version: 'v2.1'
        });
    };

    (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id))
            return;

        js = d.createElement(s);
        js.id = id;
        js.src = "http://connect.facebook.net/fr_FR/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
}

initFacebookSdk();