$(document).ready(function () {

    $("#provinsi").change(function () {

 
            $.getJSON("/instansi/tambah", {
                provinsiId : $(this).val(),
                ajax : 'true'
            }, function(data) {
                var html = '<select name="instansi">';
                var len = data.length;
                for ( var i = 0; i < len; i++) {
                    html += '<option value="' + data[i].nama + '">'
                            + data[i].nama + '</option>';
                }
                html += '</select>';
                console.log("berhasil");
                $('#instansi').html(html);
            });
    });    
        
});

