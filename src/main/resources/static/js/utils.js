function putParameterToUrl(key, value) {
    var newUrl = $.query.copy().set(key, value);
    $(location).attr('search', newUrl);
}