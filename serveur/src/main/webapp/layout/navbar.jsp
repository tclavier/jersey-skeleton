<nav class="navbar navbar-default">
  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav">
      <li><a href="/">Javascript (Single Page Application)</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">MVC Template (jsp)<span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="/html/user">Liste des "User"</a></li>
          <li><a href="/html/secure">Liste s&eacute;curis&eacute;e</a></li>
          <li><a id="login" href="/html/login">Login</a></li>
        </ul>
      </li>
    </ul>
  </div>
</nav>
<script type="text/javascript">
    function readCookie(name) {
        var nameEQ = encodeURIComponent(name) + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) return decodeURIComponent(c.substring(nameEQ.length, c.length));
        }
        return null;
    }
    $(document).ready(function() {
        $("#login").attr("href", "/html/login?user="+readCookie("user"));
        $("#login").text("Re-login");
    });
</script>
