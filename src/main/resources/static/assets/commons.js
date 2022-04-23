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
        },
        error : function(request){
            reObj.responseText = request.responseText;
        },
    });
    return reObj;
}

function selectedValues(obj){
    let selects = document.querySelectorAll("select");
    selects.forEach(function(item){
        obj[item.name] = item.value;
    })
    return obj;
}

function inputValues(obj){
    let inputs = document.querySelectorAll("input");
    inputs.forEach(function(item){
        if(item.name){
            let val = item.value;
            obj[item.name] = val.trim();
        }
    })
    return obj;
}