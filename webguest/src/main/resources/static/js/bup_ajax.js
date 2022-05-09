//Form Data전송
function ajaxFormSend(url,formData,func){
    $.ajaxSettings.traditional = true;//배열 형태로 서버쪽 전송을 위한 설정
    $.ajax({
        beforeSend:function(xhr){
            xhr.setRequestHeader("AJAX", true);
        },
        data: formData,
        async: false,
        contentType: false,
        processData: false,
        type: "POST",
        enctype: "multipart/form-data",
        url: url,
        cache: false,
        processData: false,
        success: func,
        error : function(error){
            console.log(error);
        }
    });
}
function ajaxDataSend(url,data,func){
    $.ajax({
        beforeSend:function(xhr){
            xhr.setRequestHeader("AJAX", true);
        },
        data : data,
        async: false,
        contentType: false,
        processData: false,
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: url,
        cache: false,
        processData: false,
        success : func,
        error : function(error){
            console.log(error);
        }
    });
}



function postFormDataSubmit(path, params, method) {
    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement('form');
    method = 'post';
    form.method = method;
    form.action = path;

    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            var hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = key;
            hiddenField.value = params[key];

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}


function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
