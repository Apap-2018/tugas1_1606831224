$(document).ready(function () {

    $("#provinsi").change(function () {

 
            $.getJSON("/instansi/tambah", {
                provinsiId : $(this).val(),
                ajax : 'true'
            }, function(data) {
                var html = '';
                var len = data.length;
                for ( var i = 0; i < len; i++) {
                    html += '<option value="' + data[i].id + '">'
                            + data[i].nama + '</option>';
                }
                html += '';
                console.log("berhasil");
                $('#instansi').html(html);
            });
    });    
        
});

