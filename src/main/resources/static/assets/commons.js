function apiCon(method, url ,obj) {
    let reObj = new Object();
    $.ajax({
        url: url,
        type: method,
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: "json",
        async: false,
        success: function (result) {
            reObj = result;
        }
    });
    return reObj;
}