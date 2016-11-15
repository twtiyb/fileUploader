

(function( $ ){
    // 当domReady的时候开始初始化
    $(function() {

        var uploader = WebUploader.create({
            // 自动上传。
            auto: true,
            // swf文件路径
            swf: './Uploader.swf',

            // 文件接收服务端。
            server: '/webUpload.action',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',

            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false
        });


        // 当有文件被添加进队列的时候
        uploader.on( 'fileQueued', function( file ) {
            $("#thelist").append( '<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>' );
        });



        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo( $li ).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');

            $percent.css( 'width', percentage * 100 + '%' );
        });

        uploader.on( 'uploadSuccess', function( file,json ) {

            if (!json.success) {
                layer.msg(json.obj);
                return;
            } else {
                $( '#'+file.id ).find('p.state').text('已上传');
                $('#' + file.id).parent().find("input").eq(0).attr("value", json.obj[0].url);
            }



        });

        uploader.on( 'uploadError', function( file ) {
            $( '#'+file.id ).find('p.state').text('上传出错');
        });

        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').fadeOut();
        });


    })
})( jQuery );