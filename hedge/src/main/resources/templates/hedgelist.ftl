<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>对冲记录列表</title>
    <style>
        body{
            text-align:center;
        }
        p{
            font-size:24px;
            background-color:#ccc;
            text-align:center;
        }
        tr{
            width:800px;
            height:70px;
        }
        td{
            width:100px;
        }
    </style>
</head>
<body>
<table border="1" cellspacing="0" cellpadding="0">
    <caption><h2>对冲记录列表</h2></caption>
    <tr>
        <th>比赛</th>
        <th>记录时间</th>
        <th>主胜盘口选择及赔率</th>
        <th>平盘口选择及赔率</th>
        <th>客胜盘口选择及赔率</th>
        <th>是否最新</th>
        <th>返还率</th>
    </tr>
<#list hedgeRecordList as item>
    <tr align="center">
        <td>${item.betInfoKey}</td>
        <td>${item.currTime?number_to_datetime}</td>
        <td>${item.optimizationDetail.winPlatfrom}:${item.optimizationDetail.winOdds}</td>
        <td>${item.optimizationDetail.equalPlatform}:${item.optimizationDetail.equalOdds}</td>
        <td>${item.optimizationDetail.lossPlatform}:${item.optimizationDetail.lossOdds}</td>
        <td>${item.isNewest}</td>
        <td>${item.returnRateString}</td>
    </tr>
</#list>
</table>
</body>
</html>