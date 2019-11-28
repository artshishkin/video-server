<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Camera video files</title>


    <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet" />


</head>
<body>
<#--<div>-->
<#--    <#list videoFilesFromController as video_file>-->
<#--    <p>${video_file}-->
<#--        </#list>-->
<#--</div>-->
<div>

    <#--    VideoFile(id=5018, fullName=20191126_224647.h264, filePath=d:\Record\192.168.1.10\1\20191126_224647.h264,-->
    <#--    size=163840, date=2019-11-26 22:46:47.401, cameraName=192.168.1.10, videoType=Record)-->
    <table class="table">
        <tr>
            <th>Camera Name</th>
            <th>Video Type</th>
            <th>File Name</th>
            <th>Date</th>
            <th>Size (in bytes)</th>
        </tr>

        <#list videoFilesFromController as video_file>
            <tr>
                <td>${video_file.cameraName}</td>
                <td>${video_file.videoType}</td>
                <td>${video_file.fileName}</td>
                <td>${video_file.date}</td>
                <td>${video_file.size}</td>
            </tr>
        </#list>


    </table>


</div>

</body>
</html>