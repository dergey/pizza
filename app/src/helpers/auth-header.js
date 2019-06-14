export function authHeader() {
    // return authorization header with basic auth credentials
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.authdata) {
        console.log(user);
        return {'Authorization': 'Basic ' + user.authdata};
    } else {
        return {};
    }
}

export function getAuthorization() {
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.authdata) {
        return 'Basic ' + user.authdata;
    } else {
        return {};
    }
}