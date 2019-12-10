<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Camera snapshots</title>


    <link type="text/css" href="/css/bootstrap.min.css" rel="stylesheet"/>


    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial;
        }

        .header {
            text-align: center;
            padding: 32px;
        }

        .row {
            display: -ms-flexbox;
            display: flex;
            -ms-flex-wrap: wrap;
            flex-wrap: wrap;
            padding: 0 4px;
        }

        /* Create two equal columns that sits next to each other */
        .column {
            -ms-flex: 0 0 50%;
            flex: 0 0 50%;
            padding: 0 4px;
        }

        .column img {
            margin-top: 8px;
            vertical-align: middle;
        }

        /* Style the buttons */
        .btn {
            border: none;
            outline: none;
            padding: 10px 16px;
            background-color: #f1f1f1;
            cursor: pointer;
            font-size: 36px;
        }

        .btn:hover {
            background-color: #ddd;
        }

        .btn.active {
            background-color: #666;
            color: white;
        }
    </style>

</head>
<body>
<#--<div>-->
<#--    <#list videoFilesFromController as video_file>-->
<#--    <p>${video_file}-->
<#--        </#list>-->
<#--</div>-->



<#--<div class="container">-->
<#--    <#list videoFilesFromController as video_file>-->
<#--        <img src="/videos/snapshot?video_file_path=${video_file.filePathEncoded}" class="img-thumbnail w-25" alt=""/>-->
<#--    </#list>-->
<#--</div>-->

<!-- Header -->
<div class="header" id="myHeader">
    <h1>Snapshots Grid</h1>
    <p>Click on the buttons to change the grid view.</p>
    <button class="btn" onclick="one()">1</button>
    <button class="btn active" onclick="two()">2</button>
    <button class="btn" onclick="four()">4</button>
</div>


<div style="width: 100%; background-color: rgba(255,0,0,0.1);">
    <#list videoFilesFromController as video_file>

<#--        <img src="/videos/snapshot?video_file_path=${video_file.filePathEncoded}" class="images_my" style="width: 24%" alt=""/>-->
        <img src="/videos/snapshot?video_file_path=${video_file.filePathEncoded}" style="width: 49%" class="images_my"  alt=""/>

    </#list>


</div>

<#--<div class="w-25">-->
<#--    <#list videoFilesFromController as video_file>-->
<#--        <img src="/videos/snapshot?video_file_path=${video_file.filePathEncoded}" class="img-fluid w-25" alt=""/>-->
<#--    </#list>-->

<#--</div>-->







<#--<div class="container-fluid">-->
<#--    <table class="table-responsive">-->
<#--        <#list videoFilesFromController as video_file>-->
<#--            <#if video_file?index % 4 == 0>-->
<#--                <tr>-->
<#--            </#if>-->
<#--            <td class="w-25">-->
<#--                <img src="/videos/snapshot?video_file_path=${video_file.filePathEncoded}" class="img-fluid" -->
<#--                     alt="No snapshot"/>-->
<#--            </td>-->
<#--            <#if video_file?index % 4 == 3 ||  video_file?isLast>-->
<#--                </tr>-->
<#--            </#if>-->



<#--        </#list>-->
<#--    </table>-->


<#--</div>-->


<script>
    // Get the elements with class="column"
    // var elements = document.getElementsByClassName("column");
    var elements = document.getElementsByClassName("images_my");

    // Declare a loop variable
    var i;

    // Four images side by side
    function four() {
        for (i = 0; i < elements.length; i++) {
            elements[i].style.width = "24%";
            // elements[i].style.msFlex = "0 0 25%";
            // elements[i].style.flex = "0 0 25%";
            // elements[i].style.maxWidth = "25%";
        }
    }

    // Two images side by side
    function two() {
        for (i = 0; i < elements.length; i++) {
            elements[i].style.width = "49%";
            // elements[i].style.msFlex = "0 0 50%";
            // elements[i].style.flex = "0 0 50%";
            // elements[i].style.maxWidth = "50%";
        }
    }

    // Full-width images
    function one() {
        for (i = 0; i < elements.length; i++) {
            elements[i].style.width = "100%";
            // elements[i].style.msFlex = "0 0 100%";
            // elements[i].style.flex = "0 0 100%";
            // elements[i].style.maxWidth = "100%";
        }
    }

    // Add active class to the current button (highlight it)
    var header = document.getElementById("myHeader");
    var btns = header.getElementsByClassName("btn");
    for (var i = 0; i < btns.length; i++) {
        btns[i].addEventListener("click", function(){
            var current = document.getElementsByClassName("active");
            current[0].className = current[0].className.replace(" active", "");
            this.className += " active";
        });
    }
</script>

</body>
</html>