
<form action="/TrafficThreat/registration" method="post" class="form-horizontal">
<input type="text" id="loginReg" class="form-control" placeholder="Login">
<input type="password" id="passwordReg" class="form-control" placeholder="Password">
<input type="submit">
<button onclick="signUp('USER')" class="btn btn-default">Sign up</button>
<sec:authorize access="hasRole('ADMIN')">
    <button onclick="signUp('ADMIN')" class="btn btn-default">Sign up as admin</button>
</sec:authorize>
    </form>